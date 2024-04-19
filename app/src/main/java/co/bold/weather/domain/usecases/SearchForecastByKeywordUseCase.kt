package co.bold.weather.domain.usecases

import co.bold.weather.data.model.ForecastResponse
import co.bold.weather.domain.WeatherAppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SearchForecastByKeywordUseCase(private val weatherAppRepository: WeatherAppRepository) {

    suspend fun invoke(keyword: String): Flow<ForecastResponse?> = flow {
        val response = weatherAppRepository.getForecastByKeywordAndDays(keyword)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            emit(null)
        }
    }.catch {
        emit(null)
    }
}