package com.gevcorst.k_forceopenweather.di

import com.gevcorst.k_forceopenweather.repository.LocationRepository
import com.gevcorst.k_forceopenweather.repository.WeatherRepository
import com.gevcorst.k_forceopenweather.repository.services.LocationAPIService
import com.gevcorst.k_forceopenweather.repository.services.OpenWeatherAPIService
import com.gevcorst.k_forceopenweather.repository.services.ReverseLocationAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideLocationRepository (
        locationAPIService: LocationAPIService,
        reversedLocationAPIService: ReverseLocationAPIService
    ): LocationRepository {
        return LocationRepository(locationAPIService,
            reversedLocationAPIService)
    }
    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherAPIService: OpenWeatherAPIService
    ): WeatherRepository {
        return WeatherRepository(weatherAPIService)
    }
}
