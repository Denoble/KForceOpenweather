package com.gevcorst.k_forceopenweather.repository


import com.gevcorst.k_forceopenweather.BuildConfig
import com.gevcorst.k_forceopenweather.model.location.Location
import com.gevcorst.k_forceopenweather.model.location.LocationAddress
import com.gevcorst.k_forceopenweather.repository.services.LocationAPIService
import com.gevcorst.k_forceopenweather.repository.services.ReverseLocationAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
class LocationRepositoryImpl @Inject constructor (private val locationAPIService:
                                              LocationAPIService,
                                                  private val reverseLocationAPIService:
                                                  ReverseLocationAPIService):LocationRepository{
    override suspend fun fetchLatitudeLongitude(
        cityName:String,
        countryCode:String, key:String
    ): Flow<Location> = flow{
        val deferedLocation  = locationAPIService.getaddress(cityName,
            countryCode, key)
        val location = deferedLocation.await()
        emit(location)
    }
    override suspend fun fetchCityNameWithCordinate(
        lat: Double, lng: Double,
        key: String
    ):Flow<LocationAddress> = flow {
        val latlng = "$lat,$lng"
        val deferedLocation = reverseLocationAPIService.getReverseAddress(latlng,
            "",
            "",key = key)
        val locationAddress = deferedLocation.await()
        emit(locationAddress)
    }
}
interface LocationRepository{
  suspend  fun  fetchLatitudeLongitude(
        cityName:String,
        countryCode:String, key:String
    ): Flow<Location>
    suspend fun fetchCityNameWithCordinate(
        lat: Double, lng: Double,
        key: String = BuildConfig.GEOCODING_KEY
    ):Flow<LocationAddress>
}
