package co.bold.weather.views.states

import co.bold.weather.data.model.Location

sealed class SearchLocationUiState {
    data object Loading: SearchLocationUiState()
    class Success(val list: Location): SearchLocationUiState()
    data object Error: SearchLocationUiState()
}
