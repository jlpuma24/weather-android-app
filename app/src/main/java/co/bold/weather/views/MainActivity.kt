package co.bold.weather.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.bold.weather.databinding.ActivityMainBinding
import co.bold.weather.views.states.SearchLocationUiState
import co.bold.weather.views.viewmodels.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        observeViewModel()
        weatherViewModel.getLocationsByKeyword("Medell")
    }

    private fun observeViewModel() {
        weatherViewModel.searchKeywordState.observe(this@MainActivity) { state ->
            when (state) {
                is SearchLocationUiState.ErrorSearchLocation -> {

                }
                is SearchLocationUiState.Loading -> {

                }
                is SearchLocationUiState.SuccessSearchLocation -> {

                }

                is SearchLocationUiState.ErrorSearchForecastLocation -> {

                }
                is SearchLocationUiState.SuccessSearchForecastLocation -> {

                }
            }
        }
    }

    private fun requestForecastLocation() {
        weatherViewModel.getLocationsForecastByKeyword("Medell")
    }
}