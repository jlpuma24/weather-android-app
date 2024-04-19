package co.bold.weather.views

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import co.bold.weather.R
import co.bold.weather.data.model.Forecast
import co.bold.weather.data.model.ForecastResponse
import co.bold.weather.databinding.ActivityForecastDetailBinding
import co.bold.weather.views.extensions.cleanUrl
import co.bold.weather.views.extensions.toCelsius
import co.bold.weather.views.extensions.toCurrentDayFormat
import co.bold.weather.views.states.SearchLocationUiState
import co.bold.weather.views.viewmodels.WeatherViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
            pbLoaderImage.isVisible = true
            forecast?.apply {
                val city = location?.name
                val country = location?.country
                tvLocation.text = "$city, $country"
                tvLocalDate.text = location?.localtime?.toCurrentDayFormat()
                current?.apply {
                    tvCurrentCondition.text = condition?.text ?: ""
                    tvCurrentTemperature.text = tempC?.toCelsius()
                    Glide
                        .with(this@ForecastDetailActivity)
                        .load(condition?.icon?.cleanUrl())
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>,
                                isFirstResource: Boolean
                            ): Boolean {
                                pbLoaderImage.isVisible = false
                                ivWeatherCondition.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        this@ForecastDetailActivity,
                                        R.drawable.not_available_weather
                                    )
                                )
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any,
                                target: Target<Drawable>?,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {
                                pbLoaderImage.isVisible = false
                                return false
                            }
                        })
                        .centerCrop()
                        .into(ivWeatherCondition)
                }
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