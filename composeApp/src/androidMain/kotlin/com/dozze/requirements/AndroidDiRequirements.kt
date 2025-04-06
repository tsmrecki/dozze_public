package com.dozze.requirements

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import com.dozze.storage.createDataStore
import com.dozze.storage.createDatabaseBuilder
import com.dozze.storage.database.DozzeDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


internal actual val platformModule: Module = module {
    // Platform specific dependencies
    single<DataStore<Preferences>> { createDataStore(context = androidContext()) }
    single<RoomDatabase.Builder<DozzeDatabase>> { createDatabaseBuilder(context = androidContext()) }
}
