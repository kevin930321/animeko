/*
 * Copyright (C) 2024-2026 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.domain.chinese

import kotlinx.coroutines.test.runTest
import me.him188.ani.app.data.models.subject.SubjectInfo
import me.him188.ani.app.domain.mediasource.MediaListFilterContext
import me.him188.ani.app.domain.mediasource.MediaListFilters
import me.him188.ani.app.domain.mediasource.StringMatcher
import me.him188.ani.datasources.api.DefaultMedia
import me.him188.ani.datasources.api.EpisodeSort
import me.him188.ani.datasources.api.MediaProperties
import me.him188.ani.datasources.api.MediaSourceKind
import me.him188.ani.datasources.api.MediaSourceLocation
import me.him188.ani.datasources.api.topic.EpisodeRange
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ChineseConverterTest {

    @Test
    fun testConversionAndInteroperability() = runTest {
        ChineseConverter.ensureLoaded()

        // S2T
        assertEquals("孤獨搖滾", ChineseConverter.toTraditional("孤独摇滚"))
        assertEquals("無職轉生", ChineseConverter.toTraditional("无职转生"))
        assertEquals("間諜過家家", ChineseConverter.toTraditional("间谍过家家"))
        assertEquals("葬送的芙莉蓮", ChineseConverter.toTraditional("葬送的芙莉莲"))

        // T2S
        assertEquals("孤独摇滚", ChineseConverter.toSimplified("孤獨搖滾"))
        assertEquals("无职转生", ChineseConverter.toSimplified("無職轉生"))
        assertEquals("间谍过家家", ChineseConverter.toSimplified("間諜過家家"))
        assertEquals("葬送的芙莉莲", ChineseConverter.toSimplified("葬送的芙莉蓮"))
        assertEquals("咒术回战", ChineseConverter.toSimplified("咒術迴戰"))
        assertEquals("咒术回战", ChineseConverter.toSimplified("咒術回戰"))

        // convertOpposite
        assertEquals("孤獨搖滾", ChineseConverter.convertOpposite("孤独摇滚"))
        assertEquals("孤独摇滚", ChineseConverter.convertOpposite("孤獨搖滾"))
        assertEquals(null, ChineseConverter.convertOpposite("ぼっち・ざ・ろっく！"))

        // getVariants
        val variants = ChineseConverter.getVariants("无职转生")
        assertTrue("无职转生" in variants)
        assertTrue("無職轉生" in variants)
    }

    @Test
    fun testSpecialEqualsTraditionalAndSimplified() = runTest {
        ChineseConverter.ensureLoaded()

        assertTrue(MediaListFilters.specialEquals("無職轉生", "无职转生"))
        assertTrue(MediaListFilters.specialEquals("孤獨搖滾！", "孤独摇滚"))
        assertTrue(MediaListFilters.specialEquals("間諜過家家", "间谍过家家"))
        assertTrue(MediaListFilters.specialEquals("葬送的芙莉蓮", "葬送的芙莉莲"))
        assertTrue(MediaListFilters.specialEquals("咒術迴戰 第二季", "咒术回战 第2季"))
    }

    @Test
    fun testSpecialContainsTraditionalAndSimplified() = runTest {
        ChineseConverter.ensureLoaded()

        assertTrue(MediaListFilters.specialContains("【10月】孤獨搖滾！ 第01話", "孤独摇滚"))
        assertTrue(MediaListFilters.specialContains("【悠哈璃羽字幕社】无职转生 01", "無職轉生"))
    }

    @Test
    fun testStringMatcherMatchRate() = runTest {
        ChineseConverter.ensureLoaded()

        assertEquals(100, StringMatcher.calculateMatchRate("無職轉生", "无职转生"))
        assertEquals(100, StringMatcher.calculateMatchRate("孤獨搖滾！", "孤独摇滚！"))
    }

    @Test
    fun testContainsSubjectNameFilter() = runTest {
        ChineseConverter.ensureLoaded()

        val context = MediaListFilterContext(
            subjectNames = setOf("孤独摇滚"),
            episodeSort = EpisodeSort(1),
        )

        val media = DefaultMedia(
            mediaId = "test.1",
            mediaSourceId = "test",
            originalUrl = "",
            download = null,
            originalTitle = "【幻樱字幕组】孤獨搖滾！ [01][GB_MP4][1080P]",
            publishedTime = 0L,
            properties = MediaProperties(
                subjectName = "孤獨搖滾！",
                episodeName = null,
                subtitleLanguageIds = emptyList(),
                resolution = "1080P",
                alliance = null,
            ),
            episodeRange = EpisodeRange.single(EpisodeSort(1)),
            location = MediaSourceLocation.Online,
            kind = MediaSourceKind.WEB,
        )

        assertTrue(MediaListFilters.ContainsSubjectName.applyOn(media, context))
    }

    @Test
    fun testSubjectInfoAllNamesContainsVariants() = runTest {
        ChineseConverter.ensureLoaded()

        val info = SubjectInfo.Empty.copy(
            nameCn = "孤独摇滚",
            name = "ぼっち・ざ・ろっく！",
        )

        val allNames = info.allNames
        assertEquals("孤独摇滚", allNames.first()) // nameCn must be first
        assertTrue("孤獨搖滾" in allNames)
        assertNotNull(allNames.find { it == "ぼっち・ざ・ろっく！" })
    }
}
