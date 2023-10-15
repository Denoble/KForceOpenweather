package com.gevcorst.k_forceopenweather.repository.services

import com.gevcorst.k_forceopenweather.model.location.Location
import com.gevcorst.k_forceopenweather.model.location.LocationAddress
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

private const val LOCATION_BASE_URL = "https://maps.googleapis.com/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(LOCATION_BASE_URL)
    .build()

interface LocationAPIService {
    @GET("maps/api/geocode/json")
    fun getaddress(
        @Query("address") city: String,
        @Query("components") countryCode: String,
        @Query("key") key: String
    ): Deferred<Location>
}
interface ReverseLocationAPIService {
    @GET("maps/api/geocode/json")
    fun getReverseAddress(
        @Query("latlng") latlng: String,
        @Query("location_type") location_type: String = "ROOFTOP",
        @Query("result_type") result_type: String = "street_address",
        @Query("key") key: String
    ): Deferred<LocationAddress>
}
object LocationApi {
    val locationRetrofitServices: LocationAPIService
            by lazy { retrofit.create(LocationAPIService::class.java) }
}
object  ReverseLocationApi{
    val reverseLocationRetrofitService: ReverseLocationAPIService
    by lazy {
        retrofit.create(ReverseLocationAPIService::class.java)
    }
}