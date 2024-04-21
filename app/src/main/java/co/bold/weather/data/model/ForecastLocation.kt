package co.bold.weather.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastLocation(
    var name: String? = null,
    var country: String? = null,
    var localtime: String? = null
): Parcelable