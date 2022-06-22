package effective.band.compose.drawer_base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DebugDrawerModule(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    icon: @Composable (() -> Unit)? = null,
    title: String,
    contentModifier: Modifier = Modifier,
    showBadge: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    var expandedState by rememberSaveable { mutableStateOf(expanded) }

    val semanticsModifier = Modifier.semantics {
        testTag = "Module $title"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (expandedState) modifier else Modifier)
            .then(semanticsModifier),
    ) {
        DrawerModuleHeader(
            icon = icon,
            title = title,
            showBadge = showBadge,
            expandedState = expandedState,
            onClick = {
                expandedState = !expandedState
            },
        )

        val contentSemanticsModifier = Modifier.semantics {
            testTag = "Module content $title"
        }
        AnimatedVisibility(visible = expandedState) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .then(contentModifier)
                    .then(contentSemanticsModifier),
            ) {
                content()
            }
        }
    }
}