package band.effective.drawer_base

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DebugDrawerDivider() {
    Spacer(modifier = Modifier.requiredHeight(4.dp))
    Divider(
        color = MaterialTheme.colors.onSurface.compositeOverSurface(0.12f)
    )
    Spacer(modifier = Modifier.requiredHeight(4.dp))
}