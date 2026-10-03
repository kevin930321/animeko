/*
 * Copyright (C) 2024-2026 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.domain.chinese

import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.him188.ani.app.data.Res
import kotlin.concurrent.Volatile

/**
 * 基于 OpenCC 词典的简繁中文转换器，用于搜索数据源时简繁中文互通。
 */
object ChineseConverter {
    @Volatile
    var isLoaded: Boolean = false
        private set

    private val mutex = Mutex()
    private val lock = SynchronizedObject()
    @Volatile
    private var s2tDict = OpenCcDict()
    @Volatile
    private var t2sDict = OpenCcDict()

    /**
     * 确保词典已异步加载完毕。可在应用启动或数据源搜索前调用。
     */
    suspend fun ensureLoaded() {
        if (isLoaded) return
        mutex.withLock {
            if (isLoaded) return
            val s2t = OpenCcDict()
            val t2s = OpenCcDict()
            loadInternal(s2t, t2s)
            synchronized(lock) {
                s2tDict = s2t
                t2sDict = t2s
                isLoaded = true
            }
        }
    }

    private suspend fun loadInternal(s2t: OpenCcDict, t2s: OpenCcDict) {
        // S2T 词典 (包含台湾词汇与变体)
        loadDict(s2t, "files/opencc/STPhrases.txt")
        loadDict(s2t, "files/opencc/STCharacters.txt")
        loadDict(s2t, "files/opencc/TWPhrases.txt")
        loadDict(s2t, "files/opencc/TWVariants.txt")

        // T2S 词典 (先还原台湾变体短语, 再转简体)
        loadDict(t2s, "files/opencc/TWVariantsRevPhrases.txt")
        loadDict(t2s, "files/opencc/TWPhrasesRev.txt")
        loadDict(t2s, "files/opencc/TSPhrases.txt")
        loadDict(t2s, "files/opencc/TSCharacters.txt")
    }

    private suspend fun loadDict(dict: OpenCcDict, path: String) {
        val bytes = try {
            readOpenCcBytes(path)
        } catch (_: Throwable) {
            null
        } ?: return
        dict.loadFromLines(bytes.decodeToString().lineSequence())
    }

    /**
     * 将繁体中文转换为简体中文。词典未加载时返回原字符串。
     */
    fun toSimplified(text: String): String {
        if (text.isEmpty()) return text
        if (!isLoaded) {
            tryLoadBlocking()
        }
        return if (isLoaded) t2sDict.convert(text) else text
    }

    /**
     * 将简体中文转换为繁体中文。词典未加载时返回原字符串。
     */
    fun toTraditional(text: String): String {
        if (text.isEmpty()) return text
        if (!isLoaded) {
            tryLoadBlocking()
        }
        return if (isLoaded) s2tDict.convert(text) else text
    }

    /**
     * 转换为相反的中文形态（繁转简或简转繁）。
     * 若转换后字符串与原字符串相同（如纯日文或无对应简繁差异），返回 null。
     */
    fun convertOpposite(text: String): String? {
        if (text.isEmpty()) return null
        val simp = toSimplified(text)
        if (simp != text) return simp
        val trad = toTraditional(text)
        if (trad != text) return trad
        return null
    }

    /**
     * 获取字符串的所有有效简繁中文变体（包括原字符串）。
     */
    fun getVariants(text: String): List<String> {
        if (text.isBlank()) return listOf(text)
        val list = mutableListOf(text)
        val trad = toTraditional(text)
        if (trad != text && trad.isNotBlank()) list.add(trad)
        val simp = toSimplified(text)
        if (simp != text && simp != trad && simp.isNotBlank()) list.add(simp)
        return list
    }

    private fun tryLoadBlocking() {
        if (isLoaded) return
        val bytes = readOpenCcFallback("files/opencc/STCharacters.txt") ?: return
        synchronized(lock) {
            if (isLoaded) return
            try {
                val s2t = OpenCcDict()
                loadDictBlocking(s2t, "files/opencc/STPhrases.txt")
                loadDictBlocking(s2t, "files/opencc/STCharacters.txt")
                loadDictBlocking(s2t, "files/opencc/TWPhrases.txt")
                loadDictBlocking(s2t, "files/opencc/TWVariants.txt")

                val t2s = OpenCcDict()
                loadDictBlocking(t2s, "files/opencc/TWVariantsRevPhrases.txt")
                loadDictBlocking(t2s, "files/opencc/TWPhrasesRev.txt")
                loadDictBlocking(t2s, "files/opencc/TSPhrases.txt")
                loadDictBlocking(t2s, "files/opencc/TSCharacters.txt")

                s2tDict = s2t
                t2sDict = t2s
                isLoaded = true
            } catch (_: Throwable) {
            }
        }
    }

    private fun loadDictBlocking(dict: OpenCcDict, path: String) {
        val bytes = readOpenCcFallback(path) ?: return
        dict.loadFromLines(bytes.decodeToString().lineSequence())
    }
}

/**
 * OpenCC 前缀树匹配转换字典。
 */
class OpenCcDict {
    private class Node {
        val children = HashMap<Char, Node>(2)
        var replacement: String? = null
    }

    private val root = Node()
    private val singleCharMap = HashMap<Char, String>(4096)

    fun add(key: String, value: String) {
        if (key.length == 1) {
            singleCharMap[key[0]] = value
        } else {
            var curr = root
            for (i in 0 until key.length) {
                curr = curr.children.getOrPut(key[i]) { Node() }
            }
            curr.replacement = value
        }
    }

    fun loadFromLines(lines: Sequence<String>) {
        for (line in lines) {
            if (line.isEmpty() || line.startsWith('#')) continue
            val tabIdx = line.indexOf('\t')
            if (tabIdx == -1) continue
            val key = line.substring(0, tabIdx)
            val rest = line.substring(tabIdx + 1)
            val spaceIdx = rest.indexOf(' ')
            val value = if (spaceIdx == -1) rest else rest.substring(0, spaceIdx)
            if (key.isNotEmpty() && value.isNotEmpty()) {
                add(key, value)
            }
        }
    }

    fun convert(text: String): String {
        if (text.isEmpty()) return text
        val sb = StringBuilder(text.length)
        var i = 0
        while (i < text.length) {
            var curr = root
            var lastMatchLen = 0
            var lastMatchVal: String? = null
            var j = i
            while (j < text.length) {
                val next = curr.children[text[j]] ?: break
                curr = next
                if (curr.replacement != null) {
                    lastMatchLen = j - i + 1
                    lastMatchVal = curr.replacement
                }
                j++
            }

            if (lastMatchVal != null) {
                sb.append(lastMatchVal)
                i += lastMatchLen
            } else {
                val c = text[i]
                val rep = singleCharMap[c]
                if (rep != null) {
                    sb.append(rep)
                } else {
                    sb.append(c)
                }
                i++
            }
        }
        return sb.toString()
    }
}

internal suspend fun readOpenCcBytes(path: String): ByteArray {
    return try {
        Res.readBytes(path)
    } catch (e: Throwable) {
        readOpenCcFallback(path) ?: throw e
    }
}

internal expect fun readOpenCcFallback(path: String): ByteArray?
