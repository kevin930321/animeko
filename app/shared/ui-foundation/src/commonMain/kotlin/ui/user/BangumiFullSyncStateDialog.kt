/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import me.him188.ani.app.data.models.bangumi.BangumiSyncState
import me.him188.ani.app.ui.foundation.ProvideCompositionLocalsForPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BangumiFullSyncStateDialog(
    state: BangumiSyncState?,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        title = { Text("正在下載 Bangumi 收藏資料") },
        text = {
            Column {
                Text(renderBangumiSyncState(state))
                Spacer(modifier = Modifier.height(24.dp))
                if (state?.finished == false) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                } else {
                    LinearProgressIndicator({ 1f }, modifier = Modifier.fillMaxWidth())
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("此操作可能需要 5-15 分鐘時間，請耐心等待。在下載過程中，你可以正常使用其他功能。可手動刷新收藏列表查看最新進度。")
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onDismissRequest) {
                Text("在背景繼續")
            }
        },
        properties = DialogProperties(dismissOnClickOutside = false),
    )
}

private fun renderBangumiSyncState(state: BangumiSyncState?): String {
    return when (state) {
        null -> "準備中"
        BangumiSyncState.Preparing -> "正在獲取元資料"
        is BangumiSyncState.FetchingSubjects -> "(已完成 ${state.fetchedCount} 條) 正在獲取更多收藏列表"
        is BangumiSyncState.FetchingEpisodes -> "(已完成 ${state.fetchedCount} 條) 正在獲取觀看進度"
        is BangumiSyncState.Inserting -> "(已完成 ${state.savedCount} 條) 正在儲存"
        is BangumiSyncState.Finishing -> "(已完成 ${state.savedCount} 條) 正在完成"
        is BangumiSyncState.Finished -> {
            if (state.error != null) {
                "(已完成 ${state.savedCount} 條) 同步失敗，錯誤訊息如下：\n$state"
            } else {
                "(已完成 ${state.savedCount} 條) 同步成功"
            }
        }

        BangumiSyncState.Unsupported -> "進行中"
    }
}

@Composable
@Preview
private fun PreviewBangumiFullSyncDialogSaved() {
    ProvideCompositionLocalsForPreview {
        BangumiFullSyncStateDialog(
            state = BangumiSyncState.Inserting(123),
            onDismissRequest = {},
        )
    }
}


@Composable
@Preview
private fun PreviewBangumiFullSyncDialogSyncTimeline() {
    ProvideCompositionLocalsForPreview {
        BangumiFullSyncStateDialog(
            state = BangumiSyncState.Finishing(100),
            onDismissRequest = {},
        )
    }
}