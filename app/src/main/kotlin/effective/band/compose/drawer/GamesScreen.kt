package effective.band.compose.drawer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import effective.band.compose.drawer.domain.Game

@Composable
fun GamesScreen() {
    ConfigureScreen { AppContent() }
}

@Composable
private fun AppContent() {
    val viewModel = GamesViewModel()
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                elevation = 0.dp,
                title = { Text(text = title) }
            )
        }
    ) { innerPadding ->
        when (state) {
            GamesViewModel.State.Error -> {
                Text(text = "Error")
            }
            GamesViewModel.State.Idle -> {
                Text(text = "Idle")
            }
            GamesViewModel.State.Loading -> {
                Text(text = "Loading...")
            }
            is GamesViewModel.State.Success -> {
                LazyColumn(modifier = Modifier.padding(innerPadding), content = {
                    items((state as GamesViewModel.State.Success).games) { game ->
                        GameListItem(game)
                    }
                })
            }
        }
    }
}

@Composable
fun GameListItem(game: Game) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = game.image.small_url,
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = game.name)
    }
    Divider(modifier = Modifier.fillMaxWidth())
}
