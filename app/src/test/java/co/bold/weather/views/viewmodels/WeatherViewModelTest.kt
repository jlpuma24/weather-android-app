package co.bold.weather.views.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.bold.weather.data.model.ForecastResponse
import co.bold.weather.data.model.Location
import co.bold.weather.domain.usecases.SearchByKeywordUseCase
import co.bold.weather.domain.usecases.SearchForecastByKeywordUseCase
import co.bold.weather.views.states.SearchLocationUiState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherViewModel
    private val searchByKeywordUseCase: SearchByKeywordUseCase = mockk()
    private val searchForecastByKeywordUseCase: SearchForecastByKeywordUseCase = mockk()

    private val testScope = TestCoroutineScope()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = WeatherViewModel(searchByKeywordUseCase, searchForecastByKeywordUseCase)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        clearAllMocks()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `test getLocationsByKeyword success`() = testScope.runBlockingTest {
        coEvery { searchByKeywordUseCase.invoke(any()) } returns flowOf(
            listOf(
                Location(name = "Medellin"),
                Location(name = "Bogotá")
            )
        )
        viewModel.getLocationsByKeyword("keyword")
        assert(viewModel.searchKeywordState.value is SearchLocationUiState.SuccessSearchLocation)
    }

    @Test
    fun `test getLocationsByKeyword failure`() = testScope.runTest {
        coEvery { searchByKeywordUseCase.invoke(any()) } returns flowOf(emptyList())
        viewModel.getLocationsByKeyword("keyword")
        assert(viewModel.searchKeywordState.value is SearchLocationUiState.ErrorSearchLocation)
    }

    @Test
    fun `test searchForecastByKeywordUseCase success`() = testScope.runBlockingTest {
        coEvery { searchForecastByKeywordUseCase.invoke(any()) } returns flowOf(
            ForecastResponse()
        )
        viewModel.getLocationsForecastByKeyword("keyword")
        assert(viewModel.searchKeywordState.value is SearchLocationUiState.SuccessSearchForecastLocation)
    }

    @Test
    fun `test searchForecastByKeywordUseCase failure`() = testScope.runBlockingTest {
        coEvery { searchForecastByKeywordUseCase.invoke(any()) } returns flowOf(
            null
        )
        viewModel.getLocationsForecastByKeyword("keyword")
        assert(viewModel.searchKeywordState.value is SearchLocationUiState.ErrorSearchForecastLocation)
    }
}