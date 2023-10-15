package com.gevcorst.k_forceopenweather.di

import com.gevcorst.k_forceopenweather.repository.LocationRepository
import com.gevcorst.k_forceopenweather.repository.LocationRepositoryImpl
import com.gevcorst.k_forceopenweather.repository.WeatherRepository
import com.gevcorst.k_forceopenweather.repository.WeatherRepositoryImpl
import com.gevcorst.k_forceopenweather.repository.services.LocationAPIService
import com.gevcorst.k_forceopenweather.repository.services.OpenWeatherAPIService
import com.gevcorst.k_forceopenweather.repository.services.ReverseLocationAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideLocationRepository (
        locationAPIService: LocationAPIService,
        reversedLocationAPIService: ReverseLocationAPIService
    ): LocationRepositoryImpl {
        return LocationRepositoryImpl(locationAPIService,
            reversedLocationAPIService)
    }
    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherAPIService: OpenWeatherAPIService
    ): WeatherRepositoryImpl {
        return WeatherRepositoryImpl(weatherAPIService)
    }

    @Singleton
    @Provides
    fun provideLocationRepositoryInterface(locationAPIService: LocationAPIService,
                                           reverseLocationAPIService:
                                           ReverseLocationAPIService):LocationRepository =
        LocationRepositoryImpl(locationAPIService,reverseLocationAPIService)

    @Singleton
    @Provides
    fun provideWeatherRepositoryInterface(openWeatherAPIService:
                                          OpenWeatherAPIService):WeatherRepository =
        WeatherRepositoryImpl(openWeatherAPIService)
}
