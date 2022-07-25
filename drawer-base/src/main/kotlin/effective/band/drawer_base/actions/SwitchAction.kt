package effective.band.drawer_base.actions

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SwitchAction(
    modifier: Modifier = Modifier,
    text: String,
    isChecked: Boolean,
    tag: String? = null,
    onChange: (checked: Boolean) -> Unit,
) {
    var checkedState by remember { mutableStateOf(isChecked) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .requiredHeight(36.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .then(modifier)
            .toggleable(checkedState) { checked ->
                checkedState = checked
                onChange(checkedState)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val actionTextSemanticModifier = Modifier.semantics {
            testTag = "Action $tag text"
        }
        Text(
            color = MaterialTheme.colors.onSurface,
            modifier = actionTextSemanticModifier,
            text = text,
            textAlign = TextAlign.Start,
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
        val actionSwitchSemanticModifier = Modifier.semantics {
            testTag = "Action $tag switch"
        }
        Switch(
            modifier = actionSwitchSemanticModifier,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.secondary,
                uncheckedThumbColor = MaterialTheme.colors.onSurface,
                checkedTrackAlpha = 0.6f,
                uncheckedTrackAlpha = 0.4f
            ),
            checked = checkedState,
            onCheckedChange = null,
        )
    }
}
