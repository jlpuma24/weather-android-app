package co.bold.weather.domain

import co.bold.weather.data.model.Location
import retrofit2.Response

interface WeatherAppRepository {
    suspend fun getLocationsByKeyword(string: String): Response<List<Location>>
}