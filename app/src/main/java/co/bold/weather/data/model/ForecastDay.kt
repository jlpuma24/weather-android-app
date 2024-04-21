package co.bold.weather.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastDay(
    var date: String? = null,
    var day: Day? = Day(),
): Parcelable