package co.bold.weather.data.remote

import co.bold.weather.BuildConfig
import co.bold.weather.data.model.Location
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAppSearchApiService {
    @GET(BuildConfig.SEARCH_PATH)
    suspend fun searchLocations(
        @Query("q") query: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY,
    ): Response<List<Location>>

    @GET(BuildConfig.FORECAST_PATH)
    suspend fun searchForecastLocations(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("q") query: String,
        @Query("days") days: Int,
    ): Response<List<Location>>
}