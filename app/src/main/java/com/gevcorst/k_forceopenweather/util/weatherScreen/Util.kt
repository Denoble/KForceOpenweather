package com.gevcorst.k_forceopenweather.util.weatherScreen

import android.content.Context
import com.gevcorst.k_forceopenweather.model.country.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object ReadLocalJsonFile {
    fun readJsonLocally(context: Context, fileName: String): String {
        var jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
        } catch (e: IOException) {
            jsonString = e.localizedMessage ?: "Problem reading json file"
        }
        return jsonString
    }

    fun mapJsonToCountry(json: String): List<Country> {
        val gson = Gson()
        val countries = object : TypeToken<List<Country>>() {}.type
        return gson.fromJson(json, countries)
    }
}