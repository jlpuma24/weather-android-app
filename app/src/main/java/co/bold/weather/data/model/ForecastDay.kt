package co.bold.weather.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastDay(
    @SerializedName("date") var date: String? = null,
    @SerializedName("day") var day: Day? = Day(),
): Parcelable