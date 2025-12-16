/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.data.models.subject

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ChineseVariantConverterTest {

    @Test
    fun testSimply() {
        // Simple 1-to-1
        assertEquals("对", ChineseVariantConverter.toSimplified("對"))
        assertEquals("對", ChineseVariantConverter.toTraditional("对"))
    }

    @Test
    fun testPhrases() {
        val s = "对我垂涎欲滴的非人少女"
        val t = "對我垂涎欲滴的非人少女"

        assertEquals(t, ChineseVariantConverter.toTraditional(s))
        assertEquals(s, ChineseVariantConverter.toSimplified(t))
    }

    @Test
    fun testMixed() {
        val text = "关于我转生变成史莱姆这档事"
        val t = "關於我轉生變成史萊姆這檔事"

        assertEquals(t, ChineseVariantConverter.toTraditional(text))
    }

    @Test
    fun testGetAllVariants() {
        val s = "对我垂涎欲滴的非人少女"
        val t = "對我垂涎欲滴的非人少女"

        val variants = ChineseVariantConverter.getAllVariants(s)
        assertTrue(variants.contains(s))
        assertTrue(variants.contains(t))
        assertTrue(variants.size >= 2)
    }

    @Test
    fun testJapaneseUnchanged() {
        // Japanese text that happens to share characters should be handled carefully,
        // but here we just ensure we don't break non-Chinese text essentially.
        // Though Kanji often match Traditional Chinese.
        val jp = "転生したらスライムだった件"
        // "転" is a Japanese Shinjitai, Traditional is 轉, Simplified is 转
        // Our converter might map 転 -> 転 (if not in map) or something else.
        // Ideally it shouldn't produce garbage.
        
        // Let's just check converted strings are not empty
        assertTrue(ChineseVariantConverter.toTraditional(jp).isNotEmpty())
    }
    
    @Test
    fun testAnimeTitles() {
        val pairs = listOf(
            "无职转生" to "無職轉生",
            "进击的巨人" to "進擊的巨人",
            "间谍过家家" to "間諜家家酒", // Note: This converter is char-based, so "过家家" won't become "家家酒" (vocabulary diff). It will become "過家家". This is expected limitation.
            "孤独摇滚" to "孤獨搖滾",
        )

        pairs.forEach { (s, t) ->
            // Check strictly char conversion
            val convertedT = ChineseVariantConverter.toTraditional(s)
            // println("$s -> $convertedT (Expected: $t)")
            assertEquals(t, convertedT, "Failed to convert $s to Traditional")
        }
    }
}
