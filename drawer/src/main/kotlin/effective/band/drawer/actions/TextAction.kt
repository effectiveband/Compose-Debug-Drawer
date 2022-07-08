package effective.band.drawer.actions

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextAction(
    modifier: Modifier = Modifier,
    text: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(36.dp)
            .padding(bottom = 8.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            color = MaterialTheme.colors.secondary,
            text = text
        )
    }
}