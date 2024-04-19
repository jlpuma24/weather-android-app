package co.bold.weather.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.bold.weather.domain.usecases.SearchByKeywordUseCase
import co.bold.weather.domain.usecases.SearchForecastByKeywordUseCase
import co.bold.weather.views.states.SearchLocationUiState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class WeatherViewModel(
    private val searchByKeywordUseCase: SearchByKeywordUseCase,
    private val searchForecastByKeywordUseCase: SearchForecastByKeywordUseCase
) : ViewModel(), KoinComponent {

    private val _searchKeywordState: MutableLiveData<SearchLocationUiState> = MutableLiveData()
    val searchKeywordState: LiveData<SearchLocationUiState> = _searchKeywordState

    fun getLocationsByKeyword(keyword: String) {
        viewModelScope.launch {
            searchByKeywordUseCase.invoke(keyword).collect {
                if (it.isNotEmpty()) {
                    _searchKeywordState.value = SearchLocationUiState.SuccessSearchLocation(it)
                } else {
                    _searchKeywordState.value = SearchLocationUiState.ErrorSearchLocation
                }
            }
        }
    }

    fun getLocationsForecastByKeyword(keyword: String) {
        viewModelScope.launch {
            searchForecastByKeywordUseCase.invoke(keyword).collect {
                if (it?.forecast != null) {
                    _searchKeywordState.value =
                        SearchLocationUiState.SuccessSearchForecastLocation(it.forecast)
                } else {
                    _searchKeywordState.value =
                        SearchLocationUiState.ErrorSearchForecastLocation
                }
            }
        }
    }
}