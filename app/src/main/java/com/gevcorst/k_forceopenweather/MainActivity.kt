package com.gevcorst.k_forceopenweather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.gevcorst.k_forceopenweather.R.string as AppText
import com.gevcorst.k_forceopenweather.ui.theme.KForceOpenWeatherTheme
import com.gevcorst.k_forceopenweather.ui.composables.Main
import com.gevcorst.k_forceopenweather.ui.weatherScreen.MainViewModel
import com.gevcorst.k_forceopenweather.util.weatherScreen.checkPermissons
import com.gevcorst.k_forceopenweather.util.weatherScreen.isLocationEnabled
import com.gevcorst.k_forceopenweather.util.weatherScreen.requestLocationPermissions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity(),LocationListener {
     val viewModel:MainViewModel   by viewModels()
    lateinit var locationManager:LocationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            getLocation()
            var locationPermissionsGranted = remember {
                mutableStateOf(checkPermissons(context))
            }
            if(locationPermissionsGranted.value){

            }else{
              requestLocationPermissions(this)
            }
            //getLocation()
            KForceOpenWeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main(viewModel)
                }
            }

        }
    }
    private fun getLocation() {
        var  locationManager = application
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (checkPermissons(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestLocationPermissions(this)
            }
            locationManager .requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000, 5f, this
            )
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000, 5f, this
            )
        }
    }

    override fun onLocationChanged(location: Location) {
        val lat = location.latitude
        val lng = location.longitude
        viewModel.updateCordinate(lat, lng)
        viewModel.fetchCityNameWithCordinate(lat, lng)
    }

}

