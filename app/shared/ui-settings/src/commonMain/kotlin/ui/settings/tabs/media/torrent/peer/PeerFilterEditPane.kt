/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.ui.settings.tabs.media.torrent.peer

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowOutward
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.him188.ani.app.domain.torrent.peer.PeerFilterSubscription
import me.him188.ani.app.ui.foundation.animation.AniAnimatedVisibility
import me.him188.ani.app.ui.foundation.animation.LocalAniMotionScheme
import me.him188.ani.app.ui.foundation.text.ProvideTextStyleContentColor
import me.him188.ani.app.ui.richtext.RichText
import me.him188.ani.app.ui.richtext.rememberBBCodeRichTextState
import me.him188.ani.app.ui.settings.SettingsTab
import me.him188.ani.app.ui.settings.framework.components.SwitchItem
import me.him188.ani.app.ui.settings.framework.components.TextItem

@Composable
private fun BBCodeSupportingText(text: String, modifier: Modifier = Modifier) {
    val richTextState = rememberBBCodeRichTextState(text)
    RichText(richTextState.elements, modifier)
}

@Composable
private fun RuleEditItem(
    content: String,
    enabled: Boolean,
    supportingTextBBCode: String,
    onContentChange: (String) -> Unit,
    textFieldShape: Shape = MaterialTheme.shapes.extraSmall
) {
    val listItemColors = ListItemDefaults.colors(containerColor = Color.Transparent)
    ListItem(
        headlineContent = {
            OutlinedTextField(
                value = content,
                enabled = enabled,
                label = { Text("規則") },
                maxLines = 8,
                onValueChange = onContentChange,
                shape = textFieldShape,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        colors = listItemColors,
        supportingContent = {
            BBCodeSupportingText(supportingTextBBCode, Modifier.padding(8.dp))
        }
    )
}

@Composable
private fun renderLastLoaded(lastLoaded: PeerFilterSubscription.LastLoaded?): String {
    val stat = lastLoaded?.ruleStat
    val error = lastLoaded?.error

    if (lastLoaded == null || (stat == null && error == null)) {
        return "未更新，啟用以更新"
    }

    return buildString {
        if (stat != null) {
            append("包含 ${stat.ipRuleCount} 個 IP 過濾項，${stat.idRuleCount} 個 ID 過濾項和 ${stat.clientRuleCount} 個客戶端過濾項")
        }

        if (error != null) {
            if (stat == null) {
                append("載入錯誤：$error")
            } else {
                appendLine()
                append("更新錯誤：$error")
            }
        }
    }
}

@Composable
fun PeerFilterEditPane(
    state: PeerFilterSettingsState,
    showIpBlockingItem: Boolean,
    onClickIpBlockSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val list by state.subscriptions.collectAsStateWithLifecycle()
    
    SettingsTab(modifier.verticalScroll(rememberScrollState())) {
        Group(
            title = { Text("過濾規則訂閱") },
            actions = {
                val updatingSubs by state.updatingSubs.collectAsStateWithLifecycle(false)
                AnimatedContent(
                    updatingSubs,
                    transitionSpec = LocalAniMotionScheme.current.animatedContent.standard,
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    if (it) {
                        CircularProgressIndicator(Modifier.size(24.dp))
                    } else {
                        IconButton({ state.updateSubs() }) {
                            Icon(Icons.Rounded.Refresh, contentDescription = "重新整理全部")
                        }
                    }
                }
            },
        ) {
            val builtInSub = list.firstOrNull()
            val onCheckedChange by rememberUpdatedState { it: Boolean ->
                if (builtInSub != null) state.toggleSub(builtInSub.subscriptionId, it)
            }

            SwitchItem(
                onClick = { onCheckedChange(!(builtInSub?.enabled ?: false)) },
                title = { Text("使用內建過濾規則") },
                description = { Text(renderLastLoaded(list.firstOrNull()?.lastLoaded)) },
            ) {
                Switch(
                    builtInSub?.enabled ?: false,
                    onCheckedChange = onCheckedChange,
                    enabled = builtInSub != null,
                )
            }
        }

        Group(
            title = { Text("本地過濾規則") },
            description = { Text("除透過訂閱過濾規則外，還可以手動新增過濾規則") },
        ) {
            SwitchItem(
                title = { Text("過濾 IP 位址") },
                checked = state.ipFilterEnabled,
                onCheckedChange = { state.ipFilterEnabled = it },
            )
            AniAnimatedVisibility(visible = state.ipFilterEnabled) {
                RuleEditItem(
                    content = state.ipFiltersPattern,
                    enabled = true,
                    supportingTextBBCode = """
                        每行一條過濾規則，支援 IPv4 和 IPv6
                        支援以下格式：
                        * 無類別域間路由（CIDR）
                          例如：[code]10.0.0.1/24[/code] 將過濾從 [code]10.0.0.0[/code] 至 [code]10.0.0.255[/code] 的所有 IP
                          [code]ff06:1234::/64[/code] 將過濾從 [code]ff06:1234::[/code] 至 [code]ff06:1234::ffff:ffff:ffff:ffff[/code] 的所有 IP
                        * 萬用字元
                          例如：[code]10.0.12.*[/code] 將過濾從 [code]10.0.12.0[/code] 至 [code]10.0.12.255[/code] 的所有 IP
                          [code]ff06:1234::*[/code] 將過濾從 [code]ff06:1234::[/code] 至 [code]ff06:1234::ffff[/code] 的所有 IP
                          支援多級萬用字元，例如 [code]10.0.*.*[/code]
                        * 範圍表示
                          例如 [code]10.0.24.100-200[/code] 和 [code]ff06:1234::cafe-dead[/code]
                    """.trimIndent(),
                    onContentChange = { state.ipFiltersPattern = it },
                )
            }

            SwitchItem(
                title = { Text("過濾客戶端指紋") },
                checked = state.idFilterEnabled,
                onCheckedChange = { state.idFilterEnabled = it },
            )
            AniAnimatedVisibility(visible = state.idFilterEnabled) {
                Column {
                    RuleEditItem(
                        content = state.idFiltersRegex,
                        enabled = true,
                        supportingTextBBCode = """
                        每行一條過濾規則，僅支援使用正規表達式過濾
                        例如：[code]\-HP\d{4}\-[/code] 將封禁具有 -HPxxxx- 指紋的客戶端
                    """.trimIndent(),
                        onContentChange = { state.idFiltersRegex = it },
                    )
                    SwitchItem(
                        title = { Text("總是過濾異常指紋") },
                        checked = state.blockInvalidId,
                        onCheckedChange = { state.blockInvalidId = it },
                        description = {
                            BBCodeSupportingText("無論是否滿足規則, 都會封鎖指紋不符合 [code]-xxxxxx-[/code] 格式的客戶端")
                        },
                    )
                }
            }

            SwitchItem(
                title = { Text("過濾客戶端類型") },
                checked = state.clientFilterEnabled,
                onCheckedChange = { state.clientFilterEnabled = it },
            )
            AniAnimatedVisibility(visible = state.clientFilterEnabled) {
                RuleEditItem(
                    content = state.clientFiltersRegex,
                    enabled = true,
                    supportingTextBBCode = """
                        每行一條過濾規則，僅支援使用正規表達式過濾
                        例如：[code]go\.torrent(\sdev)?[/code] 將封禁百度網盤的離線下載客戶端
                    """.trimIndent(),
                    onContentChange = { state.clientFiltersRegex = it },
                )
            }

            AniAnimatedVisibility(visible = showIpBlockingItem) {
                Group(
                    title = { Text("黑名單") },
                    description = { Text("黑名單中的 Peer 總是會被封鎖，無論是否匹配過濾規則") },
                ) {
                    TextItem(
                        title = { Text("IP 黑名單設定") },
                        description = { Text("配置 IP 黑名單列表") },
                        action = {
                            IconButton(onClickIpBlockSettings) {
                                Icon(Icons.Rounded.ArrowOutward, null)
                            }
                        },
                        onClick = onClickIpBlockSettings,
                    )
                }
            }

            TextItem {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                ) {
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