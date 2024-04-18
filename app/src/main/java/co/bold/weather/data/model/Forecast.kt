package co.bold.weather.data.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday") var forecastday: ArrayList<ForecastDay> = arrayListOf()
)