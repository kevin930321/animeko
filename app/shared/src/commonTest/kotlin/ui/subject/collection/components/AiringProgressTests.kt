/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.ui.subject.collection.components

import me.him188.ani.app.data.models.subject.ContinueWatchingStatus
import me.him188.ani.app.data.models.subject.ContinueWatchingStatus.Done
import me.him188.ani.app.data.models.subject.ContinueWatchingStatus.NotOnAir
import me.him188.ani.app.data.models.subject.ContinueWatchingStatus.Start
import me.him188.ani.app.data.models.subject.SubjectAiringInfo
import me.him188.ani.app.data.models.subject.SubjectAiringKind
import me.him188.ani.app.data.models.subject.SubjectAiringKind.COMPLETED
import me.him188.ani.app.data.models.subject.SubjectAiringKind.ON_AIR
import me.him188.ani.app.data.models.subject.SubjectAiringKind.UPCOMING
import me.him188.ani.app.data.models.subject.SubjectProgressInfo
import me.him188.ani.app.tools.WeekFormatter
import me.him188.ani.app.ui.foundation.stateOf
import me.him188.ani.app.ui.subject.AiringLabelState
import me.him188.ani.app.ui.subject.SubjectProgressState
import me.him188.ani.datasources.api.EpisodeSort
import me.him188.ani.datasources.api.PackedDate
import me.him188.ani.datasources.api.PackedDate.Companion.Invalid
import me.him188.ani.test.TestContainer
import me.him188.ani.test.TestFactory
import me.him188.ani.test.runDynamicTests
import kotlin.test.assertEquals
import kotlin.time.Instant

/**
 * Test [AiringLabelState] and [SubjectProgressState]
 */
@TestContainer
class AiringProgressTests {
    private val today = Instant.parse("2024-08-23T12:00:00Z")

    private class Scope(
        val airingLabelState: AiringLabelState,
        val subjectProgressState: SubjectProgressState,
    ) {
        val airingLabel get() = airingLabelState.run { """$progressText · $totalEpisodesText""" }
        val highlightProgress get() = airingLabelState.highlightProgress
        val buttonText get() = subjectProgressState.buttonText
        val buttonIsPrimary get() = subjectProgressState.buttonIsPrimary
    }

    private fun create(
        // SubjectAiringInfo
        kind: SubjectAiringKind,
        latestSort: Int?,
        // SubjectProgressInfo
        ep: ContinueWatchingStatus,
        episodeCount: Int = 12,
    ): Scope {
        val subjectProgressInfo = SubjectProgressInfo.Done.copy(
            continueWatchingStatus = ep,
            nextEpisodeIdToPlay = null,
        )
        val progressInfoState = stateOf(
            subjectProgressInfo,
        )
        return Scope(
            AiringLabelState(
                airingInfoState = stateOf(
                    SubjectAiringInfo.EmptyCompleted.copy(
                        kind = kind,
                        airDate = if (ep is NotOnAir) ep.airDate else Invalid,
                        latestEp = when {
                            latestSort == null -> null
                            latestSort <= 12 -> latestSort
                            else -> latestSort - 12
                        }?.let { EpisodeSort(it) },
                        latestSort = latestSort?.let { EpisodeSort(it) },
                        mainEpisodeCount = episodeCount,
                    ),
                ),
                progressInfoState = progressInfoState,
            ),
            SubjectProgressState(
                stateOf(subjectProgressInfo),
                weekFormatter = WeekFormatter { today },
            ),
        )
    }

    @TestFactory
    fun tests() = runDynamicTests {
        val aug24 = PackedDate(2024, 8, 24)
        val sep30 = PackedDate(2024, 9, 30)

        val watched2 = ContinueWatchingStatus.Watched(EpisodeSort(2), EpisodeSort(2), Invalid)
        val watched22 = ContinueWatchingStatus.Watched(EpisodeSort(22 - 12), EpisodeSort(22), Invalid)
        val watched1 = ContinueWatchingStatus.Watched(EpisodeSort(1), EpisodeSort(1), Invalid)
        val done = Done

        add("未開播, 沒有時間") {
            create(UPCOMING, null, ep = NotOnAir(Invalid)).run {
                assertEquals("未開播 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("還未開播", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("未開播但是有最新劇集 (bgm 條目數據問題)") {
            create(UPCOMING, 1, ep = NotOnAir(Invalid)).run {
                assertEquals("未開播 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("還未開播", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("未開播, 有開播時間") {
            create(UPCOMING, null, ep = NotOnAir(aug24)).run {
                assertEquals("未開播 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("明天開播", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("未開播, 有開播時間 (下個月)") {
            create(UPCOMING, null, ep = NotOnAir(sep30)).run {
                assertEquals("未開播 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("9 月 30 日開播", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("未開播, 看過第一集 (偷跑)") {
            create(UPCOMING, null, ep = watched1).run {
                assertEquals("未開播 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("看過 01", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("未開播, 看完了") {
            create(UPCOMING, null, ep = done).run {
                assertEquals("未開播 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("已看完", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }

        add("連載中, 還沒開始看") {
            create(ON_AIR, null, ep = Start).run {
                assertEquals("連載中 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("開始觀看", buttonText)
                assertEquals(true, buttonIsPrimary)
            }
        }
        add("連載中, 劇集列表還未知, 看完了") {
            create(ON_AIR, null, ep = Done).run {
                assertEquals("已看完 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("已看完", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("連載到 2, 看完了") {
            create(ON_AIR, 2, ep = Done).run {
                assertEquals("已看完 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("已看完", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("連載到 1, 看過 2, 沒有 3 的開播時間") {
            create(ON_AIR, 1, ep = watched2).run {
                assertEquals("看過 02 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("看過 02", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("連載到 1, 看過 2, 有 3 的開播時間") {
            create(ON_AIR, 1, ep = ContinueWatchingStatus.Watched(EpisodeSort(2), EpisodeSort(2), aug24)).run {
                assertEquals("看過 02 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("明天更新", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("連載到 2, 看過 1, 沒有下集的開播時間") {
            create(
                ON_AIR, 2,
                ep = ContinueWatchingStatus.Watched(EpisodeSort(1), EpisodeSort(1), Invalid),
            ).run {
                assertEquals("看過 01 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("看過 01", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("連載到 2, 看過 1, 有下集開播時間") {
            create(
                ON_AIR, 2,
                ep = ContinueWatchingStatus.Watched(EpisodeSort(1), EpisodeSort(1), aug24),
            ).run {
                assertEquals("看過 01 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("明天更新", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("連載到 2, 看過 1, 可以看 2") {
            create(
                ON_AIR, 2,
                ep = ContinueWatchingStatus.Continue(EpisodeSort(2), EpisodeSort(2), EpisodeSort(1), EpisodeSort(1)),
            ).run {
                assertEquals("連載至 02 · 預定全 12 話", airingLabel)
                assertEquals(true, highlightProgress)
                assertEquals("繼續觀看 02", buttonText)
                assertEquals(true, buttonIsPrimary)
            }
        }
        add("連載到 2, 看過 2, 沒有下集開播時間") {
            create(ON_AIR, 2, ep = watched2).run {
                assertEquals("看過 02 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("看過 02", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("連載到 2, 看過 2, 有下集開播時間") {
            create(
                ON_AIR, 2,
                ep = ContinueWatchingStatus.Watched(EpisodeSort(2), EpisodeSort(2), aug24),
            ).run {
                assertEquals("看過 02 · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("明天更新", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("已完結, 沒看過") {
            create(COMPLETED, 12, ep = Start).run {
                assertEquals("已完結 · 全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("開始觀看", buttonText)
                assertEquals(true, buttonIsPrimary)
            }
        }
        add("已完結, 看了 1") {
            create(
                COMPLETED, 12,
                ep = ContinueWatchingStatus.Continue(
                    EpisodeSort(2),
                    EpisodeSort(2),
                    EpisodeSort(1),
                    EpisodeSort(1),
                ),
            ).run {
                assertEquals("看過 01 · 全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("繼續觀看 02", buttonText)
                assertEquals(true, buttonIsPrimary)
            }
        }
        add("已完結, 看了 1, 沒有下一集") {
            // 注意, 只要是计算为了 ContinueWatchingStatus.Watched, 就只能显示 "看過"
            // 不过如果总过有 12 集, 这种情况下 ContinueWatchingStatus 不会是 Watched.
            // 这个 case 只是为了更稳健
            create(COMPLETED, 12, ep = watched1).run {
                assertEquals("看過 01 · 全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("看過 01", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("已完結, 看完了") {
            create(COMPLETED, 12, ep = done).run {
                assertEquals("已看完 · 全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("已看完", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }


        // “看过 xx，全 xx 话” 同时显示 ep 和 sort #1047
        add("同時顯示 ep 和 sort: 連載到 2, 看過 2, 沒有下集開播時間") {
            create(ON_AIR, 23, ep = watched22).run {
                assertEquals("看過 10 (22) · 預定全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("看過 10 (22)", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("同時顯示 ep 和 sort: 看過 22, 全 12 話") {
            create(COMPLETED, 23, ep = watched22).run {
                assertEquals("看過 10 (22) · 全 12 話", airingLabel)
                assertEquals(false, highlightProgress)
                assertEquals("看過 10 (22)", buttonText)
                assertEquals(false, buttonIsPrimary)
            }
        }
        add("同時顯示 ep 和 sort: 連載到 23, 看過 22, 可以看 23") {
            create(
                ON_AIR, 23,
                ep = ContinueWatchingStatus.Continue(
                    episodeEp = EpisodeSort(23 - 12),
                    episodeSort = EpisodeSort(23),
                    watchedEpisodeEp = EpisodeSort(22 - 12),
                    watchedEpisodeSort = EpisodeSort(22),
                ),
            ).run {
                assertEquals("連載至 11 (23) · 預定全 12 話", airingLabel)
                assertEquals(true, highlightProgress)
                assertEquals("繼續觀看 11 (23)", buttonText)
                assertEquals(true, buttonIsPrimary)
            }
        }
    }
}
