package com.dozze.storage.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import okio.Path.Companion.toPath

/**
 * Gets the singleton DataStore instance, creating it if necessary.
 */
internal fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() },
        corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() }
    )

const val dataStoreFileName = "dozze.preferences_pb"