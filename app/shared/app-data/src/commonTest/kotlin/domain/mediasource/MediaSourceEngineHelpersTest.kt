/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.domain.mediasource

import me.him188.ani.app.domain.mediasource.MediaSourceEngineHelpers.getSearchKeyword
import me.him188.ani.test.TestFactory
import me.him188.ani.test.runDynamicTests
import kotlin.test.assertEquals

class MediaSourceEngineHelpersTest {
    @TestFactory
    fun `getSearchKeyword cases`() = runDynamicTests {
        fun case(
            expected: String,
            originalTitle: String,
            removeSpecial: Boolean = true, // default configuration of SelectorMediaSource
            useOnlyFirstWord: Boolean = true,
        ) {
            add("$originalTitle -> $expected") {
                assertEquals(expected, getSearchKeyword(originalTitle, removeSpecial, useOnlyFirstWord))
            }
        }

        case("香格里拉", "香格里拉·弗隴提亞～屎作獵人向神作發起挑戰～ 第二季")
        case("異世界魔王與召喚少女的奴隸魔術Ω", "異世界魔王與召喚少女的奴隸魔術Ω")
        case("天降之物f", "天降之物f")
        case("五等分的新娘＊", "五等分的新娘＊")
        case("邪神與廚二病少女’", "邪神與廚二病少女’")
        case("打工吧", "打工吧！！魔王大人")
        case("理科生墜入情網故嘗試證明", "理科生墜入情網故嘗試證明[r=1-sinθ]♡")
        case("new", "new game!")
        case("new", "new game!!")
        case("命運石之門", "命運石之門 負荷領域的既視感")
        case("命運石之門", "劇場版 命運石之門 負荷領域的既視感")
        case("OVERLORD", "劇場版總集篇 OVERLORD 漆黑的英雄")
        case("Test", "Test OAD")
        case("測試", "測試OAD")
        case("Test", "OAD Test")
    }
}