package com.gevcorst.k_forceopenweather.services

import com.gevcorst.k_forceopenweather.model.location.Location
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

object LocationApi {
    val locationRetrofitServices: LocationAPIService
            by lazy { retrofit.create(LocationAPIService::class.java) }
}