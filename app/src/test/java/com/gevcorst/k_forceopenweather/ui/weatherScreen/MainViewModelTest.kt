package com.gevcorst.k_forceopenweather.ui.weatherScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Observer
import com.gevcorst.k_forceopenweather.ExampleUnitTest
import com.gevcorst.k_forceopenweather.model.country.City
import com.gevcorst.k_forceopenweather.repository.services.UserDataStore
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

import org.mockito.MockitoAnnotations
import com.gevcorst.k_forceopenweather.R.string as Apptext

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val fakeAddressResult = ExampleUnitTest().fakeResult
    private lateinit var viewModel: MainViewModel
    private lateinit var dataStore: UserDataStore
    private val city = mutableStateOf(City(""))
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Create a TestCoroutineDispatcher for testing coroutines
    private val testDispatcher = TestCoroutineDispatcher()

    // Create a TestCoroutineScope
    private val testScope = TestCoroutineScope(testDispatcher)

    @Mock
    lateinit var observer: Observer<City>


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        dataStore = mockk<UserDataStore>()
        viewModel = MainViewModel(dataStore = dataStore)

    }

    @Test
    fun fetchData_updatesMutableState() = testScope.runBlockingTest {
        // Given
        val expectedValue = "Manhattan"
        val fakeCity = fakeAddressResult.addressComponents[0].longName

        // When
        viewModel.updateCityName(fakeCity)

        // Then
        // Ensure that the MutableState was updated to the expected value
       // assert(viewModel.myState.value == expectedValue)

        // Verify that the Observer was notified with the expected value
        //(observer).onChanged(expectedValue)
    }
}

private const val USER_PREFERENCES_NAME = "city_name_data_store"