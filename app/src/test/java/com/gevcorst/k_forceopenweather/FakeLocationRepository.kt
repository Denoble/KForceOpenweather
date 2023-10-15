package com.gevcorst.k_forceopenweather


import com.gevcorst.k_forceopenweather.model.location.AddressComponent
import com.gevcorst.k_forceopenweather.model.location.Bounds
import com.gevcorst.k_forceopenweather.model.location.Cordinate
import com.gevcorst.k_forceopenweather.model.location.Geometry
import com.gevcorst.k_forceopenweather.model.location.Location
import com.gevcorst.k_forceopenweather.model.location.LocationAddress
import com.gevcorst.k_forceopenweather.model.location.Northeast
import com.gevcorst.k_forceopenweather.model.location.PlusCode
import com.gevcorst.k_forceopenweather.model.location.Result
import com.gevcorst.k_forceopenweather.model.location.Southwest
import com.gevcorst.k_forceopenweather.model.location.Viewport
import com.gevcorst.k_forceopenweather.repository.LocationRepository
import com.gevcorst.k_forceopenweather.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FakeLocationRepository : LocationRepository {

    private val fakeAddressComponent = AddressComponent(
        longName = "Manhattan", shortName = "Manhattan",
        types = listOf("political", "sublocality", "sublocality_level_1")
    )
    private val fakeAddressComponent2 = AddressComponent(
        longName = "New York", shortName = "New York",
        types = listOf("political", "locality")
    )
    private val fakeAddressComponent3 = AddressComponent(
        longName = "New York county", shortName = "New York county",
        types = listOf("administrative_area_level_2", "political")
    )
    private val fakeBounds = Bounds(
        northeast = Northeast(lat = 40.882214, lng = -73.907),
        southwest = Southwest(lat = 40.6803955, lng = -74.0472368)
    )
    private val fakeGeomtry = Geometry(
        bounds = fakeBounds,
        Cordinate(lat = 40.7830603, lng = -73.9712488),
        locationType = "APPROXIMATE",
        viewport =
        Viewport(
            northeast = Northeast(lat = 40.820045, lng = -73.90331300000001),
            southwest = Southwest(lat = 40.698078, lng = -74.03514899999999)
        )
    )
    val fakeResult = Result(
        addressComponents = listOf(
            fakeAddressComponent,
            fakeAddressComponent2, fakeAddressComponent3
        ),
        formattedAddress = "Manhattan",
        geometry = fakeGeomtry,
        placeId = "ChIJYeZuBI9YwokRjMDs_IEyCwo",
        types = listOf("political", "sublocality", "sublocality_level_1")
    )
    private val location = Location(listOf(fakeResult), "")
    private val plusCode = PlusCode("", "")
    private val locationAddress = LocationAddress(plusCode, listOf(fakeResult))

    override suspend fun fetchLatitudeLongitude(
        cityName: String,
        countryCode: String,
        key: String
    ): Flow<Location> = flow {
        emit(location)
    }

    override suspend fun fetchCityNameWithCordinate(
        lat: Double,
        lng: Double,
        key: String
    ): Flow<LocationAddress> = flow {
        emit(locationAddress)
    }

    companion object {
        val lat = 40.783063
        val lng = 73.9712488
        val key = BuildConfig.GEOCODING_KEY
        val countryCode = "country:US"
        val CITY = "Manhattan"
    }
}

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher(),
    TestCoroutineScope by TestCoroutineScope() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(
            this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
        )
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

