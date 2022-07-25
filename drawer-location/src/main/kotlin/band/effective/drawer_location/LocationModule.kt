package band.effective.drawer_location

import android.location.Location
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import effective.band.drawer_base.InfoModule

@Composable
fun LocationModule(
    modifier: Modifier, viewModel: LocationModuleViewModel = LocationModuleViewModel(
        LocalContext.current
    )
) {
    LocationModuleInfo(modifier, viewModel.state.collectAsState().value)
}

@Composable
fun LocationModuleInfo(modifier: Modifier, value: Location?) {
    InfoModule(modifier = modifier, icon = {
        Icon(
            imageVector = Icons.Default.Map,
            contentDescription = "Map icon"
        )
    }, title = stringResource(id = R.string.location), items = value.let {
        val latitude = "Latitude" to it?.latitude.toString()
        val longitude = "Longitude" to it?.longitude.toString()
        val accuracy = "Accuracy" to it?.accuracy.toString()
        val time = "Time" to it?.time.toString()
        val provider = "Provider" to it?.provider.toString()

        listOf(
            latitude,
            longitude,
            accuracy,
            time,
            provider
        )
    })
}
