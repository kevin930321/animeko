package me.him188.ani.app.ui.settings.tabs.media

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import me.him188.ani.app.data.models.preference.VideoResolverSettings
import me.him188.ani.app.data.models.preference.WebViewDriver
import me.him188.ani.app.ui.foundation.LocalPlatform
import me.him188.ani.app.ui.settings.framework.SettingsState
import me.him188.ani.app.ui.settings.framework.components.DropdownItem
import me.him188.ani.app.ui.settings.framework.components.SettingsScope
import me.him188.ani.utils.platform.isDesktop

@Composable
internal fun SettingsScope.VideoResolverGroup(
    videoResolverSettingsState: SettingsState<VideoResolverSettings>,
    modifier: Modifier = Modifier,
) {
    // There are not many options for the player.
    if (!LocalPlatform.current.isDesktop()) {
        return
    }

    val config by videoResolverSettingsState

    Group(
        title = {
            Text("影片解析")
        },
        modifier = modifier,
    ) {
        val itemText: @Composable (WebViewDriver) -> Unit = {
            when (it) {
                WebViewDriver.CHROME -> Text("Chrome")
                WebViewDriver.EDGE -> Text("Edge 瀏覽器")
                WebViewDriver.AUTO -> Text("自動選擇")
            }
        }
        DropdownItem(
            selected = { config.driver },
            values = { WebViewDriver.enabledEntries },
            itemText = itemText,
            exposedItemText = itemText,
            onSelect = {
                videoResolverSettingsState.update(
                    config.copy(
                        driver = it,
                    ),
                )
            },
            title = { Text("瀏覽器引擎") },
            description = { Text("播放部分影片來源時需要使用無頭瀏覽器引擎，請在電腦上安裝 Chrome 或 Edge 瀏覽器，Safari 不支援") },
        )
        DropdownItem(
            selected = { config.effectiveDataSourceBrowserConcurrency },
            values = { VideoResolverSettings.DataSourceBrowserConcurrencyOptions },
            itemText = { Text("$it 個") },
            onSelect = {
                videoResolverSettingsState.update(
                    config.copy(
                        dataSourceBrowserConcurrency = it,
                    ),
                )
            },
            title = { Text("資料來源查詢瀏覽器數") },
            description = { Text("限制資料來源查詢階段可同時使用的瀏覽器數量，例如驗證碼處理") },
        )
    }
}

// TODO: More accurate icons
//@Composable
//private fun WebViewDriverIcon(driver: WebViewDriver) {
////    when (driver) {
////        else -> Icon(Icons.Rounded.TravelExplore, driver.toString())
////    }
//    Icon(Icons.Rounded.TravelExplore, driver.toString())
//}
