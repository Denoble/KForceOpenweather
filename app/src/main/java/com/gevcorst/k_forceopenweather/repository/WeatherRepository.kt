package com.gevcorst.k_forceopenweather.repository

import com.gevcorst.k_forceopenweather.OpenWeatherData
import com.gevcorst.k_forceopenweather.repository.services.OpenWeatherAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepository(private val openWeatherAPIService: OpenWeatherAPIService){
    suspend fun getCurrentWeather(
        lat:Double,
        ln:Double,key:String
    ): Flow<OpenWeatherData> = flow{
        val deferedWeatherData  = openWeatherAPIService.getCurrentWeather(lat,
            ln,key)
        val weatherData = deferedWeatherData.await()
        emit(weatherData)
    }
}