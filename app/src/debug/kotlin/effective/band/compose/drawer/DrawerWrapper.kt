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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import band.effective.drawer_okhttplogger.HttpLogger
import band.effective.drawer_okhttplogger.OkHttpLoggerModule
import band.effective.drawer_retrofit.DebugRetrofitConfig
import band.effective.drawer_retrofit.Endpoint
import band.effective.drawer_retrofit.RetrofitModule
import effective.band.compose.drawer_base.DebugDrawerLayout
import effective.band.compose.drawer_modules.BuildModule
import effective.band.compose.drawer_modules.DeviceModule
import band.effective.leak.LeakCanaryModule
import effective.band.compose.drawer_ui_modules.design.DebugGridLayer
import effective.band.compose.drawer_ui_modules.design.DebugGridStateConfig
import effective.band.compose.drawer_ui_modules.design.DesignModule
import retrofit2.mock.NetworkBehavior

@Composable
fun ConfigureScreen(bodyContent: @Composable (isDrawerOpen: Boolean) -> Unit) {

    val gridAlpha = LocalContentAlpha.current
    val context = LocalContext.current

    var debugGridLayerConfig: DebugGridStateConfig by remember {
        mutableStateOf(
            DebugGridStateConfig(
                isEnabled = false,
                alpha = gridAlpha,
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
                config = DebugRetrofitConfig(
                    context,
                    endpoints = listOf(
                        Endpoint("Development", "https//dev-endpoint", true),
                        Endpoint("Production", "https//prod-endpoint", false),
                    ),
                    networkBehavior = NetworkBehavior.create()
                )
            )
            OkHttpLoggerModule(modulesModifier, HttpLogger(LocalContext.current))
            LeakCanaryModule(modulesModifier)
        },
        bodyContent = { drawerState ->
            Box {
                bodyContent(drawerState.isOpen)
                DebugGridLayer(debugGridLayerConfig)
            }
        },
    )
}