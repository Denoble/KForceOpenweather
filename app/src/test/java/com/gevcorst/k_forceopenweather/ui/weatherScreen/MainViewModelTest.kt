package com.gevcorst.k_forceopenweather.ui.weatherScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gevcorst.k_forceopenweather.FakeLocationRepository
import com.gevcorst.k_forceopenweather.FakeWeatherData
import com.gevcorst.k_forceopenweather.MainCoroutineRule
import com.gevcorst.k_forceopenweather.repository.services.UserDataStore
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
    }

    @Test
    fun  when_fetchLatitudeLongitude_method_is_called_Location_Data_are_fetched() = runTest {
        // Given
        fakeLocation
        // when
        val locationFlow = async(UnconfinedTestDispatcher(testScheduler)) {
              fakeLocation.fetchLatitudeLongitude(FakeLocationRepository.CITY,
                  FakeLocationRepository.COUNTRY_CODE,
                  FakeLocationRepository.KEY)
          }.await()
        locationFlow.collect{
            //then
            assert(it.results.isEmpty().not())
            assert(it.results[0].addressComponents[0].longName == "Manhattan")
        }

    }
    @Test
    fun when_FetchCityNameWithCordinate_Method_Is_Called_Location_Data_Is_Not_empty() = runTest {
        // Given
        fakeLocation
        // when
        val locationFlow = async(UnconfinedTestDispatcher(testScheduler)) {
            fakeLocation.fetchCityNameWithCordinate (FakeLocationRepository.LAT,
                FakeLocationRepository.LNG,
                FakeLocationRepository.KEY)
        }.await()
        locationFlow.collect{
            //then
            assert(it.results.isEmpty().not())
        }
    }
    @Test
    fun when_GetCurrentWeather_Method_Called_Weather_Data_Not_Empty() = runTest {
        //Given
        fakeWeather

        //when
        val weatherDataFlow = async(UnconfinedTestDispatcher(testScheduler)) {
          fakeWeather.getCurrentWeather(FakeWeatherData.LAT,
              FakeWeatherData.LNG,
              FakeWeatherData.KEY)
        }.await()
        weatherDataFlow.collect{
            assert(it.current.weather.isEmpty().not())
        }
    }
}

private const val USER_PREFERENCES_NAME = "city_name_data_store"