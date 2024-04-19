package co.bold.weather.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.bold.weather.data.model.Location
import co.bold.weather.views.states.LocationEventClick

class LocationsAdapterViewModel : ViewModel() {
    private val _event = MutableLiveData<LocationEventClick>()
    val event: LiveData<LocationEventClick>
        get() = _event

    fun onItemClick(location: Location) {
        _event.value = LocationEventClick.ItemClick(location)
    }
}