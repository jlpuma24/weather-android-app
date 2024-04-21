package co.bold.weather.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastResponse(
    var location: ForecastLocation? = ForecastLocation(),
    var current: Current? = Current(),
    var forecast: Forecast? = Forecast()
): Parcelable