package com.dozze.requirements

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import com.dozze.storage.createDataStore
import com.dozze.storage.createDatabaseBuilder
import com.dozze.storage.database.DozzeDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<DataStore<Preferences>> { createDataStore() }
    single<RoomDatabase.Builder<DozzeDatabase>> { createDatabaseBuilder() }
}
