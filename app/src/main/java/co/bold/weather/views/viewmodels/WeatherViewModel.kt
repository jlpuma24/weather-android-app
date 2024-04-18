package co.bold.weather.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.bold.weather.domain.usecases.SearchByKeywordUseCase
import co.bold.weather.views.states.SearchLocationUiState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class WeatherViewModel(
    private val searchByKeywordUseCase: SearchByKeywordUseCase
): ViewModel(), KoinComponent {

    private val _searchKeywordState: MutableLiveData<SearchLocationUiState> = MutableLiveData()
    val searchKeywordState: LiveData<SearchLocationUiState> = _searchKeywordState

    fun getLocationsByKeyword(keyword: String) {
        viewModelScope.launch {
            searchByKeywordUseCase.invoke("Mede").collect {

            }
        }
    }
}