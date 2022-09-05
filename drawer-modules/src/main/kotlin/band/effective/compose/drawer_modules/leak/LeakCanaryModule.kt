package band.effective.compose.drawer_modules.leak

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import band.effective.compose.drawer_modules.R
import band.effective.drawer_base.ActionsModule
import band.effective.drawer_base.actions.ButtonAction
import band.effective.drawer_base.actions.SwitchAction
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
