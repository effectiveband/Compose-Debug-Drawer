package effective.band.compose.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.drawer_location.LocationModule
import effective.band.compose.drawer_modules.okhttp.OkHttpLoggerModule
import effective.band.compose.drawer_modules.retrofit.RetrofitModule
import effective.band.compose.drawer_modules.leak.LeakCanaryModule
import effective.band.drawer.DebugDrawerLayout
import effective.band.compose.drawer_modules.BuildModule
import effective.band.compose.drawer_modules.DeviceModule
import effective.band.compose.drawer_modules.design.DebugGridLayer
import effective.band.compose.drawer_modules.design.DebugGridStateConfig
import effective.band.compose.drawer_modules.design.DesignModule

@Composable
fun ConfigureScreen(bodyContent: @Composable () -> Unit) {

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
                bodyContent()
                DebugGridLayer(debugGridLayerConfig)
            }
        },
    )
}