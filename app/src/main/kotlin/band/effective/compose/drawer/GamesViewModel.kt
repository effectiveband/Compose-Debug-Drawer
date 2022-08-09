package band.effective.compose.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.compose.drawer.AppConfiguration
import band.effective.compose.drawer.domain.Game
import band.effective.compose.drawer.domain.GamesResponse
import band.effective.compose.drawer.network.GamesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class GamesViewModel : ViewModel() {

    data class State(
        val isLoading: Boolean = true,
        val games: List<Game> = emptyList(),
        val error: String? = ""
    )

    private val mutableState: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> = mutableState.asStateFlow()
    private val api: GamesApi = AppConfiguration.api

    init {
        loadData()
    }

    fun loadData() = viewModelScope.launch {
        mutableState.update { it.copy(isLoading = true, error = null) }

        Timber.v("Fetching games list...")

        try {
            val response: GamesResponse = api.getGames()
            mutableState.update {
                it.copy(
                    games = response.results,
                    isLoading = false,
                    error = null
                )
            }
            Timber.v("Fetched games list successfully.")
        } catch (e: Exception) {
            Timber.e(e, "Something went wrong when fetching games list.")
            mutableState.update { it.copy(error = e.localizedMessage, isLoading = false) }
        }
    }
}
