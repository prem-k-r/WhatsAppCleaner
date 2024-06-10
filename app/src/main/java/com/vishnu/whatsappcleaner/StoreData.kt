package com.vishnu.whatsappcleaner

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class StoreData(val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "store_data")
    }

    suspend fun set(key: String, value: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    suspend fun get(key: String): String? {
        return context.dataStore.data.first().get(
            stringPreferencesKey(key)
        )
    }
}