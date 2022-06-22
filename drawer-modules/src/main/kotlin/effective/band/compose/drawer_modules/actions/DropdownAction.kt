package effective.band.compose.drawer_modules.actions

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp

@Composable
fun DropdownAction(
    modifier: Modifier = Modifier,
    text: String? = null,
    label: String? = null,
    onClick: () -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    onClick()
                }
            }
            .then(modifier),
        value = text ?: "",
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { onClick() }) {
                DropdownIcon(false)
            }
        },
        label = { label?.let { Text("") } },
        onValueChange = { },
    )
}