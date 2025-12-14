package me.him188.ani.app.ui.media

import androidx.compose.runtime.Stable
import me.him188.ani.datasources.api.Media
import me.him188.ani.datasources.api.SubtitleKind
import me.him188.ani.datasources.api.topic.FileSize.Companion.Unspecified
import me.him188.ani.datasources.api.topic.FileSize.Companion.bytes
import me.him188.ani.datasources.api.topic.Resolution
import me.him188.ani.datasources.api.topic.SubtitleLanguage
import kotlin.jvm.JvmName

object MediaDetailsRenderer {
    @JvmName("renderSubtitleKindNotNull")
    fun renderSubtitleKind(subtitleKind: SubtitleKind): String = renderSubtitleKind(subtitleKind as SubtitleKind?)!!

    fun renderSubtitleKind(
        subtitleKind: SubtitleKind?,
    ): String? {
        return when (subtitleKind) {
            SubtitleKind.EMBEDDED -> "內嵌"
            SubtitleKind.CLOSED -> "內封"
            SubtitleKind.EXTERNAL_PROVIDED -> "外掛"
            SubtitleKind.EXTERNAL_DISCOVER -> "未知"
            SubtitleKind.CLOSED_OR_EXTERNAL_DISCOVER -> "內封或未知"
            null -> null
        }
    }

    fun renderSubtitleLanguages(
        subtitleKind: SubtitleKind?,
        subtitleLanguageIds: List<String>,
    ): String = buildString {
        if (subtitleKind != null) {
            append("[")
            append(renderSubtitleKind(subtitleKind))
            append("] ")
        } else {
            if (subtitleLanguageIds.isEmpty()) {
                append("未知")
            }
        }

        for ((index, subtitleLanguageId) in subtitleLanguageIds.withIndex()) {
            append(renderSubtitleLanguage(subtitleLanguageId))
            if (index != subtitleLanguageIds.size - 1) {
                append(" ")
            }
        }
    }
}

@Stable
fun Media.renderProperties(): String {
    val properties = this.properties
    return listOfNotNull(
        properties.resolution,
        properties.subtitleLanguageIds.joinToString("/") { renderSubtitleLanguage(it) }
            .takeIf { it.isNotBlank() },
        properties.size.takeIf { it != 0.bytes && it != Unspecified },
        properties.alliance,
    ).joinToString(" · ")
}

fun renderSubtitleLanguage(id: String): String {
    return when (id) {
        SubtitleLanguage.ChineseCantonese.id -> "粵語"
        SubtitleLanguage.ChineseSimplified.id -> "簡中"
        SubtitleLanguage.ChineseTraditional.id -> "繁中"
        SubtitleLanguage.Japanese.id -> "日語"
        SubtitleLanguage.English.id -> "英語"
        else -> id
    }
}

fun renderResolution(id: String): String {
    return Resolution.tryParse(id)?.displayName ?: id
}

