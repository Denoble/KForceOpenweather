package com.gevcorst.k_forceopenweather.model.country

import android.os.Parcelable

data class Country(
    val name: String,
    val code: String
)
class Countries:ArrayList<Country>()