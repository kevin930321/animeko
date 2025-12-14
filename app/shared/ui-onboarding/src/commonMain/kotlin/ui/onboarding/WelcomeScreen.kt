/*
 * Copyright (C) 2024-2025 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.him188.ani.app.ui.foundation.animation.WithContentEnterAnimation
import me.him188.ani.app.ui.foundation.layout.AniWindowInsets
import me.him188.ani.app.ui.foundation.layout.currentWindowAdaptiveInfo1
import me.him188.ani.app.ui.foundation.theme.AniThemeDefaults

@Composable
fun WelcomeScreen(
    onClickContinue: () -> Unit,
    contactActions: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = AniWindowInsets.forPageContent(),
    wizardLayoutParams: WizardLayoutParams =
        WizardLayoutParams.calculate(currentWindowAdaptiveInfo1().windowSizeClass)
) {
    Surface(color = AniThemeDefaults.pageContentBackgroundColor) {
        WelcomeScene(
            onClickContinue,
            contactActions = contactActions,
            wizardLayoutParams = wizardLayoutParams,
            modifier = modifier,
            windowInsets = windowInsets,
        )
    }
}

/**
 * 首次启动 APP 的欢迎界面, 在向导之前显示.
 */
@Composable
internal fun WelcomeScene(
    onClickContinue: () -> Unit,
    contactActions: @Composable () -> Unit,
    wizardLayoutParams: WizardLayoutParams,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = AniWindowInsets.forPageContent(),
) {
    Box(
        modifier = modifier, 
        contentAlignment = Alignment.Center
    ) {
        WithContentEnterAnimation(Modifier.wrapContentSize()) {
            Column(
                Modifier
                    .windowInsetsPadding(windowInsets)
                    .padding(
                        horizontal = wizardLayoutParams.horizontalPadding,
                        vertical = wizardLayoutParams.verticalPadding,
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text("歡迎使用 Animeko", style = MaterialTheme.typography.headlineMedium)

                    ProvideTextStyle(MaterialTheme.typography.bodyLarge) {
                        Row(Modifier.padding(top = 8.dp).align(Alignment.Start)) {
                            Text(
                                """一站式線上彈幕追番平臺 (簡稱 Ani)""",
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }

                    Column(
                        Modifier.padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
                            Text("""Ani 目前由愛好者組成的組織 OpenAni 和社羣貢獻者維護，完全免費，在 GitHub 上開源。""")

                            Text("""Ani 的目標是提供盡可能簡單且舒適的追番體驗。""")
                        }
                    }

                    contactActions()
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 64.dp)
                        .padding(top = 16.dp, bottom = 36.dp),
                ) {
                    Button(
                        onClick = onClickContinue,
                        modifier = Modifier.widthIn(300.dp),
                    ) {
                        Text("繼續")
                    }
                }
            }
        }
    }
}