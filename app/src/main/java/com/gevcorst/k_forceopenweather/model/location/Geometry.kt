package com.gevcorst.k_forceopenweather.model.location

data class Geometry(
    val bounds: Bounds,
    val cordinate: Cordinate,
    val location_type: String,
    val viewport: Viewport
)