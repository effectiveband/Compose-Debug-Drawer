package band.effective.drawer_location

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import effective.band.compose.drawer_modules.InfoModule

@Composable
fun LocationModule(
    modifier: Modifier, viewModel: LocationModuleViewModel = LocationModuleViewModel(
        LocalContext.current
    )
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = null, block = {
        viewModel.loadLocation()
    })

    state.let {
        if (it != null) {
            val latitude = "Latitude" to it.latitude.toString()
            val longitude = "Longitude" to it.longitude.toString()
            val accuracy = "Accuracy" to it.accuracy.toString()
            val time = "Time" to it.time.toString()
            val provider = "Provider" to it.provider


            val items = listOf(
                latitude,
                longitude,
                accuracy,
                time,
                provider
            )

            InfoModule(modifier = modifier, icon = {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = "Map icon"
                )
            }, title = stringResource(id = R.string.location), items = items)
        }
    }
}