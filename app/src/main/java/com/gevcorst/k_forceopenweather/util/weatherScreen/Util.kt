package com.gevcorst.k_forceopenweather.util.weatherScreen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gevcorst.k_forceopenweather.model.country.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

val LOCATION_PERMISSION_ID = 42
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


fun requestLocationPermissions(activity: Activity) {
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        LOCATION_PERMISSION_ID
    )
}
 fun checkPermissons(context: Context): Boolean {
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ContextCompat.checkSelfPermission(
            context.applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
           context.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else false
    }
    return permission

}
fun isLocationEnabled(context: Context): Boolean {
    var locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}