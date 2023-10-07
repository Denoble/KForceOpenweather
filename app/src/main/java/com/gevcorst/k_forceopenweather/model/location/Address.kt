package com.gevcorst.k_forceopenweather.model.location

data class Address(
    val address_components: List<AddressComponent>,
    val formatted_address: String,
    val geometry: Geometry,
    val place_id: String,
    val types: List<String>
)