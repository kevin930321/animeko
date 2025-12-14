/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.danmaku.api

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import me.him188.ani.danmaku.api.provider.DanmakuEpisodeWithSubject
import me.him188.ani.danmaku.api.provider.DanmakuMatchers
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class DanmakuMatcherTest {
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val testData = json.decodeFromString(
        ListSerializer(DanmakuEpisodeWithSubject.serializer()),
        """
        [{"id":"175640013","subjectName":"迷宮飯","episodeName":"第13話 炎龍3/良藥"},{"id":"181620013","subjectName":"地下城裏的人們","episodeName":"第13話"},{"id":"108940013","subjectName":"在地下城尋求邂逅是否搞錯了什麼","episodeName":"第13話 眷族物語(Familiar Myth)"},{"id":"140890013","subjectName":"飯沼。","episodeName":"第13話 ハンバーガー食べよう... 前編"},{"id":"86900001","subjectName":"ToHeart2 迷宮旅人","episodeName":"第1話 最壞的災難"},{"id":"86900002","subjectName":"ToHeart2 迷宮旅人","episodeName":"第2話 たいせつなもの"},{"id":"145590013","subjectName":"阿拉德：逆轉之輪","episodeName":"第13話 希望"},{"id":"119410001","subjectName":"在地下城尋求邂逅是否搞錯了什麼 OVA","episodeName":"第1話 在地下城尋求溫泉是否搞錯了什麼"},{"id":"27690001","subjectName":"1月にはChristmas","episodeName":"第1話 OVA"},{"id":"27130003","subjectName":"奇兵大冒險 風之塔","episodeName":"第3話 おまたせコーダ"},{"id":"134610013","subjectName":"豬豬俠 超星萌寵","episodeName":"第13話 穿越阿五的回憶"},{"id":"27130001","subjectName":"奇兵大冒險 風之塔","episodeName":"第1話 よろしくプレリュード"},{"id":"27130002","subjectName":"奇兵大冒險 風之塔","episodeName":"第2話 まだまだカプリッチオ"},{"id":"166930013","subjectName":"如果歷史是一羣喵","episodeName":"第13話"},{"id":"4340013","subjectName":"中華小當家","episodeName":"第13話 鯰魚面完成！命運的審判"},{"id":"23620013","subjectName":"銀河飄流華爾分13","episodeName":"第13話 絶體絶命! さらば愛しきJr.たち"},{"id":"57460013","subjectName":"骷髏13","episodeName":"第13話 交叉的夾角"},{"id":"18790001","subjectName":"雲界的迷宮","episodeName":"第1話 Volume 1"},{"id":"18790002","subjectName":"雲界的迷宮","episodeName":"第2話 Volume 2"},{"id":"69820001","subjectName":"町一番のけちんぼう","episodeName":"第1話 TV Special"},{"id":"82670001","subjectName":"寒月一凍悪霊斬り","episodeName":"第1話 Volume 1"},{"id":"82670002","subjectName":"寒月一凍悪霊斬り","episodeName":"第2話 Volume 2"},{"id":"127970001","subjectName":"吸血鬼僕人 Alice in the Garden","episodeName":"劇場版"},{"id":"10760001","subjectName":"骷髏13 劇場版","episodeName":"第1話 骷髏13 劇場版"},{"id":"10770001","subjectName":"骷髏13 女王蜂","episodeName":"第1話 OVA"}]
    """.trimIndent(),
    ).shuffled(Random(123))

    @Test
    fun testMatch() {
        assertEquals(
            "175640013",
            DanmakuMatchers.mostRelevant("迷宮飯", "第13話 炎龍3").match(testData)?.id,
        )
    }

    @Test
    fun testEmpty() {
        assertEquals(
            null,
            DanmakuMatchers.mostRelevant("迷宮飯", "第13話 炎龍3").match(emptyList()),
        )
    }
}