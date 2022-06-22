package effective.band.compose.drawer.modules

import android.widget.Toast
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import effective.band.compose.drawer_modules.ActionsModule
import effective.band.compose.drawer_modules.actions.ButtonAction
import effective.band.compose.drawer_modules.actions.DropdownSelectorAction
import effective.band.compose.drawer_modules.actions.SwitchAction
import effective.band.compose.drawer_modules.actions.TextAction

@Composable
fun DemoActionsModule(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val dropdownItems = listOf(
        Forlayo("Item 1"),
        Forlayo("Item 2"),
        Forlayo("Item 3"),
    )

    var switchState by remember { mutableStateOf(false) }
    var itemSelectorState by remember { mutableStateOf<Forlayo?>(null) }

    val showBadge = derivedStateOf {
        itemSelectorState != null || switchState
    }

    ActionsModule(
        modifier = modifier,
        icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings") },
        title = "Actions",
        showBadge = showBadge.value,
    ) {
        TextAction(text = "Buttons")
        ButtonAction(text = "Button 2") {
            Toast.makeText(context, "Click Button 2", Toast.LENGTH_SHORT).show()
        }
        TextAction(text = "Switches")
        SwitchAction(text = "Switch 2", isChecked = switchState) { checked ->
            switchState = checked
            Toast.makeText(context, "Switch 2 change $checked", Toast.LENGTH_SHORT).show()
        }
        TextAction(text = "Selectors")
        DropdownSelectorAction(
            label = "Items",
            items = dropdownItems,
            itemFormatter = { forlayo -> forlayo.text },
            onItemSelected = { forlayo ->
                itemSelectorState = forlayo
                Toast.makeText(context, "Item: ${forlayo.text}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}


data class Forlayo(val text: String)