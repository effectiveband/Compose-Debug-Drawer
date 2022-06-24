package effective.band.compose.drawer_modules.actions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp

@Composable
fun <T> DropdownSelectorAction(
    modifier: Modifier = Modifier,
    items: List<T>,
    itemFormatter: (T) -> String = { itemFormat -> itemFormat.toString() },
    defaultValue: T? = null,
    onItemSelected: (T) -> Unit,
) {
    val textState = remember { mutableStateOf(defaultValue?.let(itemFormatter) ?: "") }
    val isExpanded = remember { mutableStateOf(false) }

    Box {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .onFocusChanged { focusState ->
                    isExpanded.value = focusState.isFocused
                }
                .then(modifier),
            value = textState.value,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { isExpanded.value = !isExpanded.value }) {
                    DropdownIcon(isExpanded = isExpanded.value)
                }
            },
            onValueChange = { },
        )
        if (isExpanded.value) {
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = isExpanded.value,
                onDismissRequest = { isExpanded.value = false }) {
                items.forEach { item ->
                    DropdownItemComponent(
                        item = item,
                        itemFormatter = itemFormatter,
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