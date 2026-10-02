/*
 * Copyright (C) 2024 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.data.models.trending

import me.him188.ani.client.models.AniTrendingSubject

data class TrendsInfo(
    val subjects: List<TrendingSubjectInfo>
)

/**
 * @see AniTrendingSubject
 */
data class TrendingSubjectInfo(
    val bangumiId: Int,
    val nameCn: String,
    val imageLarge: String,
    /**
     * 条目原名 (通常为日文), 供"显示原名"设置开启时使用.
     *
     * 服务端尚未下发该字段时为空, 此时回退显示 [nameCn].
     */
    val name: String = "",
) {
    val displayName: String get() = nameCn.ifBlank { name }
}

/**
 * 根据用户偏好选择的显示名称.
 * @param useOriginalTitle 为 `true` 时优先显示原名 ([TrendingSubjectInfo.name]).
 */
fun TrendingSubjectInfo.preferredDisplayName(useOriginalTitle: Boolean): String =
    if (useOriginalTitle) name.ifBlank { nameCn } else displayName
