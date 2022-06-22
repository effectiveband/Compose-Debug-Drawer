package effective.band.compose.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    ConfigureScreen { isDrawerOpen -> AppContent(isDrawerOpen) }
}

@Composable
private fun AppContent(isDrawerOpen: Boolean) {
    Scaffold(
        topBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                elevation = 0.dp,
                title = { Text(text = title) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            val text = if (isDrawerOpen) {
                "Hooray!"
            } else {
                "Swipe from right or shake"
            }
            Text(
                text = text,
                style = MaterialTheme.typography.h5,
            )
        }
    }
}