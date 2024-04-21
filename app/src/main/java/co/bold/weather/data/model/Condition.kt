package co.bold.weather.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Condition(
    var text: String? = null,
    var icon: String? = null,
): Parcelable