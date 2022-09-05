package band.effective.compose.drawer_modules.design

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

val LocalDebugGridConfig: ProvidableCompositionLocal<DebugGridStateConfig> =
    staticCompositionLocalOf { DebugGridStateConfig(false) }