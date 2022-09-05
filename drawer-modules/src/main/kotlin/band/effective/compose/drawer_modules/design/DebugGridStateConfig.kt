package band.effective.compose.drawer_modules.design

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DebugGridStateConfig internal constructor(
    val isEnabled: Boolean,
    val alpha: Float,
    val size: Dp,
) {

    companion object {
        operator fun invoke(
            isEnabled: Boolean = false,
            alpha: Float = 0.3f,
            size: Dp = 4.dp,
        ) = DebugGridStateConfig(
            isEnabled = isEnabled,
            alpha = alpha,
            size = size,
        )
    }
}
