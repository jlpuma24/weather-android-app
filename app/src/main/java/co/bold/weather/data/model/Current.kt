package co.bold.weather.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Current(
    @SerializedName("temp_c") var tempC: Double? = null,
    var condition: Condition? = Condition(),
): Parcelable