package com.gevcorst.k_forceopenweather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hourly(
    @Json(name = "clouds")
    val clouds: Double,
    @Json(name = "dew_point")
    val dewPoint: Double,
    @Json(name = "dt")
    val dt: Int,
    @Json(name = "feels_like")
    val feelsLike: Double,
    @Json(name = "humidity")
    val humidity: Double,
    @Json(name = "pop")
    val pop: Double,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "uvi")
    val uvi: Double,
    @Json(name = "visibility")
    val visibility: Double,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "wind_deg")
    val windDeg: Double,
    @Json(name = "wind_gust")
    val windGust: Double,
    @Json(name = "wind_speed")
    val windSpeed: Double
)