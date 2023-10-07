package com.gevcorst.k_forceopenweather.ui.weatherScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gevcorst.k_forceopenweather.model.country.Country
import com.gevcorst.k_forceopenweather.util.weatherScreen.ReadLocalJsonFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): AppViewModel(){
    var countries =  mutableStateOf(listOf(Country("","")))
    fun populateCountryDropDown(appContext:Context) {
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
                countries.value = deferedCountries.await()
                Log.i("MainViewModel","${countries}")
            }catch(e:Exception){

            }
        }
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