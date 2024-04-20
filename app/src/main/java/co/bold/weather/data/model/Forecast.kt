package co.bold.weather.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forecast(
    @SerializedName("forecastday") var forecastday: ArrayList<ForecastDay> = arrayListOf()
): Parcelable