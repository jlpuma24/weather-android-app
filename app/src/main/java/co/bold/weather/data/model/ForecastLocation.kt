package co.bold.weather.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastLocation(
    @SerializedName("name") var name: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("localtime") var localtime: String? = null
): Parcelable