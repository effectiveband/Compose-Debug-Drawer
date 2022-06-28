package band.effective.drawer_location

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeviceUnknown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import effective.band.compose.drawer_modules.InfoModule

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationModule(
    modifier: Modifier, viewModel: LocationModuleViewModel = LocationModuleViewModel(
        LocalContext.current
    )
) {
    val state by viewModel.state.collectAsState()

    val locationPermission = rememberPermissionState(
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    LaunchedEffect(key1 = Unit, block = {
        viewModel.loadLocation()
    })

    when (locationPermission.status) {
        is PermissionStatus.Granted -> {

            state.let {
                if (it != null) {
                    val latitude = "Latitude" to it.latitude.toString()
                    val longitude = "Longitude" to it.longitude.toString()
                    val accuracy = "Accuracy" to it.accuracy.toString()
                    val time = "Time" to it.time.toString()
                    val provider = "Provider" to it.provider

                    val title = stringResource(id = R.string.location)

                    val items = listOf(
                        latitude,
                        longitude,
                        accuracy,
                        time,
                        provider
                    )

                    InfoModule(modifier = modifier, icon = {
                        Icon(
                            imageVector = Icons.Default.DeviceUnknown,
                            contentDescription = "Device icon"
                        )
                    }, title = title, items = items)
                }
            }
        }
        is PermissionStatus.Denied -> {
            Column(modifier = Modifier.padding(16.dp)) {
                val textToShow =
                    if ((locationPermission.status as PermissionStatus.Denied).shouldShowRationale) {
                        // If the user has denied the permission but the rationale can be shown,
                        // then gently explain why the app requires this permission
                        "The location is important for this module. Please grant the permission."
                    } else {
                        // If it's the first time the user lands on this feature, or the user
                        // doesn't want to be asked again for this permission, explain that the
                        // permission is required
                        "Location permission required for this feature to be available. " +
                                "Please grant the permission"
                    }
                Text(textToShow)
                Button(onClick = {
                    locationPermission.launchPermissionRequest()
                }) {
                    Text("Request permission")
                }
            }
        }
    }
}