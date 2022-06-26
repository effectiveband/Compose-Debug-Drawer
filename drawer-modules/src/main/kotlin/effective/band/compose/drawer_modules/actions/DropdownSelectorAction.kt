package effective.band.compose.drawer_modules.actions

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T : Any> DropdownSelectorAction(
    modifier: Modifier = Modifier,
    items: List<T>,
    itemFormatter: (T) -> String = { itemFormat -> itemFormat.toString() },
    defaultValue: T? = null,
    enabled: Boolean = true,
    onItemSelected: (T) -> Unit,
) {
    val textState = remember { mutableStateOf(defaultValue?.let(itemFormatter) ?: "") }
    val isExpanded = remember { mutableStateOf(false) }

    TextButton(
        modifier = modifier,
        onClick = { isExpanded.value = !isExpanded.value },
        enabled = enabled
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = textState.value)
            Spacer(modifier = Modifier.weight(1f))
            DropdownIcon(isExpanded = isExpanded.value)
            if (isExpanded.value) {
                DropdownMenu(
                    expanded = isExpanded.value,
                    onDismissRequest = { isExpanded.value = false }) {
                    items.forEach { item ->
                        DropdownItemComponent(
                            item = item,
                            itemFormatter = itemFormatter
                        ) { clickItem ->
                            textState.value = itemFormatter(clickItem)
                            isExpanded.value = false
                            onItemSelected(clickItem)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T> DropdownItemComponent(
    item: T,
    itemFormatter: (T) -> String = { itemFormat -> itemFormat.toString() },
    onClick: (T) -> Unit,
) {
    DropdownMenuItem(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            onClick(item)
        }) {
        Text(
            text = itemFormatter(item),
            modifier = Modifier.wrapContentWidth()
        )
    }
}