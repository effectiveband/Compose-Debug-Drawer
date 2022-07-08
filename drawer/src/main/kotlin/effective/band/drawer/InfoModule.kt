package effective.band.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InfoModule(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    title: String,
    items: List<Pair<String, String>>,
) {
    DebugDrawerModule(
        modifier = modifier,
        icon = icon,
        title = title
    ) {
        Column {
            items.forEachIndexed { index, item ->
                Column {
                    DebugModuleInfoContent(item.first, item.second)
                    if (index < items.size - 1) {
                        DebugDrawerDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun DebugModuleInfoContent(
    key: String,
    value: String,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.requiredWidth(80.dp)
        ) {
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.high,
            ) {
                Text(
                    text = key,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.medium)
                .padding(8.dp)
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = value,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}