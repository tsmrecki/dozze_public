package com.dozze.storage.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class Datastore(private val store: DataStore<Preferences>) {

    suspend fun <T> write(key: Preferences.Key<T>, value: T) {
        store.edit { prefs ->
            prefs[key] = value
        }
    }

    fun <T> read(key: Preferences.Key<T>): Flow<T?> {
        return store.data.map { prefs -> prefs[key] }.map { if(it == null || it == 0L) null else it }
    }

    companion object {
        val IS_PREFILLED_AT_LAUNCH = booleanPreferencesKey("is_prefilled_at_launch")
    }

}