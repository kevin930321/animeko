/*
 * Copyright (C) 2026 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.tv.ui.foundation

import androidx.compose.runtime.compositionLocalOf

/**
 * TV 侧的"显示原名"开关. 由 [TvAniAppContent][me.him188.ani.tv.ui.main.TvAniAppContent]
 * 从设置库提供, TV 叶子组件经此读取, 不得直接 import 手机 UI 树的
 * `LocalSubjectAppearanceSettings` (见 `TvArchitectureTest`).
 */
val LocalTvUseOriginalTitle = compositionLocalOf { false }
