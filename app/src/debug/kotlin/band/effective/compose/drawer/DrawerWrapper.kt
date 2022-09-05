package band.effective.compose.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.compose.drawer_modules.BuildModule
import band.effective.compose.drawer_modules.DeviceModule
import band.effective.compose.drawer_modules.design.DebugGridLayer
import band.effective.compose.drawer_modules.design.DebugGridStateConfig
import band.effective.compose.drawer_modules.design.DesignModule
import band.effective.compose.drawer_modules.leak.LeakCanaryModule
import band.effective.compose.drawer_modules.okhttp.OkHttpLoggerModule
import band.effective.compose.drawer_modules.retrofit.RetrofitModule
import band.effective.drawer_base.ActionsModule
import band.effective.drawer_base.DebugDrawerLayout
import band.effective.drawer_base.actions.ButtonAction
import band.effective.drawer_base.actions.SwitchAction
import band.effective.drawer_location.LocationModule

@Composable
fun ConfigureScreen(bodyContent: @Composable (isDrawerOpen: Boolean) -> Unit) {

    val gridAlpha = LocalContentAlpha.current

    var debugGridLayerConfig: DebugGridStateConfig by remember {
        mutableStateOf(
            DebugGridStateConfig(
                isEnabled = false,
                alpha = gridAlpha
            )
        )
    }

    DebugDrawerLayout(
        drawerModules = {
            val modulesModifier = Modifier
                .padding(4.dp)
                .clip(shape = MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colors.surface)
            ActionsModule(
                title = "Actions",
                icon = { Icon(Icons.Filled.Settings, contentDescription = null) }) {
                ButtonAction(text = "Button 1") {

                }
                ButtonAction(text = "Button 2") {

                }
                SwitchAction(text = "Switch 1", isChecked = true, onChange = {})
                SwitchAction(text = "Switch 2", isChecked = false, onChange = {})
            }
            DesignModule(modulesModifier, config = debugGridLayerConfig) {
                debugGridLayerConfig = it
            }
            BuildModule(modulesModifier)
            DeviceModule(modulesModifier)
            RetrofitModule(
                modifier = modulesModifier,
                config = AppConfiguration.debugRetrofitConfig
            )
            OkHttpLoggerModule(modulesModifier, AppConfiguration.httpLogger)
            LeakCanaryModule(modulesModifier)
            LocationModule(modulesModifier)
        },
        bodyContent = {
            Box {
                bodyContent(true)
                DebugGridLayer(debugGridLayerConfig)
            }
        },
    )
}