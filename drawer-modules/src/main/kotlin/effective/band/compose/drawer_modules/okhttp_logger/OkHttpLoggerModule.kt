package effective.band.compose.drawer_modules.okhttp_logger

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import effective.band.compose.drawer_modules.ActionsModule
import effective.band.compose.drawer_modules.R
import effective.band.compose.drawer_modules.actions.ButtonAction
import effective.band.compose.drawer_modules.actions.DropdownSelectorAction
import effective.band.compose.drawer_modules.timber_module.LogsAlertDialog
import okhttp3.logging.HttpLoggingInterceptor

@Composable
fun OkHttpLoggerModule(modifier: Modifier, httpLogger: HttpLogger) {
    val httpLoggerActions = HttpLoggerActions(httpLogger)
    var isLogsAlertDialogVisible by remember {
        mutableStateOf(false)
    }
    ActionsModule(modifier = modifier, title = stringResource(id = R.string.logs), icon = {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = stringResource(id = R.string.logs),
        )
    }) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(id = R.string.http))
                Spacer(modifier = Modifier.width(16.dp))
                DropdownSelectorAction(
                    modifier = modifier,
                    items = HttpLoggingInterceptor.Level.values().toList(),
                    onItemSelected = {
                        httpLoggerActions.setLevel(it.ordinal)
                    },
                    defaultValue = HttpLoggingInterceptor.Level.values().first()
                )

            }
            ButtonAction(text = stringResource(id = R.string.show_logs)) {
                isLogsAlertDialogVisible = true
            }
            if (isLogsAlertDialogVisible) {
                LogsAlertDialog { isLogsAlertDialogVisible = false }
            }
        }
    }
}