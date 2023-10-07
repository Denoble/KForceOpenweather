package com.gevcorst.k_forceopenweather.model.location


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Bounds(
    @Json(name = "northeast")
    val northeast: Northeast,
    @Json(name = "southwest")
    val southwest: Southwest
)