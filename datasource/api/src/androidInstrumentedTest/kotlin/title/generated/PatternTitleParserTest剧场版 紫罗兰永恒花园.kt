// @formatter:off
@file:Suppress(
  "FunctionName",
  "ClassName",
  "RedundantVisibilityModifier",
  "PackageDirectoryMismatch",
  "NonAsciiCharacters",
  "SpellCheckingInspection",
)

import me.him188.ani.datasources.api.SubtitleKind
import me.him188.ani.datasources.api.title.PatternBasedTitleParserTestSuite
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * 原名: `剧场版 紫罗兰永恒花园`
 * 数据源: `dmhy`
 *
 * 由 `test-codegen` 的 `GenerateTests.kt` 生成, 不要手动修改!
 * 如果你优化了解析器, 这些 test 可能会失败, 请检查是否它是因为以前解析错误而现在解析正确了. 
 * 如果是, 请更新测试数据: 执行 `GenerateTests.kt`.
 */
public class `PatternTitleParserTest剧场版 紫罗兰永恒花园` : PatternBasedTitleParserTestSuite() {
  @Test
  public fun `651397-615844-588811-588810-587366-587364-585468-583084-582838-582836`() {
    kotlin.run {
    val r =
        parse("[VCB-Studio] 劇場版 紫羅蘭永恆花園 / Gekijouban Violet Evergarden 10-bit 2160p/1080p HEVC BDRip [MOVIE]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("1080P", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
    kotlin.run {
    val r = parse("[雲光字幕組]劇場版 紫羅蘭永恆花園 Violet Evergarden the Movie [簡體雙語][4K SDR]招募時軸翻譯")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS, JPN", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("null", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
    kotlin.run {
    val r = parse("【幻櫻字幕組】【劇場版紫羅蘭永恆花園 Violet Evergarden The Movie】【BDrip】【GB_MP4】【1280X720】")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("720P", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
    kotlin.run {
    val r = parse("【幻櫻字幕組】【劇場版紫羅蘭永恆花園 Violet Evergarden The Movie】【BDrip】【GB_MP4】【1920X1080】")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("1080P", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("【幻櫻字幕組】【劇場版】【紫羅蘭永恆花園外傳：永遠與自動手記人偶 Violet Evergarden Eien to Jidou Shuki Ningyou】【BDrip】【GB_MP4】【1920X1080】")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("1080P", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("【幻櫻字幕組】【劇場版】【紫羅蘭永恆花園外傳：永遠與自動手記人偶 Violet Evergarden Eien to Jidou Shuki Ningyou】【BDrip】【GB_MP4】【1280X720】")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("720P", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("[.subbers project] 劇場版 紫羅蘭永恆花園 / 劇場版 ヴァイオレット・エヴァーガーデン / Violet Evergarden the Movie [BDRip][2K SDR][簡繁日字幕內封][rev2]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS, CHT, JPN", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("null", r.resolution.toString())
    assertEquals(SubtitleKind.CLOSED, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("[.subbers project] 劇場版 紫羅蘭永恆花園 / 劇場版 ヴァイオレット・エヴァーガーデン / Violet Evergarden the Movie [BDRip][4K HDR+2K SDR][簡繁日字幕內封](附BD及劇場特典掃圖、相關音樂、小說翻譯等)")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS, CHT, JPN", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("null", r.resolution.toString())
    assertEquals(SubtitleKind.CLOSED, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("【千夏字幕組】【劇場版 紫羅蘭永恆花園/薇爾莉特·伊芙嘉登_Violet Evergarden the Movie】[劇場版][BDRip_Full HD_HEVC][簡繁內封]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS, CHT", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("null", r.resolution.toString())
    assertEquals(SubtitleKind.CLOSED, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("【千夏字幕組】【劇場版 紫羅蘭永恆花園/薇爾莉特·伊芙嘉登_Violet Evergarden the Movie】[劇場版][BDRip_Full HD_AVC][簡體]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("null", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
  }

  @Test
  public fun `582144-582130-582129-581912-571322-571317-549455-537654-537653-537305`() {
    kotlin.run {
    val r =
        parse("[DBD-Raws][劇場版 紫羅蘭永恆花園/Violet Evergarden The Movie/劇場版 ヴァイオレット・エヴァーガーデ][1080P][BDRip][HEVC-10bit][FLAC][MKV]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("1080P", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("[DBD-Raws][4K_HDR][劇場版 紫羅蘭永恆花園/Violet Evergarden The Movie/劇場版 ヴァイオレット・エヴァーガーデ][2160P][BDRip][HEVC-10bit][FLAC][MKV]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("4K", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
    kotlin.run {
    val r = parse("【幻之字幕組】劇場版 紫羅蘭永恆花園[Violet Evergarden the Movie] [1080P][GB][BDrip]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("1080P", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("[.subbers project] 劇場版 紫羅蘭永恆花園 / 劇場版 ヴァイオレット・エヴァーガーデン / Violet Evergarden the Movie [BDRip][FullHD][簡體中文字幕內嵌]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("null", r.resolution.toString())
    assertEquals(SubtitleKind.EMBEDDED, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("[森之屋動畫組][WEBRip][劇場版 紫羅蘭永恆花園 / 劇場版ヴァイオレット・エヴァーガーデン / MOVIE Violet Evergarden][1920x804][x.264 AAC MP4][俄語音軌][內嵌簡中]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("null", r.resolution.toString())
    assertEquals(SubtitleKind.EMBEDDED, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("[森之屋動畫組][WEBRip][劇場版 紫羅蘭永恆花園 / 劇場版ヴァイオレット・エヴァーガーデン / MOVIE Violet Evergarden][1920x804][x.264 AAC MP4][日語劇場錄音音軌（先行版）][內嵌簡中]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS, JPN", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("null", r.resolution.toString())
    assertEquals(SubtitleKind.EMBEDDED, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("【MCE漢化組】[劇場版 紫羅蘭永恆花園 冒頭影像 / Violet Evergarden 2020][Trial Version][簡體][1080P][x264 AAC]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("1080P", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("【千夏字幕組】【紫羅蘭永恆花園·外傳 —永遠與自動手記人偶—_Violet Evergarden Side Story -Eternity and Auto Memory Doll-】[劇場版][BDRip_1080p_HEVC][簡繁外掛]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS, CHT", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("1080P", r.resolution.toString())
    assertEquals(SubtitleKind.EXTERNAL_DISCOVER, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("【千夏字幕組】【紫羅蘭永恆花園·外傳 —永遠與自動手記人偶—_Violet Evergarden Side Story -Eternity and Auto Memory Doll-】[劇場版][BDRip_1080p_AVC][簡體]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("1080P", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("[Moozzi2] 劇場版 紫羅蘭永恆花園外傳:永遠與自動手記人偶 Violet Evergarden Eien to Jidou Shuki Ningyou (BD 1920x804 x264-10Bit 4Audio)")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("null", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
  }

  @Test
  public fun `537233-537232`() {
    kotlin.run {
    val r =
        parse("【幻之字幕組】劇場版 紫羅蘭永恆花園外傳：永遠與自動手記人偶 Violet Evergarden Gaiden: Eien to Jidou Shuki Ningyou [1080P][雙語][CHT&JPN][BDrip][AVC AAC YUV420P8]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHT, JPN", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("1080P", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
    kotlin.run {
    val r =
        parse("【幻之字幕組】劇場版 紫羅蘭永恆花園外傳：永遠與自動手記人偶 Violet Evergarden Gaiden: Eien to Jidou Shuki Ningyou [1080P][雙語][CHS&JPN][BDrip][AVC AAC YUV420P8]")
    assertEquals("S?", r.episodeRange.toString())
    assertEquals("CHS, JPN", r.subtitleLanguages.sortedBy { it.id }.joinToString { it.id })
    assertEquals("1080P", r.resolution.toString())
    assertEquals(null, r.subtitleKind)
    }
  }
}

// @formatter:on
