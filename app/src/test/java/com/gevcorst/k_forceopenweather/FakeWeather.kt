package com.gevcorst.k_forceopenweather

import com.gevcorst.k_forceopenweather.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeather{
    private val weather = Weather(description="clear sky", icon="01d", id=800, main="Clear")
    private val current = Current(clouds = 0,
        dewPoint = 282.89,
        dt = 1697057724,
        feelsLike = 293.75,
        humidity = 48,
        pressure = 1010,
        sunrise = 1697033540,
        sunset = 1697074652,
        temp = 294.32,
        uvi = 5.09,
        visibility = 10000,
        weather = listOf(weather),
        windDeg = 320,
        windGust = 0.0,
        windSpeed = 7.2
        )
    private val daily = Daily(clouds=0, dewPoint=281.26, dt=1697050800,
        feelsLike= FeelsLike( day=292.85, eve=290.82, morn=286.49, night=286.34),
        humidity=45,moonPhase=0.91,
        moonrise=1697022840,
         moonset=1697070660, pop=0.0, pressure=1011,
        summary="There will be partly cloudy until morning, then clearing",
        sunrise=1697033540,
        sunset=1697074652,
        temp=Temp(day=293.58, eve=291.54, max=294.32, min=286.64, morn=286.87, night=286.83),
        uvi=5.42,
        weather=listOf(Weather(description="clear sky", icon="01d", id=800, main="Clear")),
        windDeg=305, windGust=7.66, windSpeed=5.15)
    private val hourly = Hourly(clouds=0.0, dewPoint=288.42,
        dt=1697392800, feelsLike=294.78, humidity=67.0,
        pop=0.0, pressure=1019, temp=294.81, uvi=3.3,
        visibility=10000.0,
        weather=listOf(Weather(description="clear sky", icon="01d", id=800, main="Clear")),
        windDeg=345.0, windGust=1.12, windSpeed=1.64)
    private val minutely = listOf<Minutely>( Minutely(dt=1697393940, precipitation=0),
        Minutely(dt=1697394000, precipitation=0),
        Minutely(dt=1697394060, precipitation=0))
val openWeatherData = OpenWeatherData(current, daily = listOf(daily),
    timezone = "America/Los_Angeles", hourly = listOf(hourly), lat =  37.3861,
    lon =  -122.0839,
    minutely = minutely, timezoneOffset =  -25200,)
}

class FakeWeatherData : WeatherRepository {
    override suspend fun getCurrentWeather(
        lat: Double,
        ln: Double,
        key: String
    ): Flow<OpenWeatherData> = flow {
        emit(FakeWeather().openWeatherData)
    }
  companion object{
      val LAT = 37.3861
      val LNG = -122.0839
      val KEY = BuildConfig.OPEN_WEATHER_KEY
  }
}