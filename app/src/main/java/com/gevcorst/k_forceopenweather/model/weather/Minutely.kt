package com.gevcorst.k_forceopenweather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Minutely(
    @Json(name = "dt")
    val dt: Int,
    @Json(name = "precipitation")
    val precipitation: Int
)