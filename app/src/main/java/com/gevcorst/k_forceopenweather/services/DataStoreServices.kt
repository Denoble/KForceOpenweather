package com.gevcorst.k_forceopenweather.services


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

import kotlinx.coroutines.flow.flow


class UserDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserDataStore {

    override fun retrieveCityName(): Flow<String> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map { preference ->
                preference[CITY_NAME_KEY] ?: ""
            }
    }

    override suspend fun saveCityName(name: String) {
        dataStore.edit { preference ->
            preference[CITY_NAME_KEY] = name
        }
    }

    override suspend fun deleteUserId(): Flow<String> {
        return flow {
            dataStore.edit { preference ->
                preference.remove(CITY_NAME_KEY)
                emit("Operation Success!")
            }
        }
    }

    companion object {
        val CITY_NAME_KEY = stringPreferencesKey("CITY_NAME")
    }
}
interface UserDataStore {
    fun retrieveCityName(): Flow<String>
    suspend fun saveCityName(name: String)
    suspend fun deleteUserId(): Flow<String>
}