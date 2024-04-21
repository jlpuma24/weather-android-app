package co.bold.weather.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Day(
    @SerializedName("maxtemp_c") var maxTempC: Double? = null,
    @SerializedName("mintemp_c") var minTempC: Double? = null,
    @SerializedName("avgtemp_c") var avgTempC: Double? = null,
    var condition: Condition? = Condition(),
): Parcelable