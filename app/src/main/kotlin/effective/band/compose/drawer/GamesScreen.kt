package effective.band.compose.drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import effective.band.compose.drawer.domain.Game

@Composable
fun GamesScreen() {
    ConfigureScreen { isDrawerOpen -> AppContent(isDrawerOpen) }
}

@Composable
private fun AppContent(isDrawerOpen: Boolean, viewModel: GamesViewModel = GamesViewModel()) {
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
        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = rememberSwipeRefreshState(state.isLoading),
            onRefresh = { viewModel.loadData() }) {
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    state.error != null -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = state.error ?: "Something went wrong",
                                modifier = Modifier
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.loadData() }) {
                                Text(
                                    text = "Retry",
                                )
                            }
                        }

                    }
                    else -> {
                        LazyColumn(modifier = Modifier, content = {
                            items(state.games) { game ->
                                GameListItem(game)
                            }
                        })
                    }
                }
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
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = game.name)
    }
    Divider(modifier = Modifier.fillMaxWidth())
}
