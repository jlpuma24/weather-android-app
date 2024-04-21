package co.bold.weather.views

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import co.bold.weather.R
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

    private companion object {
        const val FORECAST_RESPONSE_KEY = "forecast_response"
    }

    private lateinit var binding: ActivityForecastDetailBinding
    private val weatherViewModel: WeatherViewModel by viewModel()
    private var forecastResponse: ForecastResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        savedInstanceState?.let {
            forecastResponse = it.getParcelable(FORECAST_RESPONSE_KEY)
            setValues()
        } ?: run {
            listenViewModel()
            weatherViewModel.getLocationsForecastByKeyword(intent.getStringExtra("location") ?: "")
        }

        prepareToolbar()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(FORECAST_RESPONSE_KEY, forecastResponse)
    }

    private fun prepareToolbar() {
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setValues() {
        binding.apply {
            pbLoaderImage.isVisible = true
            tvNextDays.isVisible = true
            rvNextDays.isVisible = true
            forecastResponse?.apply {
                tvLocation.text =
                    getString(R.string.city_country_format, location?.name, location?.country)
                tvLocalDate.text = location?.localtime?.toCurrentDayFormat()
                current?.apply {
                    tvCurrentCondition.text = condition?.text ?: ""
                    tvCurrentTemperature.text =
                        getString(
                            R.string.avg_format,
                            tempC?.toCelsius(),
                            forecast?.forecastDayList?.firstOrNull()?.day?.avgTempC?.toCelsius() ?: ""
                        )
                    ivWeatherCondition.setUrlImage(condition?.icon?.cleanUrl() ?: "", pbLoaderImage)
                }
                rvNextDays.layoutManager = LinearLayoutManager(this@ForecastDetailActivity)
                rvNextDays.adapter = NextDaysAdapter(forecast?.forecastDayList?.takeLast(3) ?: listOf())
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
                    forecastResponse = state.forecast
                    setValues()
                }

                else -> {
                    // No - Op
                }
            }
        }
    }
}