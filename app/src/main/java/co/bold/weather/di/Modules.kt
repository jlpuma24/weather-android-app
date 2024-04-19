package co.bold.weather.di

import co.bold.weather.BuildConfig
import co.bold.weather.data.remote.WeatherAppSearchApiService
import co.bold.weather.data.repository.WeatherAppRepositoryImpl
import co.bold.weather.domain.WeatherAppRepository
import co.bold.weather.domain.usecases.SearchByKeywordUseCase
import co.bold.weather.domain.usecases.SearchForecastByKeywordUseCase
import co.bold.weather.views.viewmodels.LocationsAdapterViewModel
import co.bold.weather.views.viewmodels.WeatherViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkingModules = module {
    factory<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
    }
}

val apiServicesModule = module {
    single<WeatherAppSearchApiService> {
        get<Retrofit>().create(WeatherAppSearchApiService::class.java)
    }
}

val repositoryModule = module {
    single<WeatherAppRepository> { WeatherAppRepositoryImpl(get()) }
}

val useCasesModule = module {
    single { SearchByKeywordUseCase(get()) }
    single { SearchForecastByKeywordUseCase(get()) }
}

val viewModelModules = module {
    viewModel { WeatherViewModel(get(), get()) }
    viewModel { LocationsAdapterViewModel() }
}