package com.gevcorst.k_forceopenweather.model.location


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "address_components")
    val addressComponents: List<AddressComponent>,
    @Json(name = "formatted_address")
    val formattedAddress: String,
    @Json(name = "geometry")
    val geometry: Geometry,
    @Json(name = "place_id")
    val placeId: String,
    @Json(name = "types")
    val types: List<String>
)