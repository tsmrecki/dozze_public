package com.dozze.storage

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dozze.storage.database.DozzeDatabase

internal fun createDatabaseBuilder(context: Context): RoomDatabase.Builder<DozzeDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("dozze_database.db")

    return Room.databaseBuilder<DozzeDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
}