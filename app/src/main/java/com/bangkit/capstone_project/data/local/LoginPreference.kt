package com.bangkit.capstone_project.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bangkit.capstone_project.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import java.util.concurrent.TimeUnit

class LoginPreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val TOKEN = stringPreferencesKey("session_token")
    private val NAME = stringPreferencesKey("session_name")
    private val ID = stringPreferencesKey("session_id")
    private val CREATION_TIME = stringPreferencesKey("creation_time")

    fun getLoginSession(): Flow<UserModel?> {
        return dataStore.data.map { preferences ->
            val userModel = UserModel(
                preferences[NAME],
                preferences[TOKEN],
                preferences[ID]
            )

            deleteExpiredSessions(preferences)

            userModel
        }
    }


    suspend fun saveSession(userModel: UserModel) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = userModel.token as String
            preferences[NAME] = userModel.name as String
            preferences[ID] = userModel.id as String
            preferences[CREATION_TIME] = Date().time.toString()
        }
    }
    suspend fun deleteSession() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN)
            preferences.remove(NAME)
            preferences.remove(ID)
            preferences.remove(CREATION_TIME)
        }
    }
    private suspend fun deleteExpiredSessions(preferences: Preferences) {
        val currentTime = Date().time
        val expirationTime = TimeUnit.DAYS.toMillis(3)

        val creationTime = preferences[CREATION_TIME]?.toLongOrNull() ?: 0L
        if (currentTime - creationTime >= expirationTime) {
            dataStore.edit { pref ->
                pref.remove(TOKEN)
                pref.remove(NAME)
                pref.remove(ID)
                pref.remove(CREATION_TIME)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LoginPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): LoginPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = LoginPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
