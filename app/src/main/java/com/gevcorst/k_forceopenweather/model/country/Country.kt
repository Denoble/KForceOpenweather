package com.gevcorst.k_forceopenweather.model.country

import android.os.Parcelable

data class Country(
    val name: String ="United State",
    val code: String ="US"
)
data class City(val name:String ="")
class Countries:ArrayList<Country>()