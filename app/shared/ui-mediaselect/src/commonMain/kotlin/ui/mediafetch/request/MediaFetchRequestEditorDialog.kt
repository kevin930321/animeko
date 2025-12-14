/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.ui.mediafetch.request

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import me.him188.ani.app.ui.foundation.saveable.mutableStateSaver
import me.him188.ani.app.ui.foundation.widgets.LocalToaster
import me.him188.ani.datasources.api.source.MediaFetchRequest

/**
 * @see MediaFetchRequestEditor
 */
@Composable
fun MediaFetchRequestEditorDialog(
    fetchRequest: MediaFetchRequest,
    onDismissRequest: () -> Unit,
    onFetchRequestChange: (MediaFetchRequest) -> Unit,
) {
    var editingRequest by rememberSaveable(
        fetchRequest,
        saver = mutableStateSaver(EditingMediaFetchRequest.Saver),
    ) {
        mutableStateOf(fetchRequest.toEditingMediaFetchRequest())
    }
    var showConfirmDiscard by rememberSaveable { mutableStateOf(false) }
    val onDismissRequestWrapped = {
        val hasChange = editingRequest != fetchRequest.toEditingMediaFetchRequest()
        if (hasChange) {
            showConfirmDiscard = true
        } else {
            onDismissRequest()
        }
    }

    val toaster = LocalToaster.current

    AlertDialog(
        onDismissRequestWrapped,
        confirmButton = {
            TextButton(
                {
                    editingRequest.toMediaFetchRequestOrNull()?.let {
                        onDismissRequestWrapped()
                        onFetchRequestChange(it)
                    } ?: toaster.toast("請求無效，請檢查")
                },
                enabled = editingRequest.toMediaFetchRequestOrNull() != null,
            ) {
                Text("儲存並重新整理")
            }
        },
        dismissButton = {
            TextButton(onDismissRequestWrapped) {
                Text("取消")
            }
        },
        title = {
            Text("編輯查詢請求")
        },
        text = {
            MediaFetchRequestEditor(
                editingRequest,
                { editingRequest = it },
                Modifier.fillMaxWidth(),
            )
        },
    )

    if (showConfirmDiscard) {
        AlertDialog(
            onDismissRequest = {
                showConfirmDiscard = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmDiscard = false
                        onDismissRequest()
                    },
                ) {
                    Text("捨棄", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showConfirmDiscard = false
                    },
                ) {
                    Text("繼續編輯")
                }
            },
            icon = {
                Icon(
                    Icons.Rounded.Delete, null,
                    tint = MaterialTheme.colorScheme.error,
                )
            },
            text = {
                Text("有未儲存的編輯，要捨棄編輯嗎？")
            },
        )
    }
}
