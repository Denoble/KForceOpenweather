package com.gevcorst.k_forceopenweather.ui.weatherScreen


import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

import com.gevcorst.k_forceopenweather.ExampleUnitTest

import com.gevcorst.k_forceopenweather.services.UserDataStore


import dagger.hilt.android.testing.HiltAndroidTest

import io.mockk.mockk


import junit.framework.TestCase.assertTrue

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Rule
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import com.gevcorst.k_forceopenweather.R.string as Apptext

@Suppress("UNCHECKED_CAST")
@HiltAndroidTest
@RunWith(JUnit4::class)
class MainViewModelTest {
    private val fakeAddressResult = ExampleUnitTest().fakeResult

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )
    @Mock
    private lateinit var mockContext: Context

    private lateinit var viewModel: MainViewModel

    //@BindValue
    //@JvmField



    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Before
    fun setUp() {
        val datastore = mockk<UserDataStore>(relaxed = true)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = MainViewModel(dataStore = datastore)
       /* every {
            viewModel.uiCityState
        } returns cityState
        every {
            viewModel.cordinate
        } returns  cordinate*/
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testUpdateCityName() = runTest{
        //given
        val resultCity = "New York"
        val fetchLocationAPI =  fakeAddressResult
        val fetchedCity = fetchLocationAPI.addressComponents[0].longName
       // when
        viewModel.updateCityName(fetchedCity)
//       val temp = viewModel.uiCityState.value.name
        assertTrue("Manhattan"  == fetchedCity)

        //assertEquals(viewModel.uiCityState.value.name, resultCity)
    }

    @Test
    fun testFetchCityNameWithCordinate() = runTest {
        val Errormessage = "Api Error !"

        viewModel.fetchLatitudeLongitude("Atlanta",
            "country:US")

            assert(viewModel.uiCityState.value.name == "Atlanta")

    }

    @Test
    fun testFetchLatitudeLongitude() {
        /*viewModel.fetchLatitudeLongitude("New York",
            "country:US")
            assertEquals(viewModel.uiCityState.value.name,"New York")*/

    }

    @Test
    fun testUpDateWrongCity() {
    }

    @Test
    fun testGetDataStore() = runTest {
    }
}

private const val USER_PREFERENCES_NAME = "city_name_data_store"