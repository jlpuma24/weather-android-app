package co.bold.weather.data.model

data class Location(
    val id: Int? = -1,
    val name: String? = "",
    val region: String? = "",
    val country: String? = "",
    val lat: Double? = 0.0,
    val lon: Double? = 0.0,
    val url: String? = ""
)
