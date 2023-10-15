package com.gevcorst.k_forceopenweather.ui.weatherScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gevcorst.k_forceopenweather.BuildConfig
import com.gevcorst.k_forceopenweather.Current
import com.gevcorst.k_forceopenweather.model.country.City
import com.gevcorst.k_forceopenweather.model.location.Cordinate
import com.gevcorst.k_forceopenweather.repository.LocationRepositoryImpl
import com.gevcorst.k_forceopenweather.repository.WeatherRepositoryImpl
import com.gevcorst.k_forceopenweather.repository.services.UserDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val dataStore: UserDataStore,
    private val locationRepository: LocationRepositoryImpl,
    private val openWeatherRepositoryImpl: WeatherRepositoryImpl
) : AppViewModel() {
    private val countryCode = mutableStateOf("")
    private var cordinate = mutableStateOf(Cordinate(0.0, 0.0))
    var fahrenheitValue = mutableIntStateOf(0)
    var weatherIconPath = mutableStateOf("")
    var weatherDescription = mutableStateOf("")
    var wrongCity = mutableStateOf(false)
    var uiCityState: MutableState<City> = mutableStateOf(City())
        private set
    private val name
        get() = uiCityState.value.name
    private var currentWeather = mutableStateOf(Current())
    var scope = viewModelScope

    init {

        launchCatching {
            val cityNameJob: Deferred<Flow<String>> = async {
                dataStore.retrieveCityName()
            }
            val cityName = cityNameJob.await()
            cityName.collect {
                uiCityState.value = uiCityState.value.copy(name = it)

            }

        }
    }

    fun updateCityName(newName: String) {
        uiCityState.value = City(name = newName)
        launchCatching {
            fetchLatitudeLongitude(
                newName,
                countryCode.value
            )
            dataStore.saveCityName(uiCityState.value.name)
        }

    }

    fun fetchCityNameWithCordinate(
        lat: Double, lng: Double,
        key: String = BuildConfig.GEOCODING_KEY
    ) {
        val latlng = "$lat,$lng"
        scope.launch {
            try {
                val deferedLocationServices = async {
                    locationRepository.fetchCityNameWithCordinate(
                        lat,lng,key
                    )
                }.await()
                deferedLocationServices.collect{reverseAddress->
                    val currentCityName = reverseAddress.results[0].addressComponents[2].shortName
                    updateCityName(currentCityName)
                    Log.i(
                        "REVERSEAdd",
                        "${reverseAddress}"
                    )
                }

            } catch (e: Exception) {
                Log.i("REVERSEAddERROR", "${e.stackTraceToString()}")
            }
        }
    }

    fun fetchLatitudeLongitude(
        cityName: String, countryCode: String,
        key: String = BuildConfig.GEOCODING_KEY
    ) {
        scope.launch {
            try {
                val jsonString = async {
                    locationRepository.fetchLatitudeLongitude(
                        cityName, countryCode, key
                    )
                }
                jsonString.await().collect { location ->
                    cordinate.value = cordinate.value.copy(
                        location.results[0].geometry.location.lat,
                        location.results[0].geometry.location.lng
                    )
                    when (location.results[0].addressComponents[0].types[0]) {
                        AddressType.Country.type -> {
                            upDateWrongCity(true)
                        }

                        else -> {
                            refreshWeatherData(
                                BuildConfig.OPEN_WEATHER_KEY,
                                openWeatherRepositoryImpl
                            )
                        }
                    }
                    Log.i("LONGITUDE_SUCCESS", "$countryCode $location")
                }

            } catch (e: Exception) {
                Log.d("APIERROR_WEATHER", "${e.printStackTrace()} ${e.localizedMessage ?: " "}")
            }

        }
    }

    fun upDateWrongCity(isWrong: Boolean) {
        wrongCity.value = isWrong
    }

    fun convertToFahrenheit(temp: Double) {
        fahrenheitValue.value = (((temp - 273.0) * 1.8) + 32).toInt()

    }

    private suspend fun refreshWeatherData(
        key: String, openWeatherRepositoryImpl:
        WeatherRepositoryImpl
    ) {

        try {
            viewModelScope.launch {
                val deferedOpenWeatherData = async {
                    openWeatherRepositoryImpl.getCurrentWeather(
                        cordinate.value.lat, cordinate.value.lng, key
                    )
                }.await()
                deferedOpenWeatherData.collect { weatherData ->
                    currentWeather.value = currentWeather.value.copy(
                        clouds = weatherData.current.clouds,
                        dewPoint = weatherData.current.dewPoint,
                        dt = weatherData.current.dt,
                        feelsLike = weatherData.current.feelsLike,
                        humidity = weatherData.current.humidity,
                        pressure = weatherData.current.pressure,
                        sunrise = weatherData.current.sunrise,
                        sunset = weatherData.current.sunset,
                        temp = weatherData.current.temp,
                        uvi = weatherData.current.uvi,
                        visibility = weatherData.current.visibility,
                        weather = weatherData.current.weather,
                        windDeg = weatherData.current.windDeg,
                        windGust = weatherData.current.windGust,
                        windSpeed = weatherData.current.windSpeed
                    )
                    convertToFahrenheit(currentWeather.value.temp)
                    updateWeatherIconPath(currentWeather.value.weather[0].icon)
                    updateWeatherDescription(currentWeather.value.weather[0].description)
                    Log.d("CURRENT_WEATHER", "${weatherData}")
                    Log.d("CURRENT_WEATHER_ICON", "${currentWeather.value.weather[0].icon}")

                }
            }

        } catch (e: Exception) {
            Log.d("WEATHER_ERROR", "${e.stackTraceToString()}")
        }

    }

    private fun updateWeatherDescription(des: String) {
        weatherDescription.value = des
    }

    private fun updateWeatherIconPath(icon: String) {
        weatherIconPath.value = "https://openweathermap.org/img/w/$icon.png"
    }

    fun updateCordinate(lat: Double, lng: Double) {
        cordinate.value = cordinate.value.copy(lat = lat, lng = lng)
    }

    companion object {
        final val DEFAULT_NUMBER = 255.372
    }
}

open class AppViewModel : ViewModel() {
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

enum class AddressType(val type: String) {
    Country("country"),
}