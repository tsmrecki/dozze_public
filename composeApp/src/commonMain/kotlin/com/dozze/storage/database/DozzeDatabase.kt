package com.dozze.storage.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.dozze.storage.database.dao.TimerDao
import com.dozze.storage.database.entities.TimerEntity

@Database(entities = [TimerEntity::class], version = 1)
@ConstructedBy(DozzeDatabaseConstructor::class)
abstract class DozzeDatabase : RoomDatabase() {
    abstract fun getTimerDao(): TimerDao
}

// Room compiler generates the `actual` implementations
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object DozzeDatabaseConstructor : RoomDatabaseConstructor<DozzeDatabase> {
    override fun initialize(): DozzeDatabase
}