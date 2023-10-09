package com.gevcorst.k_forceopenweather

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.gevcorst.k_forceopenweather.ui.theme.KForceOpenWeatherTheme
import com.gevcorst.k_forceopenweather.R.string as AppText
import com.gevcorst.k_forceopenweather.services.LocationApi
import com.gevcorst.k_forceopenweather.BuildConfig
import com.gevcorst.k_forceopenweather.services.weatherApi
import com.gevcorst.k_forceopenweather.ui.composables.Main
import com.gevcorst.k_forceopenweather.ui.weatherScreen.MainViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel:MainViewModel = hiltViewModel()
            viewModel.populateCountryDropDown(LocalContext.current)
            KForceOpenWeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    Main()

                }
                    printResponse()
            }

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KForceOpenWeatherTheme {
        Greeting("Android")
    }
}

fun printResponse(key:String =  BuildConfig.GEOCODING_KEY) {
    val scope = MainScope()
    scope.launch {
        try{
            val jsonString =
                LocationApi.locationRetrofitServices.getaddress(
                    "Bamako",
                    "${AppText.countryCodePrefix}" +"ML",key
                ).await()

            val weather =  weatherApi.weatherRetrofitServices.getCurrentWeather(
                jsonString.results[0].geometry.location.lat,
                jsonString.results[0].geometry.location.lat,BuildConfig.OPEN_WEATHER_KEY).await()

            Log.d("APINOERROR", "${jsonString.results}")

            Log.d("APINOERROR_WEATHER", "${weather.current.weather[0].icon}")

        }catch (e:Exception){
            Log.d("APIERROR_WEATHER", "${e.printStackTrace()} ${e.localizedMessage ?: " "}")
        }

    }
}