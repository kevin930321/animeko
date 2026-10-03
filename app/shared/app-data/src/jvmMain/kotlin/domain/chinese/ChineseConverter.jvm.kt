/*
 * Copyright (C) 2024-2026 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.domain.chinese

internal actual fun readOpenCcFallback(path: String): ByteArray? {
    val normalized = if (path.startsWith("/")) path else "/$path"
    val stream = ChineseConverter::class.java.getResourceAsStream(normalized)
        ?: ChineseConverter::class.java.classLoader?.getResourceAsStream(path)
        ?: Thread.currentThread().contextClassLoader?.getResourceAsStream(path)
        ?: Thread.currentThread().contextClassLoader?.getResourceAsStream(normalized)
    return stream?.use { it.readBytes() }
}
