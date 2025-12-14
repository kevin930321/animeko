/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.domain.mediasource

import me.him188.ani.datasources.api.EpisodeSort
import me.him188.ani.datasources.api.topic.EpisodeRange
import me.him188.ani.test.DynamicTestsBuilder
import me.him188.ani.test.TestContainer
import me.him188.ani.test.TestFactory
import me.him188.ani.test.runDynamicTests
import kotlin.test.assertEquals

@TestContainer
class MediaListFilterEpisodeFilterTest {

    @TestFactory
    fun `base cases`() = runDynamicTests {
        case(
            "simple sort match",
            subjectName = "來自深淵 烈日的黃金鄉",
            episodeSort = EpisodeSort(1),
            episodeName = "測試",
            mediaEpisodeSort = EpisodeSort(1),
            expected = true,
        )
        case(
            "simple sort not match",
            subjectName = "來自深淵 烈日的黃金鄉",
            episodeSort = EpisodeSort(1),
            episodeName = "測試",
            mediaEpisodeSort = EpisodeSort(2),
            expected = false,
        )
        case(
            "simple ep match",
            subjectName = "來自深淵 烈日的黃金鄉",
            episodeSort = EpisodeSort(13),
            episodeEp = EpisodeSort(1),
            episodeName = "測試",
            mediaEpisodeSort = EpisodeSort(1),
            expected = true,
        )
        case(
            "both ep and sort does not match",
            subjectName = "來自深淵 烈日的黃金鄉",
            episodeSort = EpisodeSort(13),
            episodeEp = EpisodeSort(1),
            episodeName = "測試",
            mediaEpisodeSort = EpisodeSort(2),
            expected = false,
        )
    }

    @TestFactory
    fun `match episode name`() = runDynamicTests {
        case(
            "match 電影版",
            subjectName = "來自深淵",
            episodeSort = EpisodeSort("電影版"),
            episodeName = "電影版",
            mediaEpisodeSort = null,
            mediaTitle = "來自深淵 電影版",
            expected = true,
        )
        case(
            "match OVA",
            subjectName = "來自深淵",
            episodeSort = EpisodeSort("OVA"),
            episodeName = "OVA",
            mediaEpisodeSort = null,
            mediaTitle = "來自深淵 OVA",
            expected = true,
        )
        case(
            "match episode name - control",
            subjectName = "來自深淵",
            episodeSort = EpisodeSort(1),
            episodeName = "電影版",
            mediaEpisodeSort = null,
            mediaTitle = "來自深淵 01",
            expected = false,
        )
    }

    @TestFactory
    fun `subjectName contains episodeName`() = runDynamicTests {
        case(
            "true case",
            subjectName = "來自深淵 烈日的黃金鄉",
            episodeSort = EpisodeSort(12), // 在看 12, 所以可以匹配 12
            episodeName = "黃金",
            mediaEpisodeSort = EpisodeSort(12),
            expected = true,
        )
        case(
            "fail case",
            subjectName = "來自深淵 烈日的黃金鄉",
            episodeSort = EpisodeSort(11), // 在看 11, 但是标题叫"黃金", 不能匹配到
            episodeName = "黃金",
            mediaEpisodeSort = EpisodeSort(12),
            expected = false,
        )
    }

    private fun DynamicTestsBuilder.case(
        testName: String,
        subjectName: String,
        episodeSort: EpisodeSort,
        episodeName: String?,
        expected: Boolean,
        mediaEpisodeSort: EpisodeSort?,
        mediaTitle: String = "$subjectName $mediaEpisodeSort",
        episodeEp: EpisodeSort? = null,
    ) {
        add(testName) {
            val context = MediaListFilterContext(
                subjectNames = setOf(subjectName),
                episodeSort = episodeSort,
                episodeEp = episodeEp,
                episodeName = episodeName,
            )
            context.run {
                assertEquals(
                    expected,
                    MediaListFilters.ContainsAnyEpisodeInfo.applyOn(
                        object : MediaListFilter.Candidate {
                            override val originalTitle: String get() = mediaTitle
                            override val subjectName: String get() = subjectName
                            override val episodeRange: EpisodeRange?
                                get() = mediaEpisodeSort?.let {
                                    EpisodeRange.single(it)
                                }
                        },
                    ),
                )
            }
        }
    }
}