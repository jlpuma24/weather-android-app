package co.bold.weather.data.model

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("maxtemp_c") var maxtempC: Double? = null,
    @SerializedName("mintemp_c") var mintempC: Double? = null,
    @SerializedName("avgtemp_c") var avgtempC: Double? = null,
    @SerializedName("condition") var condition: Condition? = Condition(),
)