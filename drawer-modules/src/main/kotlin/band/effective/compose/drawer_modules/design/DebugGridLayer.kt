package band.effective.compose.drawer_modules.design

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun DebugGridLayer(
    debugGridLayerConfig: DebugGridStateConfig,
) {
    CompositionLocalProvider(LocalDebugGridConfig provides debugGridLayerConfig) {
        if (LocalDebugGridConfig.current.isEnabled) {
            val color = Color.Red.copy(alpha = LocalDebugGridConfig.current.alpha)
            val size = LocalDebugGridConfig.current.size
            DebugGridLayerCanvas(color = color, size = size)
        }
    }
}

@Composable
internal fun DebugGridLayerCanvas(
    color: Color,
    size: Dp,
) {
    Canvas(Modifier.fillMaxSize()) {
        val offset = size.toPx()
        val lineWidth = 1f
        var x = 0f
        while (x < this.size.width) {
            drawLine(
                start = Offset(x, 0f),
                end = Offset(x, this.size.height),
                strokeWidth = lineWidth,
                color = color,
            )
            x += offset
        }
        var y = 0f
        while (y < this.size.height) {
            drawLine(
                start = Offset(0f, y),
                end = Offset(this.size.width, y),
                strokeWidth = lineWidth,
                color = color,
            )
            y += offset
        }
    }
}
