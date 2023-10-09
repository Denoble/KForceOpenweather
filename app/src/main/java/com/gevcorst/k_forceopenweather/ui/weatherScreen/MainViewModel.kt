package com.gevcorst.k_forceopenweather.ui.weatherScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gevcorst.k_forceopenweather.BuildConfig
import com.gevcorst.k_forceopenweather.Current
import com.gevcorst.k_forceopenweather.OpenWeatherData
import com.gevcorst.k_forceopenweather.R
import com.gevcorst.k_forceopenweather.model.country.City
import com.gevcorst.k_forceopenweather.model.country.Country
import com.gevcorst.k_forceopenweather.model.location.Cordinate
import com.gevcorst.k_forceopenweather.services.LocationApi
import com.gevcorst.k_forceopenweather.services.weatherApi
import com.gevcorst.k_forceopenweather.util.weatherScreen.ReadLocalJsonFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : AppViewModel() {
    var countries = mutableStateOf(mutableListOf(""))
    val countryCode = mutableStateOf(Country().code)
    var cordinate = mutableStateOf(Cordinate(0.0, 0.0))
    var fahrenheitValue = mutableStateOf(0)
    var  weatherIconPath = mutableStateOf("")
    var uiCityState = mutableStateOf(City())
        private set
    private val name
        get() = uiCityState.value.name
    var currentWeather = mutableStateOf(Current())
    fun populateCountryDropDown(appContext: Context) {
        viewModelScope.launch {
            val deferedCountries: Deferred<List<Country>> =
                async(Dispatchers.IO) {
                    val jsonString =
                        ReadLocalJsonFile.readJsonLocally(
                            appContext, "countries.json"
                        )
                    ReadLocalJsonFile.mapJsonToCountry(jsonString)
                }
            try {
                deferedCountries.await().map {
                    val tempString = it.name + " " + it.code
                    countries.value.add(tempString)
                }
                Log.i("MainViewModel", "${countries.value}")
            } catch (e: Exception) {

            }
        }
    }

    fun updateCountryCode(country: String) {
        try {
            if (country.isNotEmpty()) {
                val countryCodeArray = country.split(" ")
                val arraySize = countryCodeArray.size
                countryCode.value = countryCodeArray[arraySize - 1]
            }
        } catch (e: Exception) {

        }
    }

    fun updateCityName(newName: String) {
        uiCityState.value = City(name = newName)
    }

    fun fetchLatitudeLongitude(
        cityName: String, countryCode: String = this.countryCode.value,
        key: String = BuildConfig.GEOCODING_KEY
    ) {
        val scope = MainScope()
        scope.launch {
            try {
                val jsonString =
                    LocationApi.locationRetrofitServices.getaddress(
                        cityName,
                        countryCode, key
                    ).await()
                cordinate.value = cordinate.value.copy(
                    jsonString.results[0].geometry.location.lat,
                    jsonString.results[0].geometry.location.lng
                )
                val weather = openWeatherData(BuildConfig.OPEN_WEATHER_KEY)

            } catch (e: Exception) {
                Log.d("APIERROR_WEATHER", "${e.printStackTrace()} ${e.localizedMessage ?: " "}")
            }

        }
    }

    private fun convertToFahrenheit(temp: Double) {
        fahrenheitValue.value = (((temp - 273.0) * 1.8) + 32).toInt()

    }

    private suspend fun openWeatherData(key: String): OpenWeatherData {
        val weather = weatherApi.weatherRetrofitServices.getCurrentWeather(
            cordinate.value.lat, cordinate.value.lng, key
        ).await()
        try {
            currentWeather.value = currentWeather.value.copy(
                clouds = weather.current.clouds,
                dewPoint = weather.current.dewPoint,
                dt = weather.current.dt,
                feelsLike = weather.current.feelsLike,
                humidity = weather.current.humidity,
                pressure = weather.current.pressure,
                sunrise = weather.current.sunrise,
                sunset = weather.current.sunset,
                temp = weather.current.temp,
                uvi = weather.current.uvi,
                visibility = weather.current.visibility,
                weather = weather.current.weather,
                windDeg = weather.current.windDeg,
                windGust = weather.current.windGust,
                windSpeed = weather.current.windSpeed

            )
            convertToFahrenheit(currentWeather.value.temp)
            updateWeatherIconPath(currentWeather.value.weather[0].icon)
            Log.d("CURRENT_WEATHER_ICON", "${currentWeather.value.weather[0].icon}")
        } catch (e: Exception) {
            Log.d("WEATHER_ERROR", "${e.stackTraceToString()}")
        }
        return weather
    }
    private fun updateWeatherIconPath(icon:String){
       weatherIconPath.value = "https://openweathermap.org/img/w/$icon.png"
    }

}

open class AppViewModel() : ViewModel() {
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    // CustomSnackbar.showMessage(throwable.getErrorMessage())
                }
                //logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}