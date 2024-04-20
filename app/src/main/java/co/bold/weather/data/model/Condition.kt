package co.bold.weather.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Condition(
    @SerializedName("text") var text: String? = null,
    @SerializedName("icon") var icon: String? = null,
): Parcelable