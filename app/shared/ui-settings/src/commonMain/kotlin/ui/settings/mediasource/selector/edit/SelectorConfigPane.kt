/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.ui.settings.mediasource.selector.edit

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.him188.ani.app.domain.mediasource.web.format.SelectorChannelFormatIndexGrouped
import me.him188.ani.app.domain.mediasource.web.format.SelectorChannelFormatNoChannel
import me.him188.ani.app.domain.mediasource.web.format.SelectorFormatId
import me.him188.ani.app.domain.mediasource.web.format.SelectorSubjectFormat
import me.him188.ani.app.domain.mediasource.web.format.SelectorSubjectFormatA
import me.him188.ani.app.domain.mediasource.web.format.SelectorSubjectFormatIndexed
import me.him188.ani.app.domain.mediasource.web.format.SelectorSubjectFormatJsonPathIndexed
import me.him188.ani.app.ui.foundation.animation.LocalAniMotionScheme
import me.him188.ani.app.ui.foundation.animation.StandardEasing
import me.him188.ani.app.ui.foundation.effects.moveFocusOnEnter
import me.him188.ani.app.ui.foundation.text.ProvideTextStyleContentColor
import me.him188.ani.app.ui.foundation.theme.EasingDurations
import me.him188.ani.app.ui.settings.mediasource.rss.edit.MediaSourceHeadline
import me.him188.ani.datasources.api.topic.Resolution
import me.him188.ani.datasources.api.topic.SubtitleLanguage
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun SelectorConfigurationPane(
    state: SelectorConfigState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalSpacing: Dp = SelectorConfigurationDefaults.verticalSpacing,
    textFieldShape: Shape = SelectorConfigurationDefaults.textFieldShape,
) {
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(contentPadding),
    ) {
        // 大图标和标题
        MediaSourceHeadline(state.iconUrl, state.displayName)

        Column(
            Modifier
                .fillMaxHeight()
                .padding(vertical = 16.dp),
        ) {
            val listItemColors = ListItemDefaults.colors(containerColor = Color.Transparent)

            Column(verticalArrangement = Arrangement.spacedBy(verticalSpacing)) {
                OutlinedTextField(
                    state.displayName, { state.displayName = it },
                    Modifier
                        .fillMaxWidth()
                        .moveFocusOnEnter(),
                    label = { Text("名稱*") },
                    placeholder = { Text("設定顯示在列表中的名稱") },
                    isError = state.displayNameIsError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = textFieldShape,
                    enabled = state.enableEdit,
                )
                OutlinedTextField(
                    state.iconUrl, { state.iconUrl = it },
                    Modifier
                        .fillMaxWidth()
                        .moveFocusOnEnter(),
                    label = { Text("圖示連結") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = textFieldShape,
                    enabled = state.enableEdit,
                )
            }

            Row(Modifier.padding(top = verticalSpacing, bottom = 12.dp)) {
                ProvideTextStyleContentColor(
                    MaterialTheme.typography.titleMedium,
                    MaterialTheme.colorScheme.primary,
                ) {
                    Text(SelectorConfigurationDefaults.STEP_NAME_1)
                }
            }

            Column {
                OutlinedTextField(
                    state.searchUrl, { state.searchUrl = it },
                    Modifier.fillMaxWidth().moveFocusOnEnter(),
                    label = { Text("搜尋連結") },
                    placeholder = {
                        Text(
                            "示例：https://www.nyacg.net/search.html?wd={keyword}",
                            color = MaterialTheme.colorScheme.outline,
                        )
                    },
                    supportingText = {
                        Text(
                            """
                                    替換規則：
                                    {keyword} 替換為條目 (番劇) 名稱
                                """.trimIndent(),
                        )
                    },
                    isError = state.searchUrlIsError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = textFieldShape,
                    enabled = state.enableEdit,
                )
                OutlinedTextField(
                    state.rawBaseUrl, { state.rawBaseUrl = it },
                    Modifier
                        .padding(top = (verticalSpacing - 8.dp).coerceAtLeast(0.dp))
                        .fillMaxWidth().moveFocusOnEnter(),
                    label = { Text("Base URL (可選)") },
                    placeholder = state.baseUrlPlaceholder?.let {
                        {
                            Text(it, color = MaterialTheme.colorScheme.outline)
                        }
                    },
                    supportingText = {
                        Text(
                            """可選。用於拼接條目詳情 (劇集列表) 頁面 URL，將會影響步驟 2。預設自動從搜尋連結生成""".trimIndent(),
                        )
                    },
                    isError = state.searchUrlIsError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = textFieldShape,
                    enabled = state.enableEdit,
                )
                ListItem(
                    headlineContent = { Text("僅使用第一個詞") },
                    Modifier
                        .padding(top = (verticalSpacing - 8.dp).coerceAtLeast(0.dp))
                        .clickable(enabled = state.enableEdit) {
                            state.searchUseOnlyFirstWord = !state.searchUseOnlyFirstWord
                        },
                    supportingContent = { Text("以空格分割，僅使用第一個詞搜尋。適用於搜尋相容性差的情況") },
                    trailingContent = {
                        Switch(
                            state.searchUseOnlyFirstWord, { state.searchUseOnlyFirstWord = it },
                            enabled = state.enableEdit,
                        )
                    },
                    colors = listItemColors,
                )
                ListItem(
                    headlineContent = { Text("去除特殊字元") },
                    Modifier
                        .padding(top = (verticalSpacing - 8.dp).coerceAtLeast(0.dp))
                        .clickable(enabled = state.enableEdit) {
                            state.searchRemoveSpecial = !state.searchRemoveSpecial
                        },
                    supportingContent = { Text("去除特殊字元以及 \"電影\" 等字樣，提升搜尋成功率") },
                    trailingContent = {
                        Switch(
                            state.searchRemoveSpecial, { state.searchRemoveSpecial = it },
                            enabled = state.enableEdit,
                        )
                    },
                    colors = listItemColors,
                )

                var searchUseSubjectNamesCount by remember(state.searchUseSubjectNamesCount) {
                    mutableStateOf(state.searchUseSubjectNamesCount.toString())
                }
                OutlinedTextField(
                    searchUseSubjectNamesCount,
                    {
                        searchUseSubjectNamesCount = it
                        state.searchUseSubjectNamesCount = it.toIntOrNull() ?: state.searchUseSubjectNamesCount
                    },
                    Modifier
                        .padding(top = (verticalSpacing - 8.dp).coerceAtLeast(0.dp))
                        .fillMaxWidth().moveFocusOnEnter(),
                    label = { Text("嘗試條目名稱數量") },
                    supportingText = {
                        Text(
                            """
                                每次播放使用多少個條目名稱進行查詢。
                                為 1 則只使用主中文名稱，為 2 額外使用日文原名，大於 2 將額外使用其他別名，別名的數量不固定。
                                一般用 1 就夠了，使用多個名稱將會顯著增加播放時的等待時間。
                                """.trimIndent(),
                        )
                    },
                    isError = searchUseSubjectNamesCount.toIntOrNull().let {
                        it == null || it < 1
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = textFieldShape,
                    enabled = state.enableEdit,
                )
                var requestIntervalString by remember(state.requestInterval) {
                    mutableStateOf(state.requestInterval.inWholeMilliseconds.toString())
                }
                OutlinedTextField(
                    requestIntervalString,
                    {
                        requestIntervalString = it
                        state.requestInterval = it.toLongOrNull()?.milliseconds ?: state.requestInterval
                    },
                    Modifier
                        .padding(top = (verticalSpacing - 8.dp).coerceAtLeast(0.dp))
                        .fillMaxWidth().moveFocusOnEnter(),
                    label = { Text("搜尋請求間隔時間 (毫秒)") },
                    supportingText = {
                        Text(
                            """控制每發送一個請求後等待多久後再發送下一個請求""".trimIndent(),
                        )
                    },
                    isError = requestIntervalString.toLongOrNull() == null,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = textFieldShape,
                    enabled = state.enableEdit,
                )
            }

            SelectorSubjectFormatSelectionButtonRow(
                state,
                Modifier.fillMaxWidth().padding(bottom = 4.dp),
                enabled = state.enableEdit,
            )

            AnimatedContent(
                SelectorSubjectFormat.findById(state.subjectFormatId),
                Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .animateContentSize(tween(EasingDurations.standard, easing = StandardEasing)),
                transitionSpec = LocalAniMotionScheme.current.animatedContent.standard,
            ) { format ->
                SelectorSubjectConfigurationColumn(
                    format, state,
                    textFieldShape, verticalSpacing, listItemColors,
                    Modifier.fillMaxWidth(),
                )
            }

            Row(Modifier.padding(top = verticalSpacing, bottom = 12.dp)) {
                ProvideTextStyleContentColor(
                    MaterialTheme.typography.titleMedium,
                    MaterialTheme.colorScheme.primary,
                ) {
                    Text(SelectorConfigurationDefaults.STEP_NAME_2)
                }
            }

            SelectorChannelSelectionButtonRow(
                state,
                Modifier.fillMaxWidth().padding(bottom = 4.dp),
                enabled = state.enableEdit,
            )

            AnimatedContent(
                state.channelFormatId,
                Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .animateContentSize(tween(EasingDurations.standard, easing = StandardEasing)),
                transitionSpec = LocalAniMotionScheme.current.animatedContent.standard,
            ) { formatId ->
                SelectorChannelFormatColumn(formatId, state, Modifier.fillMaxWidth())
            }

            Row(Modifier.padding(top = verticalSpacing, bottom = 12.dp)) {
                ProvideTextStyleContentColor(
                    MaterialTheme.typography.titleMedium,
                    MaterialTheme.colorScheme.primary,
                ) {
                    Text("過濾設定")
                }
            }

            Column(
                Modifier,
                verticalArrangement = Arrangement.spacedBy((verticalSpacing - 16.dp).coerceAtLeast(0.dp)),
            ) {
                ListItem(
                    headlineContent = { Text("使用條目名稱過濾") },
                    Modifier.focusable(false).clickable(
                        enabled = state.enableEdit,
                    ) { state.filterBySubjectName = !state.filterBySubjectName },
                    supportingContent = { Text("要求資源標題包含條目名稱。適用於資料源可能搜到無關內容的情況。此功能只對 4.4.0 以前版本有效，對其他版本無效") },
                    trailingContent = {
                        Switch(
                            state.filterBySubjectName, { state.filterBySubjectName = it },
                            enabled = state.enableEdit,
                        )
                    },
                    colors = listItemColors,
                )
                ListItem(
                    headlineContent = { Text("使用劇集序號過濾") },
                    Modifier.focusable(false).clickable(
                        enabled = state.enableEdit,
                    ) { state.filterByEpisodeSort = !state.filterByEpisodeSort },
                    supportingContent = { Text("要求資源標題包含劇集序號。適用於資料源可能搜到無關內容的情況。通常建議開啟") },
                    trailingContent = {
                        Switch(
                            state.filterByEpisodeSort, { state.filterByEpisodeSort = it },
                            enabled = state.enableEdit,
                        )
                    },
                    colors = listItemColors,
                )
            }

            Row(Modifier.padding(top = verticalSpacing, bottom = 12.dp)) {
                ProvideTextStyleContentColor(
                    MaterialTheme.typography.titleMedium,
                    MaterialTheme.colorScheme.primary,
                ) {
                    Text(SelectorConfigurationDefaults.STEP_NAME_3)
                }
            }

            SelectorConfigurationDefaults.MatchVideoSection(
                state,
                textFieldShape = textFieldShape,
                verticalSpacing = verticalSpacing,
            )

            kotlin.run {
                var showMenu by rememberSaveable { mutableStateOf(false) }
                ListItem(
                    headlineContent = { Text("標記解析度") },
                    Modifier.focusable(false).clickable(
                        enabled = state.enableEdit,
                    ) { showMenu = !showMenu },
                    supportingContent = { Text("將此資料源的資源都標記為該解析度。不影響查詢，只在播放器中選擇資料源時用做偏好和過濾選項。") },
                    trailingContent = {
                        TextButton(onClick = { showMenu = true }) {
                            Text(state.defaultResolution.displayName)
                        }
                        if (showMenu) {
                            DropdownMenu(showMenu, { showMenu = false }) {
                                for (resolution in Resolution.entries.asReversed()) {
                                    DropdownMenuItem(
                                        text = { Text(resolution.displayName) },
                                        onClick = {
                                            state.defaultResolution = resolution
                                            showMenu = false
                                        },
                                    )
                                }
                            }
                        }
                    },
                    colors = listItemColors,
                )
            }

            kotlin.run {
                var showMenu by rememberSaveable { mutableStateOf(false) }
                ListItem(
                    headlineContent = { Text("標記字幕語言") },
                    Modifier.focusable(false).clickable(
                        enabled = state.enableEdit,
                    ) { showMenu = !showMenu },
                    supportingContent = { Text("將此資料源的資源都標記為該字幕語言。不影響查詢，只在播放器中選擇資料源時用做偏好和過濾選項。") },
                    trailingContent = {
                        TextButton(onClick = { showMenu = true }) {
                            Text(state.defaultSubtitleLanguage.displayName)
                        }
                        if (showMenu) {
                            DropdownMenu(showMenu, { showMenu = false }) {
                                for (language in SubtitleLanguage.matchableEntries.asReversed()) {
                                    DropdownMenuItem(
                                        text = { Text(language.displayName) },
                                        onClick = {
                                            state.defaultSubtitleLanguage = language
                                            showMenu = false
                                        },
                                    )
                                }
                            }
                        }
                    },
                    colors = listItemColors,
                )
            }

            Row(Modifier.padding(top = verticalSpacing, bottom = 12.dp)) {
                ProvideTextStyleContentColor(
                    MaterialTheme.typography.titleMedium,
                    MaterialTheme.colorScheme.primary,
                ) {
                    Text("在播放器內選擇資源時")
                }
            }

            Column(Modifier, verticalArrangement = Arrangement.spacedBy(verticalSpacing)) {
                val conf = state.selectMediaConfig
                ListItem(
                    headlineContent = { Text("區分條目名稱") },
                    Modifier.focusable(false).clickable(
                        enabled = state.enableEdit,
                    ) { conf.distinguishSubjectName = !conf.distinguishSubjectName },
                    supportingContent = {
                        Text(
                            "關閉後，所有步驟 1 搜尋到的條目都將被視為同一個，它們的相同標題的劇集將會被去重。" +
                                    "開啟此項則不會這樣去重。\n" +
                                    "此選項不影響測試結果，影響播放器內選擇資料源時的結果。",
                        )
                    },
                    trailingContent = {
                        Switch(
                            conf.distinguishSubjectName, { conf.distinguishSubjectName = it },
                            enabled = state.enableEdit,
                        )
                    },
                    colors = listItemColors,
                )
                ListItem(
                    headlineContent = { Text("區分線路名稱") },
                    Modifier.focusable(false).clickable(
                        enabled = state.enableEdit,
                    ) { conf.distinguishChannelName = !conf.distinguishChannelName },
                    supportingContent = {
                        Text(
                            "關閉後，線路名稱不同，但只要標題相同的劇集就會被去重。" +
                                    "開啟此項則不會這樣去重。\n" +
                                    "此選項不影響測試結果，影響播放器內選擇資料源時的結果。",
                        )
                    },
                    trailingContent = {
                        Switch(
                            conf.distinguishChannelName, { conf.distinguishChannelName = it },
                            enabled = state.enableEdit,
                        )
                    },
                    colors = listItemColors,
                )
            }

            Row(Modifier.padding(top = verticalSpacing, bottom = 12.dp)) {
                ProvideTextStyleContentColor(
                    MaterialTheme.typography.titleMedium,
                    MaterialTheme.colorScheme.primary,
                ) {
                    Text("播放影片時")
                }
            }

            Column(Modifier, verticalArrangement = Arrangement.spacedBy(verticalSpacing)) {
                val conf = state.matchVideoConfig.videoHeaders
                OutlinedTextField(
                    conf.referer, { conf.referer = it },
                    Modifier.fillMaxWidth().moveFocusOnEnter(),
                    label = { Text("Referer") },
                    supportingText = { Text("播放影片時執行的 HTTP 請求的 Referer，可留空") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = textFieldShape,
                    enabled = state.enableEdit,
                )
                OutlinedTextField(
                    conf.userAgent, { conf.userAgent = it },
                    Modifier.fillMaxWidth().moveFocusOnEnter(),
                    label = { Text("User-Agent") },
                    supportingText = { Text("播放影片時執行的 HTTP 請求的 User-Agent") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = textFieldShape,
                    enabled = state.enableEdit,
                )
            }

            Row(Modifier.align(Alignment.End).padding(top = verticalSpacing, bottom = 12.dp)) {
                if (state.enableEdit) {
                    ProvideTextStyleContentColor(
                        MaterialTheme.typography.labelMedium,
                        MaterialTheme.colorScheme.outline,
                    ) {
                        Text("提示：修改自動儲存")
                    }
                }
            }
        }

    }
}


@Composable
private fun SelectorSubjectFormatSelectionButtonRow(
    state: SelectorConfigState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    SingleChoiceSegmentedButtonRow(modifier) {
        @Composable
        fun Btn(
            id: SelectorFormatId, index: Int,
            label: @Composable () -> Unit,
        ) {
            SegmentedButton(
                state.subjectFormatId == id,
                { state.subjectFormatId = id },
                SegmentedButtonDefaults.itemShape(index, state.allSubjectFormats.size),
                icon = { SegmentedButtonDefaults.Icon(state.subjectFormatId == id) },
                label = label,
                enabled = enabled,
            )
        }

        for ((index, format) in state.allSubjectFormats.withIndex()) {
            Btn(format.id, index) {
                Text(
                    when (format) { // type-safe to handle all formats
                        SelectorSubjectFormatA -> "單標籤"
                        SelectorSubjectFormatIndexed -> "多標籤"
                        SelectorSubjectFormatJsonPathIndexed -> "JsonPath"
                    },
                    softWrap = false,
                )
            }
        }
    }
}

@Composable
private fun SelectorChannelSelectionButtonRow(
    state: SelectorConfigState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    SingleChoiceSegmentedButtonRow(modifier) {
        @Composable
        fun Btn(
            id: SelectorFormatId, index: Int,
            label: @Composable () -> Unit,
        ) {
            SegmentedButton(
                state.channelFormatId == id,
                { state.channelFormatId = id },
                SegmentedButtonDefaults.itemShape(index, state.allChannelFormats.size),
                icon = { SegmentedButtonDefaults.Icon(state.channelFormatId == id) },
                label = label,
                enabled = enabled,
            )
        }

        for ((index, selectorChannelFormat) in state.allChannelFormats.withIndex()) {
            Btn(selectorChannelFormat.id, index) {
                Text(
                    when (selectorChannelFormat) { // type-safe to handle all formats
                        SelectorChannelFormatNoChannel -> "不區分線路"
                        SelectorChannelFormatIndexGrouped -> "線路分組"
                    },
                    softWrap = false,
                )
            }
        }
    }
}
