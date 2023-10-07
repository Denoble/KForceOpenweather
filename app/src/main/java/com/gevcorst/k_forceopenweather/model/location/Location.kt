package com.gevcorst.k_forceopenweather.model.location


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "results")
    val results: List<Result>,
    @Json(name = "status")
    val status: String
)