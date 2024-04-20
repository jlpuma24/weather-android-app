package co.bold.weather.views

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import co.bold.weather.data.model.ForecastResponse
import co.bold.weather.databinding.ActivityForecastDetailBinding
import co.bold.weather.views.adapter.NextDaysAdapter
import co.bold.weather.views.extensions.cleanUrl
import co.bold.weather.views.extensions.setUrlImage
import co.bold.weather.views.extensions.toCelsius
import co.bold.weather.views.extensions.toCurrentDayFormat
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
        prepareToolbar()
        listenViewModel()
        weatherViewModel.getLocationsForecastByKeyword(intent.getStringExtra("location") ?: "")
    }

    private fun prepareToolbar() {
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setValues(forecastResponse: ForecastResponse?) {
        binding.apply {
            pbLoaderImage.isVisible = true
            tvNextDays.isVisible = true
            rvNextDays.isVisible = true
            forecastResponse?.apply {
                val city = location?.name
                val country = location?.country
                tvLocation.text = "$city, $country"
                tvLocalDate.text = location?.localtime?.toCurrentDayFormat()
                current?.apply {
                    tvCurrentCondition.text = condition?.text ?: ""
                    tvCurrentTemperature.text = tempC?.toCelsius()
                    ivWeatherCondition.setUrlImage(condition?.icon?.cleanUrl() ?: "", pbLoaderImage)
                }
                rvNextDays.layoutManager = LinearLayoutManager(this@ForecastDetailActivity)
                rvNextDays.adapter = NextDaysAdapter(forecast?.forecastday?.takeLast(3) ?: listOf())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
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