package co.bold.weather.views

import LocationAdapter
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import co.bold.weather.data.model.Location
import co.bold.weather.databinding.ActivitySearchMainBinding
import co.bold.weather.views.extensions.hideKeyboard
import co.bold.weather.views.states.LocationEventClick
import co.bold.weather.views.states.SearchLocationUiState
import co.bold.weather.views.viewmodels.LocationsAdapterViewModel
import co.bold.weather.views.viewmodels.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchMainBinding
    private val weatherViewModel: WeatherViewModel by viewModel()
    private val locationsAdapterViewModel: LocationsAdapterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        observeViewModel()
        prepareListeners()
    }

    private fun prepareListeners() {
        binding.autoCompleteEditText.apply {
            doAfterTextChanged {
                if (it?.isNotEmpty() == true) {
                    weatherViewModel.getLocationsByKeyword(it.toString())
                } else {
                    prepareAdapter(emptyList())
                }
            }
            setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN
                ) {
                    hideKeyboard()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private fun prepareAdapter(locations: List<Location>) {
        binding.rvResults.layoutManager = LinearLayoutManager(this)
        binding.rvResults.adapter = LocationAdapter(locations, locationsAdapterViewModel)
    }

    private fun observeViewModel() {
        weatherViewModel.searchKeywordState.observe(this@MainSearchActivity) { state ->
            when (state) {
                is SearchLocationUiState.ErrorSearchLocation -> {
                    binding.progressBar.isVisible = false
                }

                is SearchLocationUiState.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is SearchLocationUiState.SuccessSearchLocation -> {
                    binding.progressBar.isVisible = false
                    prepareAdapter(state.list)
                }

                else -> {
                    // No-op
                }
            }
        }
        locationsAdapterViewModel.event.observe(this@MainSearchActivity) { event ->
            when (event) {
                is LocationEventClick.ItemClick -> {
                    val location = event.location
                    requestForecastLocation(location)
                }
            }
        }
    }

    private fun requestForecastLocation(location: String) {
        startActivity(Intent(this@MainSearchActivity, ForecastDetailActivity::class.java).apply {
            putExtra("location", location)
        })
    }
}