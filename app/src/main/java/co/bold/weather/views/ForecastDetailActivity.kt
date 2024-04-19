package co.bold.weather.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import co.bold.weather.data.model.Forecast
import co.bold.weather.data.model.ForecastResponse
import co.bold.weather.databinding.ActivityForecastDetailBinding
import co.bold.weather.views.states.SearchLocationUiState
import co.bold.weather.views.viewmodels.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForecastDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForecastDetailBinding
    private val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        listenViewModel()
        weatherViewModel.getLocationsForecastByKeyword(intent.getStringExtra("location") ?: "")
    }

    private fun setValues(forecast: ForecastResponse?) {
        binding.apply {
            forecast?.apply {
                val city = location?.name
                val country = location?.country
                tvLocation.text = "$city, $country"
            }
        }
    }

    private fun listenViewModel() {
        weatherViewModel.searchKeywordState.observe(this@ForecastDetailActivity) { state ->
            when (state) {
                is SearchLocationUiState.ErrorSearchForecastLocation -> {
                    binding.pbLoader.isVisible = false
                }

                is SearchLocationUiState.Loading -> {
                    binding.pbLoader.isVisible = true
                }

                is SearchLocationUiState.SuccessSearchForecastLocation -> {
                    binding.pbLoader.isVisible = false
                    setValues(state.forecast)
                }

                else -> {
                    // No - Op
                }
            }
        }
    }
}