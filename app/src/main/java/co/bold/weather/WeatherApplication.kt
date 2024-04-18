package co.bold.weather

import androidx.multidex.MultiDexApplication
import co.bold.weather.di.apiServicesModule
import co.bold.weather.di.networkingModules
import co.bold.weather.di.repositoryModule
import co.bold.weather.di.useCasesModule
import co.bold.weather.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            androidLogger()
            modules(
                networkingModules,
                apiServicesModule,
                repositoryModule,
                useCasesModule,
                viewModelModules
            )
        }
    }
}