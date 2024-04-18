package co.bold.weather.data.repository

import co.bold.weather.data.model.Location
import co.bold.weather.data.remote.WeatherAppSearchApiService
import co.bold.weather.domain.WeatherAppRepository
import retrofit2.Response

class WeatherAppRepositoryImpl(private val weatherAppSearchApiService: WeatherAppSearchApiService) :
    WeatherAppRepository {

    override suspend fun getLocationsByKeyword(string: String): Response<List<Location>> {
        return weatherAppSearchApiService.searchLocations(string)
    }
}