package com.capstone.moru.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingPreference constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }

    fun getUserToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_TOKEN_KEY] ?: ""
        }
    }

    suspend fun clearCache(){
        dataStore.edit {
            it.clear()
        }
    }

    suspend fun setUserIsFirstTime(status: Boolean){
        dataStore.edit {
            preferences -> preferences[USER_LOGGED_IN_STATUS] = status
        }
    }

    suspend fun getUserIsFirstTime(): Flow<Boolean>{
        return dataStore.data.map{
            preferences -> preferences[USER_LOGGED_IN_STATUS] ?: false
        }
    }

    companion object {
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token_key")
        private val USER_LOGGED_IN_STATUS = booleanPreferencesKey("user_logged_in_status")

        @Volatile
        private var INSTANCE: SettingPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}