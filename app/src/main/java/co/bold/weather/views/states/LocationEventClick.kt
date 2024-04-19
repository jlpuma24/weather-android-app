package co.bold.weather.views.states

import co.bold.weather.data.model.Location

sealed class LocationEventClick {
    data class ItemClick(val location: Location) : LocationEventClick()
}