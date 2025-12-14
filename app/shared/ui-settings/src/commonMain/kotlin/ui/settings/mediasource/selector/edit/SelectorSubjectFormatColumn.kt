/*
 * Copyright (C) 2024 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.ui.settings.mediasource.selector.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.him188.ani.app.domain.mediasource.web.format.SelectorSubjectFormat
import me.him188.ani.app.domain.mediasource.web.format.SelectorSubjectFormatA
import me.him188.ani.app.domain.mediasource.web.format.SelectorSubjectFormatIndexed
import me.him188.ani.app.domain.mediasource.web.format.SelectorSubjectFormatJsonPathIndexed
import me.him188.ani.app.ui.foundation.effects.moveFocusOnEnter

@Composable
internal fun SelectorSubjectConfigurationColumn(
    format: SelectorSubjectFormat<*>?,
    state: SelectorConfigState,
    textFieldShape: Shape,
    verticalSpacing: Dp,
    listItemColors: ListItemColors,
    modifier: Modifier = Modifier,
) {
    when (format) {
        SelectorSubjectFormatA -> Column(modifier) {
            Text(
                "單個表達式，選取一些 <a>，根據其 title 屬性或 text 確定名稱，href 屬性確定連結",
                Modifier,
                style = MaterialTheme.typography.labelLarge,
            )

            val conf = state.subjectFormatA
            OutlinedTextField(
                conf.selectLists, { conf.selectLists = it },
                Modifier.fillMaxWidth().moveFocusOnEnter().padding(top = verticalSpacing),
                label = { Text("提取條目列表") },
                supportingText = { Text("CSS Selector 表達式。期望返回一些 <a>，每個對應一個條目，將會讀取其 href 屬性和 text") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                shape = textFieldShape,
                isError = conf.selectListsIsError,
                enabled = state.enableEdit,
            )
            ListItem(
                headlineContent = { Text("優先選擇最短標題") },
                Modifier
                    .padding(top = (verticalSpacing - 8.dp).coerceAtLeast(0.dp))
                    .clickable(enabled = state.enableEdit) { conf.preferShorterName = !conf.preferShorterName },
                supportingContent = { Text("優先選擇滿足匹配的標題最短的條目。可避免為第一季匹配到第二季") },
                trailingContent = {
                    Switch(
                        conf.preferShorterName, { conf.preferShorterName = it },
                        enabled = state.enableEdit,
                    )
                },
                colors = listItemColors,
            )
        }

        SelectorSubjectFormatIndexed -> Column(modifier) {
            Text(
                "兩個 CSS Selector 表達式，分別選取條目名稱列表和連結列表，按順序一一對應",
                Modifier,
                style = MaterialTheme.typography.labelLarge,
            )
            val conf = state.subjectFormatIndex
            OutlinedTextField(
                conf.selectNames, { conf.selectNames = it },
                Modifier.fillMaxWidth().moveFocusOnEnter().padding(top = verticalSpacing),
                label = { Text("提取條目名稱列表") },
                supportingText = { Text("CSS Selector 表達式。選取條目名稱列表") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                shape = textFieldShape,
                isError = conf.selectNamesIsError,
                enabled = state.enableEdit,
            )
            OutlinedTextField(
                conf.selectLinks, { conf.selectLinks = it },
                Modifier.fillMaxWidth().moveFocusOnEnter().padding(top = verticalSpacing),
                label = { Text("提取條目連結列表") },
                supportingText = { Text("CSS Selector 表達式。選取連結列表") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                shape = textFieldShape,
                isError = conf.selectLinksIsError,
                enabled = state.enableEdit,
            )
            ListItem(
                headlineContent = { Text("優先選擇最短標題") },
                Modifier
                    .padding(top = (verticalSpacing - 8.dp).coerceAtLeast(0.dp))
                    .clickable(enabled = state.enableEdit) { conf.preferShorterName = !conf.preferShorterName },
                supportingContent = { Text("優先選擇滿足匹配的標題最短的條目。可避免為第一季匹配到第二季") },
                trailingContent = {
                    Switch(
                        conf.preferShorterName, { conf.preferShorterName = it },
                        enabled = state.enableEdit,
                    )
                },
                colors = listItemColors,
            )
        }

        SelectorSubjectFormatJsonPathIndexed -> Column(modifier) {
            Text(
                "兩個 JsonPath 表達式，分別選取條目名稱列表和連結列表，按順序一一對應",
                Modifier,
                style = MaterialTheme.typography.labelLarge,
            )
            val conf = state.subjectFormatJsonPathIndex
            OutlinedTextField(
                conf.selectNames, { conf.selectNames = it },
                Modifier.fillMaxWidth().moveFocusOnEnter().padding(top = verticalSpacing),
                label = { Text("提取條目名稱列表") },
                supportingText = { Text("""JsonPath 表達式。選取條目名稱列表。期望返回一個數組，每個元素對應一個名稱。支援嵌套結構，例如 ["a", "b"] 與 [{"any": "a"}, {"any": "b"}] 都可以解析為兩個名稱 a b""") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                shape = textFieldShape,
                isError = conf.selectNamesIsError,
                enabled = state.enableEdit,
            )
            OutlinedTextField(
                conf.selectLinks, { conf.selectLinks = it },
                Modifier.fillMaxWidth().moveFocusOnEnter().padding(top = verticalSpacing),
                label = { Text("提取條目連結列表") },
                supportingText = { Text("""JsonPath 表達式。選取連結列表。期望返回一個數組，每個元素對應一個連結。支援嵌套結構""") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                shape = textFieldShape,
                isError = conf.selectLinksIsError,
                enabled = state.enableEdit,
            )
            ListItem(
                headlineContent = { Text("優先選擇最短標題") },
                Modifier
                    .padding(top = (verticalSpacing - 8.dp).coerceAtLeast(0.dp))
                    .clickable(enabled = state.enableEdit) { conf.preferShorterName = !conf.preferShorterName },
                supportingContent = { Text("優先選擇滿足匹配的標題最短的條目。可避免為第一季匹配到第二季") },
                trailingContent = {
                    Switch(
                        conf.preferShorterName, { conf.preferShorterName = it },
                        enabled = state.enableEdit,
                    )
                },
                colors = listItemColors,
            )
        }

        null -> Column(modifier) {
            UnsupportedFormatIdHint(
                state.subjectFormatId,
                Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}
