package com.capstone.moru.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
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

    suspend fun saveUserId(id: Int) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id
        }
    }

    fun getUserId(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY] ?: 0
        }
    }

    suspend fun saveEmailUser(email: String) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = email
        }
    }

    fun getEmailUser(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }
    }

    suspend fun saveImageUser(imageUrl: String) {
        dataStore.edit { preferences ->
            preferences[USER_PHOTO_KEY] = imageUrl
        }
    }

    fun getImageUser(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_PHOTO_KEY] ?: ""
        }
    }

    suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    fun getUsername(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USERNAME_KEY] ?: ""
        }
    }

    suspend fun saveFillProfile(status: Int) {
        dataStore.edit { preferences ->
            preferences[USER_FILL_PROFILE_KEY] = status
        }
    }

    fun getFillProfile(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[USER_FILL_PROFILE_KEY] ?: 0
        }
    }

    suspend fun clearCache() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token_key")
        private val USER_ID_KEY = intPreferencesKey("user_id_key")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email_key")
        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val USER_FILL_PROFILE_KEY = intPreferencesKey("user_fill_profile_key")
        private val USER_PHOTO_KEY = stringPreferencesKey("user_photo_key")


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