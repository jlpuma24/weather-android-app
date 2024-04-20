package co.bold.weather.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.bold.weather.data.model.Location
import co.bold.weather.databinding.AdapterItemCountryBinding
import co.bold.weather.views.viewmodels.LocationsAdapterViewModel

class LocationAdapter(
    private val locations: List<Location>,
    private val locationsAdapterViewModel: LocationsAdapterViewModel
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding =
            AdapterItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    inner class LocationViewHolder(private val binding: AdapterItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val location = locations[position]
                    val name = location.name ?: ""
                    val country = location.country ?: ""
                    locationsAdapterViewModel.onItemClick("$name, $country")
                }
            }
        }

        fun bind(location: Location) {
            val name = location.name ?: ""
            val country = location.country ?: ""
            binding.textView.text = "$name, $country"
        }
    }
}