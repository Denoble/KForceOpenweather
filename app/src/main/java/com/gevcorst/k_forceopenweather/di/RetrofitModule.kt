package com.gevcorst.k_forceopenweather.di

import com.gevcorst.k_forceopenweather.repository.services.LocationAPIService
import com.gevcorst.k_forceopenweather.repository.services.OpenWeatherAPIService
import com.gevcorst.k_forceopenweather.repository.services.ReverseLocationAPIService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

private const val BASE_URL = "https://maps.googleapis.com/"
private const val OPENWEATHER_MAP_BASE_URL = "https://api.openweathermap.org"
@Module
@InstallIn(SingletonComponent::class)
object RetrofixModule {

    @Singleton
    @Provides
    fun provideKotlinJsonAdapterFactory(): KotlinJsonAdapterFactory = KotlinJsonAdapterFactory()


    @Singleton
    @Provides
    fun provideMoshi(kotlinJsonAdapterFactory: KotlinJsonAdapterFactory): Moshi = Moshi.Builder()
        .add(kotlinJsonAdapterFactory)
        .build()


    @Singleton
    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    @Singleton
    @Provides
    @Named("location_retrofit")
    fun provideRetrofit(
        okHttp: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttp)
            .baseUrl(BASE_URL)
            .build()
    }
    @Singleton
    @Provides
    @Named("weather_retrofit")
    fun provideWeatherRetrofit(
        okHttp: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttp)
            .baseUrl(OPENWEATHER_MAP_BASE_URL)
            .build()
    }
    @Singleton
    @Provides
    fun LocationAPIService(@Named("location_retrofit") retrofit: Retrofit): LocationAPIService =
        retrofit.create(LocationAPIService::class.java)
    @Singleton
    @Provides
    fun provideOpenWeatherAPI(@Named("weather_retrofit") retrofit: Retrofit): OpenWeatherAPIService =
        retrofit.create(OpenWeatherAPIService::class.java)

    @Singleton
    @Provides
    fun provideReversedLocationAPI(@Named("location_retrofit") retrofit: Retrofit): ReverseLocationAPIService =
        retrofit.create(ReverseLocationAPIService::class.java)
}