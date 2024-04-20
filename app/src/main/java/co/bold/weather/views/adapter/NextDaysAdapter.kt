package co.bold.weather.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.bold.weather.data.model.ForecastDay
import co.bold.weather.databinding.AdapterNextDaysBinding
import co.bold.weather.views.extensions.cleanUrl
import co.bold.weather.views.extensions.setUrlImage
import co.bold.weather.views.extensions.toCelsius
import co.bold.weather.views.extensions.toCurrentDayNameFormat

class NextDaysAdapter(
    private val forecastDaysList: List<ForecastDay>
) : RecyclerView.Adapter<NextDaysAdapter.NextDayLocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextDayLocationViewHolder {
        val binding =
            AdapterNextDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NextDayLocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NextDayLocationViewHolder, position: Int) {
        val location = forecastDaysList[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int {
        return forecastDaysList.size
    }

    inner class NextDayLocationViewHolder(private val binding: AdapterNextDaysBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(forecastDay: ForecastDay) {
            val day = forecastDay.day
            binding.apply {
                tvNextDay.text = " / ${forecastDay.date?.toCurrentDayNameFormat()}"
                imageView.setUrlImage(day?.condition?.icon?.cleanUrl() ?: "", binding.pbLoader)
                tvTemperature.text = toMaxAndMinFormat(day?.maxtempC ?: 0.0, day?.mintempC ?: 0.0)
            }
        }

        private fun toMaxAndMinFormat(max: Double, min: Double): String {
            return "${max.toCelsius()} / ${min.toCelsius()}"
        }
    }
}