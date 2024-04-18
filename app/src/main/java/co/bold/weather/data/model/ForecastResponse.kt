package co.bold.weather.data.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("location") var location: ForecastLocation? = ForecastLocation(),
    @SerializedName("current") var current: Current? = Current(),
    @SerializedName("forecast") var forecast: Forecast? = Forecast()
)