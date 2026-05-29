package com.example.safemindsmobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.safemindsmobile.data.local.entity.HourlyCheckEntity
import com.example.safemindsmobile.data.local.entity.SleepSummaryEntity
import com.example.safemindsmobile.data.local.entity.SyncStateEntity

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSleep(session: SleepSummaryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHourly(session: HourlyCheckEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncState(state: SyncStateEntity)

    @Query("SELECT * FROM sync_state WHERE synced = 0")
    suspend fun getUnsyncedSessions(): List<SyncStateEntity>

    @Query("""
        UPDATE sync_state 
        SET synced = 1, lastError = NULL, updatedAt = :updatedAt 
        WHERE dataID = :dataID
    """)
    suspend fun markSynced(dataID: String, updatedAt: Long = System.currentTimeMillis())

    @Query("""
        UPDATE sync_state 
        SET synced = 0, lastError = :error, updatedAt = :updatedAt 
        WHERE dataID = :dataID
    """)
    suspend fun markFailed(
        dataID: String,
        error: String?,
        updatedAt: Long = System.currentTimeMillis()
    )

    @Query("SELECT EXISTS(SELECT 1 FROM sleep_summaries WHERE dataID = :id)")
    suspend fun existsSleep(id: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM hourly_checks WHERE dataID = :id)")
    suspend fun existsHourly(id: String): Boolean
}