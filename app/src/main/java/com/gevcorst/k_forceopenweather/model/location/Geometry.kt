package com.gevcorst.k_forceopenweather.model.location


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geometry(
    @Json(name = "bounds")
    val bounds: Bounds,
    @Json(name = "location")
    val location: Cordinate,
    @Json(name = "location_type")
    val locationType: String,
    @Json(name = "viewport")
    val viewport: Viewport
)