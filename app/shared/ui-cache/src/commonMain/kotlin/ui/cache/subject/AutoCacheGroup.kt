/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.ui.cache.subject

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ViewList
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import me.him188.ani.app.ui.settings.framework.components.RowButtonItem
import me.him188.ani.app.ui.settings.framework.components.SettingsScope

@Composable
fun SettingsScope.AutoCacheGroup(
    onClickGlobalCacheManage: () -> Unit,
) {
//    Group(
//        title = { Text("自動緩存") },
//        description = {
//            Text("自動緩存未觀看的劇集")
//        },
//    ) {
//        var useGlobalSettings by remember { mutableStateOf(true) }
//        SwitchItem(
//            title = { Text("使用全局設置") },
//            description = { Text("關閉後可爲該番劇單獨設置 (暫不支持單獨設置)") },
//            checked = useGlobalSettings,
//            onCheckedChange = { useGlobalSettings = !useGlobalSettings },
//            enabled = false,
//        )
//
//        AniAnimatedVisibility(!useGlobalSettings) {
//            var sliderValue by remember { mutableFloatStateOf(0f) }
//            SliderItem(
//                title = { Text("最大自動緩存話數") },
//                description = {
//                    Row {
//                        Text(autoCacheDescription(sliderValue))
//                        if (sliderValue == 10f) {
//                            Text("可能會佔用大量空間", color = MaterialTheme.colorScheme.error)
//                        }
//                    }
//                },
//            ) {
//                Slider(
//                    value = sliderValue,
//                    onValueChange = { sliderValue = it },
//                    valueRange = 0f..10f,
//                    steps = 9,
//                    enabled = !useGlobalSettings,
//                )
//            }
//            HorizontalDividerItem()
//        }
//
//        AniAnimatedVisibility(useGlobalSettings) {
//            RowButtonItem(
//                onClick = onClickGlobalCacheSettings,
//                icon = { Icon(Icons.Rounded.ArrowOutward, null) },
//            ) { Text("查看全局設置") }
//        }
//
//    }
    RowButtonItem(
        onClick = onClickGlobalCacheManage,
        icon = { Icon(Icons.AutoMirrored.Rounded.ViewList, null) },
    ) { Text("管理全部快取") }
}
