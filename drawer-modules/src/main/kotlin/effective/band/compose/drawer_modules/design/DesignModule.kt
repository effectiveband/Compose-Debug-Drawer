package effective.band.compose.drawer_modules.design

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridOff
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import effective.band.drawer.DebugDrawerModule
import kotlin.math.roundToInt

@Composable
fun DesignModule(
    modifier: Modifier = Modifier,
    config: DebugGridStateConfig,
    onChange: (DebugGridStateConfig) -> Unit,
) {
    DebugDrawerModule(
        modifier = modifier,
        icon = {
            val icon = if (config.isEnabled) {
                Icons.Default.GridOn
            } else {
                Icons.Default.GridOff
            }
            Icon(
                imageVector = icon,
                contentDescription = "Design icon",
            )
        },
        showBadge = config.isEnabled,
        title = "Design"
    ) {
        val text = if (config.isEnabled) {
            "Grid enabled"
        } else {
            "Grid disabled"
        }
        effective.band.drawer.actions.SwitchAction(
            text = text,
            isChecked = config.isEnabled
        ) { enabled ->
            val result = config.copy(isEnabled = enabled)
            onChange(result)
        }
        AnimatedVisibility(visible = config.isEnabled) {
            Column {
                AlphaSlider(config, onChange)
                SizeSlider(config, onChange)
            }
        }
    }
}

@Composable
fun AlphaSlider(
    config: DebugGridStateConfig,
    onChange: (DebugGridStateConfig) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
    ) {
        Text(
            text = "Alpha ${(config.alpha * 100).toInt()}%",
            style = MaterialTheme.typography.caption
        )
        Slider(
            value = config.alpha,
            onValueChange = { alpha ->
                val result = config.copy(alpha = alpha)
                onChange(result)
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.secondary
            ),
        )
    }
}

@Composable
fun SizeSlider(
    config: DebugGridStateConfig,
    onChange: (DebugGridStateConfig) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
    ) {

        Text(
            text = "Size: ${config.size.value.roundToInt()}dp",
            style = MaterialTheme.typography.caption
        )
        Slider(
            value = config.size.value,
            valueRange = 4f..48f,
            steps = 10,
            onValueChange = { size ->
                val result = config.copy(size = size.dp)
                onChange(result)
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.secondary
            ),
        )
    }
}