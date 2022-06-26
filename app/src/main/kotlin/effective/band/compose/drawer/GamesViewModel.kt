package effective.band.compose.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import effective.band.compose.drawer.domain.Game
import effective.band.compose.drawer.domain.GamesResponse
import effective.band.compose.drawer.network.GamesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class GamesViewModel : ViewModel() {

    private val mutableState: MutableStateFlow<State> = MutableStateFlow(State.Idle)
    val state: StateFlow<State> = mutableState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        mutableState.value = State.Loading

        viewModelScope.launch {

            Timber.v("Fetching games list...")
            val api: GamesApi = AppConfiguration.api

            try {
                val response: GamesResponse = api.getGames()
                mutableState.emit(State.Success(response.results))
                Timber.v("Fetched games list successfully.")
            } catch (e: Exception) {
                Timber.e(e, "Something went wrong when fetching games list.")
                mutableState.emit(State.Error)
            }
        }
    }

    sealed class State {
        object Idle : State()
        object Error : State()
        object Loading : State()
        data class Success(val games: List<Game>) : State()
    }
}
