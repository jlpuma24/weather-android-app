package co.bold.weather.domain.usecases

import co.bold.weather.data.model.Location
import co.bold.weather.domain.WeatherAppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SearchByKeywordUseCase(private val weatherAppRepository: WeatherAppRepository) {

    suspend fun invoke(keyword: String): Flow<List<Location>> = flow {
        val response = weatherAppRepository.getLocationsByKeyword(keyword)
        if (!response.isSuccessful) {
            emit(response.body() ?: emptyList())
        } else {
            emit(emptyList())
        }
    }.catch {
        emit(emptyList())
    }
}