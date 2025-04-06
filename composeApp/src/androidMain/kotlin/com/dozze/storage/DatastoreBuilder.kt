package com.dozze.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dozze.storage.datastore.dataStoreFileName

internal fun createDataStore(context: Context): DataStore<Preferences> = com.dozze.storage.datastore.createDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)