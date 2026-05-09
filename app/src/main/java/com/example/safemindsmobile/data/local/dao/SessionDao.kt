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

    @Query("SELECT EXISTS(SELECT 1 FROM sleep_summaries WHERE dataId = :id)")
    suspend fun exists(id: String): Boolean
}