package com.gevcorst.k_forceopenweather.ui.weatherScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Observer
import com.gevcorst.k_forceopenweather.FakeLocationRepository
import com.gevcorst.k_forceopenweather.FakeWeather
import com.gevcorst.k_forceopenweather.FakeWeatherData
import com.gevcorst.k_forceopenweather.MainCoroutineRule
import com.gevcorst.k_forceopenweather.model.country.City
import com.gevcorst.k_forceopenweather.repository.services.UserDataStore
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val fakeAddressResult = FakeLocationRepository().fakeResult
    private lateinit var viewModel: MainViewModel
    private lateinit var dataStore: UserDataStore
    private  lateinit var fakeLocation:FakeLocationRepository
    private lateinit var fakeWeather: FakeWeatherData

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()



    @Before
    fun setup() {

        dataStore = mockk()
        fakeLocation = FakeLocationRepository()
        fakeWeather = FakeWeatherData()
        /*viewModel = MainViewModel(dataStore = dataStore,
            fakeLocation,
            fakeWeather)*/

    }

    @Test
    fun  when_fetchLatitudeLongitude_method_is_called_Location_Data_are_fetched() = runTest {
        // Given
        fakeLocation
        // when
        val temp = async(UnconfinedTestDispatcher(testScheduler)) {
              fakeLocation.fetchLatitudeLongitude(FakeLocationRepository.CITY,
                  FakeLocationRepository.countryCode,
                  FakeLocationRepository.key)
          }.await()
        temp.collect{
            //then
            assert(it.results.isEmpty().not())
        }

    }
}

private const val USER_PREFERENCES_NAME = "city_name_data_store"