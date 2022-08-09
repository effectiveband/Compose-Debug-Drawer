package band.effective.compose.drawer_modules.retrofit

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import band.effective.drawer_base.ActionsModule
import band.effective.drawer_base.MediumText
import band.effective.drawer_base.actions.DropdownSelectorAction
import band.effective.compose.drawer_modules.R

/**
 * Uses a [DebugRetrofitConfig] to display controls to modify the behaviour of
 * [Retrofit]/[NetworkBehavior].
 *
 * - **Endpoint:** Allows the URL that `Retrofit` uses  to be changed. In addition, if the selected
 *   [Endpoint] is listed as a mock endpoint then the other controls in this module will be enabled.
 *
 * - **Delay:** How long a mock request should take.
 *
 * - **Variance:** How much variance in time there should be for mock requests.
 *
 * - **Fail rate:** The percentage of requests that should fail. Failures represent problems with a
 *   network transaction, distinct from errors received from a server.
 *
 * - **Error rate:** The percentage of requests that should return an error.
 *
 * - **Error code:** The error code that is returned for requests that return mock errors.
 */
@Composable
fun RetrofitModule(modifier: Modifier, config: DebugRetrofitConfig) {
    val isMock = config.currentEndpoint.isMock
    ActionsModule(
        modifier = modifier,
        icon = {
            Icon(
                imageVector = Icons.Default.Language,
                contentDescription = "Device icon"
            )
        },
        title = stringResource(R.string.network),
        actions = {

            // Set up endpoint spinner.
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MediumText(
                    modifier = Modifier.fillMaxWidth(.3f),
                    text = stringResource(id = R.string.drawer_retrofitEndpoint)
                )
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                        .weight(1f)
                )
                DropdownSelectorAction(
                    items = config.endpoints,
                    defaultValue = config.currentEndpoint,
                    itemFormatter = { endpoint -> endpoint.name },
                    onItemSelected = {
                        config.setEndpointAndRelaunch(it)
                    })
            }
            // Set up network delay spinner.

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MediumText(
                    modifier = Modifier.fillMaxWidth(.3f),
                    text = stringResource(id = R.string.drawer_retrofitDelay)
                )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                DropdownSelectorAction(
                    items = NetworkParams.DelayVariant.values().asList(),
                    enabled = isMock,
                    defaultValue = NetworkParams.DelayVariant.values()
                        .find { it.delayValue == config.delayMs }
                        ?: NetworkParams.DelayVariant.values()[0],
                    onItemSelected = {
                        config.delayMs = it.delayValue
                    }
                )
            }

            // Set up variance spinner.

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MediumText(
                    modifier = Modifier.fillMaxWidth(.3f),
                    text = stringResource(id = R.string.drawer_retrofitVariance)
                )
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                        .weight(1f)
                )
                DropdownSelectorAction(
                    items = NetworkParams.varianceValues,
                    enabled = isMock,
                    defaultValue = config.variancePercent,
                    itemFormatter = { long -> "±${long}" },
                    onItemSelected = {
                        config.variancePercent = it
                    })
            }

            // Set up failure rate spinner.

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MediumText(
                    modifier = Modifier.fillMaxWidth(.3f),
                    text = stringResource(id = R.string.drawer_retrofitFailureRate)
                )
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                        .weight(1f)
                )
                DropdownSelectorAction(
                    modifier,
                    items = NetworkParams.failureValues,
                    enabled = isMock,
                    defaultValue = config.failurePercent,
                    itemFormatter = { int -> if (int == 0) "None" else "$int%" },
                    onItemSelected = {
                        config.failurePercent = it
                    })
            }

            // Set up error rate spinner.

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MediumText(
                    modifier = Modifier.fillMaxWidth(.3f),
                    text = stringResource(id = R.string.drawer_retrofitErrorRate)
                )
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                        .weight(1f)
                )
                DropdownSelectorAction(
                    modifier,
                    items = NetworkParams.errorPercentValues,
                    enabled = isMock,
                    defaultValue = config.errorPercent,
                    itemFormatter = { int -> if (int == 0) "None" else "$int%" },
                    onItemSelected = {
                        config.errorPercent = it
                    })
            }

            // Set up error type spinner.

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MediumText(
                    modifier = Modifier.fillMaxWidth(.3f),
                    text = stringResource(id = R.string.drawer_retrofitErrorCode)
                )
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                        .weight(1f)
                )
                DropdownSelectorAction(
                    modifier,
                    items = NetworkParams.errorCodeValues,
                    enabled = isMock,
                    defaultValue = config.errorCode,
                    itemFormatter = { int -> if (int == 0) "None" else "HTTP $int" },
                    onItemSelected = {
                        config.errorCode = it
                    })
            }
        })
}