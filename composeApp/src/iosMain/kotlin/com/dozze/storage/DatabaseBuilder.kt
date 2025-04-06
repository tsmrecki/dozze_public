package com.dozze.storage

import androidx.room.Room
import androidx.room.RoomDatabase
import com.dozze.storage.database.DozzeDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

internal fun createDatabaseBuilder(): RoomDatabase.Builder<DozzeDatabase> {
    val dbFilePath = documentDirectory() + "/dozze_database.db"
    return Room.databaseBuilder<DozzeDatabase>(
        name = dbFilePath,
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )

    return requireNotNull(documentDirectory?.path)
}