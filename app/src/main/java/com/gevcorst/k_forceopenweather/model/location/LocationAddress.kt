package com.gevcorst.k_forceopenweather.model.location

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationAddress(
    @field:Json(name = "plus_code")
    val plusCode: PlusCode = PlusCode(),
    val results: List<Result> = listOf(),
    val status: String = ""
)
@JsonClass(generateAdapter = true)
data class PlusCode(
    @Json(name = "compound_code")
    val compoundCode: String = "",
    @Json(name = "global_code")
    val globalCode: String = ""
)
