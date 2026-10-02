/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.data.network

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.him188.ani.app.data.models.trending.TrendingSubjectInfo
import me.him188.ani.app.data.models.trending.TrendsInfo
import me.him188.ani.app.data.repository.Repository
import me.him188.ani.app.data.repository.runWrappingExceptionAsLoadResult
import me.him188.ani.app.tools.paging.SinglePagePagingSource
import me.him188.ani.client.apis.SubjectsAniApi
import me.him188.ani.client.apis.TrendsAniApi
import me.him188.ani.client.models.AniTrends
import me.him188.ani.utils.coroutines.IO_
import me.him188.ani.utils.ktor.ApiInvoker
import me.him188.ani.utils.logging.error
import me.him188.ani.utils.logging.warn
import kotlin.coroutines.CoroutineContext

class TrendsRepository(
    private val trendsApi: ApiInvoker<TrendsAniApi>,
    private val subjectApi: ApiInvoker<SubjectsAniApi>,
    private val ioDispatcher: CoroutineContext = Dispatchers.IO_
) : Repository() {
    suspend fun getTrendsInfo(): TrendsInfo {
        return withContext(ioDispatcher) {
            trendsApi {
                getTrends().body().toTrendsInfo()
            }.let { enrichWithOriginalNames(it) }
        }
    }

    // From animeko server
    fun trendsInfoPager(): Flow<PagingData<TrendsInfo>> {
        return Pager(defaultPagingConfig) {
            SinglePagePagingSource<Unit, TrendsInfo> {
                runWrappingExceptionAsLoadResult<Unit, TrendsInfo> {
                    val trendsInfo = withContext(ioDispatcher) {
                        trendsApi {
                            getTrends().body().toTrendsInfo()
                        }.let { enrichWithOriginalNames(it) }
                    }
                    PagingSource.LoadResult.Page(
                        listOf(trendsInfo),
                        null,
                        null,
                    )
                }.also {
                    if (it is PagingSource.LoadResult.Error) {
                        logger.error(it.throwable) { "Failed to load ani trends info." }
                    }
                }
            }
        }.flow
    }

    /**
     * 热度接口只下发中文名, 原名 (通常为日文, 数据源为 Bangumi) 在此按 `bangumiId` 补齐.
     * 单个条目失败时回退为空 (UI 按中文名显示), 不影响整个热度列表加载.
     */
    private suspend fun enrichWithOriginalNames(info: TrendsInfo): TrendsInfo = coroutineScope {
        val enriched = info.subjects.map { subject ->
            async {
                val name = try {
                    subjectApi {
                        getSubject(subject.bangumiId.toLong()).body()
                    }.name
                } catch (e: Exception) {
                    logger.warn(e) { "Failed to load original name for trending subject ${subject.bangumiId}." }
                    ""
                }
                subject.copy(name = name)
            }
        }.awaitAll()
        info.copy(subjects = enriched)
    }
}

fun AniTrends.toTrendsInfo(): TrendsInfo {
    return TrendsInfo(
        subjects = trendingSubjects.map {
            TrendingSubjectInfo(it.bangumiId, it.nameCn, it.imageLarge)
        },
    )
}
