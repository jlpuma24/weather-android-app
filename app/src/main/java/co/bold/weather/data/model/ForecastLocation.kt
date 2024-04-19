package co.bold.weather.data.model

import com.google.gson.annotations.SerializedName

data class ForecastLocation(
    @SerializedName("name") var name: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("localtime") var localtime: String? = null
)