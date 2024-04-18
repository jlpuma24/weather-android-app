package co.bold.weather.views.states

import co.bold.weather.data.model.Forecast
import co.bold.weather.data.model.Location

sealed class SearchLocationUiState {
    data object Loading: SearchLocationUiState()
    class SuccessSearchLocation(val list: List<Location>): SearchLocationUiState()
    data object ErrorSearchLocation: SearchLocationUiState()

    class SuccessSearchForecastLocation(val forecast: Forecast?): SearchLocationUiState()
    data object ErrorSearchForecastLocation: SearchLocationUiState()
}
