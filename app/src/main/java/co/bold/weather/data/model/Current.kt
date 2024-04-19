package co.bold.weather.data.model

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("last_updated_epoch") var lastUpdatedEpoch: Double? = null,
    @SerializedName("last_updated") var lastUpdated: String? = null,
    @SerializedName("temp_c") var tempC: Double? = null,
    @SerializedName("temp_f") var tempF: Double? = null,
    @SerializedName("is_day") var isDay: Double? = null,
    @SerializedName("condition") var condition: Condition? = Condition(),
)