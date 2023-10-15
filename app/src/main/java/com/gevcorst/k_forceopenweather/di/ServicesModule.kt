package com.gevcorst.k_forceopenweather.di

import com.gevcorst.k_forceopenweather.repository.services.UserDataStore
import com.gevcorst.k_forceopenweather.repository.services.UserDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun bindUserPreferences(impl: UserDataStoreImpl): UserDataStore
}
