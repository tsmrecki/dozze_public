package com.dozze.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dozze.storage.database.entities.TimerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {
    @Query("SELECT * FROM timer")
    fun getTimers(): Flow<List<TimerEntity>>

    @Insert
    suspend fun insert(movie: TimerEntity): Long

    @Query("UPDATE timer SET lastConfirmedMs=:currentMs WHERE id = :id")
    suspend fun update(id: Long, currentMs: Long?)

    @Query("DELETE FROM timer WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM timer")
    suspend fun deleteTimers()
}