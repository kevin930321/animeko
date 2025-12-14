/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.domain.mediasource

import me.him188.ani.app.data.network.toIntArray
import me.him188.ani.app.domain.mediasource.MediaListFilters.charsToDelete
import me.him188.ani.datasources.api.EpisodeSort
import me.him188.ani.datasources.api.topic.EpisodeRange
import me.him188.ani.test.TestFactory
import me.him188.ani.test.runDynamicTests
import kotlin.test.Test
import kotlin.test.assertEquals

class MediaListFiltersTest {
    @Test
    fun `charsToDelete does not contains space`() {
        // 不能意外地删除空格

        assertEquals(-1, charsToDelete.toIntArray().indexOf(' '.code))
        assertEquals(-1, charsToDelete.toIntArray().indexOf('\t'.code))
    }

    @TestFactory
    fun `removeSpecials removes spaces`() = runDynamicTests {
        add("when removeWhitespace = true") {
            assertEquals("abc", removeSpecials("a b c", removeWhitespace = true))
        }
        add("when removeWhitespace = false") {
            assertEquals("a b c", removeSpecials("a b c", removeWhitespace = false))
        }
    }

    @TestFactory
    fun `removeSpecials numbers`() = runDynamicTests {
        add("超元氣三姐妹") {
            assertEquals("超元氣三姐妹", removeSpecials("超元氣三姐妹"))
        }
        add("中二病也要談戀愛") {
            assertEquals("中二病也要談戀愛", removeSpecials("中二病也要談戀愛！"))
        }
    }

    @TestFactory
    fun `removeSpecials re`() = runDynamicTests {
        add("Re：從零開始的異世界生活") {
            assertEquals("Re：從零開始的異世界生活", removeSpecials("Re：從零開始的異世界生活"))
        }
        add("Re:從零開始的異世界生活") {
            assertEquals("Re 從零開始的異世界生活", removeSpecials("Re:從零開始的異世界生活"))
        }
        add("Re: 從零開始的異世界生活") {
            assertEquals(
                "Re  從零開始的異世界生活",
                removeSpecials("Re: 從零開始的異世界生活", removeWhitespace = false),
            )
            assertEquals("Re從零開始的異世界生活", removeSpecials("Re: 從零開始的異世界生活", removeWhitespace = true))
        }
        add("Re：CREATORS") {
            assertEquals("Re：CREATORS", removeSpecials("Re：CREATORS"))
        }
    }

    @TestFactory
    fun `removeSpecials movie`() = runDynamicTests {
        add("劇場版 re0") {
            assertEquals("劇場版 Re：從零開始的異世界生活", removeSpecials("劇場版 Re：從零開始的異世界生活"))
        }
        add("劇場版 with special") {
            assertEquals("劇場版 從零開始的異世界生活", removeSpecials("劇場版 從零開始的異世界生活()"))
        }
        add("劇場版 紫羅蘭") {
            assertEquals("劇場版 紫羅蘭永恆花園", removeSpecials("劇場版 紫羅蘭永恆花園"))
        }
        add("劇場版 suffix") {
            assertEquals("紫羅蘭永恆花園劇場版", removeSpecials("紫羅蘭永恆花園劇場版"))
        }
        add("劇場版 suffix with space") {
            assertEquals("紫羅蘭永恆花園 劇場版", removeSpecials("紫羅蘭永恆花園 劇場版"))
        }
    }

    @TestFactory
    fun `removeSpecials period`() = runDynamicTests {
        add("with period") {
            assertEquals("紫羅蘭永恆花園", removeSpecials("紫羅蘭永恆花園。"))
        }
        add("with comma") {
            assertEquals("紫羅蘭永恆花園", removeSpecials("紫羅蘭永恆花園，"))
        }
        add("with comma and space") {
            assertEquals("紫羅蘭永恆花園", removeSpecials("紫羅蘭永恆花園， "))
        }
    }

    @TestFactory
    fun `removeSpecials seasons`() = runDynamicTests {
        add("second season") {
            assertEquals("測試 第二季", removeSpecials("測試 第二季"))
        }
    }

    @TestFactory
    fun `removeSpecials leading specials`() = runDynamicTests {
        add("remove leading specials regardless of position") {
            assertEquals("測試 第二季", removeSpecials("~~~~~~~~~~!測試 第二季"))
        }
        add("remove infix specials") {
            // 需要两个非特殊字符后才会开始删除
            assertEquals("測!!!試    第二季", removeSpecials("測!!!試!!! 第二季"))
            assertEquals("測試       第二季", removeSpecials("測試!!!!!! 第二季"))
        }
    }

    @TestFactory
    fun `removeSpecials cases`() = runDynamicTests {
        fun case(
            expected: String,
            originalTitle: String,
            removeWhitespace: Boolean = false,
            replaceNumbers: Boolean = false,
        ) {
            add("$originalTitle -> $expected") {
                assertEquals(expected, removeSpecials(originalTitle, removeWhitespace, replaceNumbers))
            }
        }

        case(
            "香格里拉 弗隴提亞 屎作獵人向神作發起挑戰  第二季",
            "香格里拉·弗隴提亞～屎作獵人向神作發起挑戰～ 第二季",
            removeWhitespace = false,
        )
        case(
            "香格里拉弗隴提亞屎作獵人向神作發起挑戰第二季",
            "香格里拉·弗隴提亞～屎作獵人向神作發起挑戰～ 第二季",
            removeWhitespace = true,
        )
        case(
            "香格里拉開拓異境糞作獵手挑戰神作",
            "香格里拉・開拓異境～糞作獵手挑戰神作～",
            removeWhitespace = true,
        )
        case(
            "香格里拉 開拓異境 糞作獵手挑戰神作",
            "香格里拉・開拓異境～糞作獵手挑戰神作～",
            removeWhitespace = false,
        )
        case(
            "五等分的新娘∬",
            "五等分的新娘∬",
            removeWhitespace = true,
        )
        case(
            "newgame",
            "new game!!",
            removeWhitespace = true,
        )
        case(
            "理科生墜入情網故嘗試證明 r=1 sinθ ♡",
            "理科生墜入情網故嘗試證明[r=1-sinθ]♡",
            removeWhitespace = false,
        )
        case(
            "青出於藍 緣",
            "青出於藍～緣～",
            removeWhitespace = false,
        )
        case(
            "博人傳 火影次世代",
            "博人傳-火影次世代-",
            removeWhitespace = false,
        )
        case(
            "博人傳 火影次世代",
            "博人傳-火影次世代",
            removeWhitespace = false,
        )
        case(
            "博人傳 火影次世代",
            "博人傳—火影次世代—",
            removeWhitespace = false,
        )
    }

    @TestFactory
    fun `test ContainsSubjectName`() = runDynamicTests {
        fun case(title: String, subjectName: String, expected: Boolean) {
            add("$title matches $subjectName") {
                val context = MediaListFilterContext(
                    subjectNames = setOf(subjectName),
                    episodeSort = EpisodeSort(1),
                    null, null,
                )
                context.run {
                    assertEquals(
                        expected,
                        MediaListFilters.ContainsSubjectName.applyOn(
                            object : MediaListFilter.Candidate {
                                override val originalTitle: String get() = title
                                override val episodeRange: EpisodeRange? get() = null
                            },
                        ),
                    )
                }
            }
        }

        // subject matches title
        infix fun String.matches(title: String) {
            case(title, this, true)
        }

        // subject matches title
        infix fun String.mismatches(title: String) {
            case(title, this, false)
        }

        "哥特蘿莉偵探事件簿" matches "哥特蘿莉偵探事件簿"
        "哥特蘿莉偵探事件薄" matches "哥特蘿莉偵探事件簿"
        "敗犬女主太多了" matches "敗犬女主太多啦"

        "地獄少女第一季" mismatches "地。 ―關於地球的運動―"
        "地獄少女" mismatches "地。"
    }

    private fun removeSpecials(
        string: String,
        removeWhitespace: Boolean = false,
        replaceNumbers: Boolean = false,
    ) = MediaListFilters.removeSpecials(
        string,
        removeWhitespace = removeWhitespace,
        replaceNumbers = replaceNumbers,
    )
}
