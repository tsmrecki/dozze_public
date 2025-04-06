package com.dozze.storage.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer")
data class TimerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val lastConfirmedMs: Long? = null,
    val confirmAction: String? = null,
    val successAction: String? = null,
    val minConfirmationIntervalMs: Long? = null,
    val remindInMs: Long? = null
)
