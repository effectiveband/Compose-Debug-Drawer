package effective.band.compose.drawer_modules.leak

import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import effective.band.compose.drawer_modules.R
import effective.band.drawer_base.ActionsModule
import effective.band.drawer_base.actions.ButtonAction
import effective.band.drawer_base.actions.SwitchAction
import leakcanary.LeakCanary

@Composable
fun LeakCanaryModule(modifier: Modifier) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit, block = {
        LeakCanaryActions.setup(context)
    })
    var isEnableHeapDumps by remember {
        mutableStateOf(LeakCanary.config.dumpHeap)
    }
    ActionsModule(modifier = modifier, title = stringResource(id = R.string.leakcanary), icon = {
        Icon(painter = painterResource(id = R.drawable.leak_canary_logo), contentDescription = null)
    }) {
        SwitchAction(
            text = stringResource(id = R.string.enable_heap_dumps),
            isChecked = isEnableHeapDumps,
            onChange = {
                LeakCanaryActions.onToggleDumpHeap(context, it)
            }
        )
        ButtonAction(text = stringResource(id = R.string.view_leaks)) {
            context.startActivity(LeakCanary.newLeakDisplayActivityIntent())
        }
    }
}
