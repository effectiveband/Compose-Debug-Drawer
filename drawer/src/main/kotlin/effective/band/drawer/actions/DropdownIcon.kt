package effective.band.drawer.actions

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable

@Composable
fun DropdownIcon(isExpanded: Boolean) {
    val iconResource = if (isExpanded) {
        Icons.Default.ArrowDropUp
    } else {
        Icons.Default.ArrowDropDown
    }
    Icon(iconResource, contentDescription = "Dropdown icon")
}