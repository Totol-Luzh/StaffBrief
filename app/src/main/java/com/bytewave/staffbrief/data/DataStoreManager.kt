package com.bytewave.staffbrief.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.bytewave.staffbrief.common.Constants
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class DataStoreManager(val context: Context) {

    suspend fun saveTheme(isDark: Boolean) {
        context.dataStore.edit { pref ->
            pref[Constants.THEME_KEY] = isDark
        }
    }

    fun loadTheme() = context.dataStore.data.map { pref ->
        pref[Constants.THEME_KEY] == true
    }
}