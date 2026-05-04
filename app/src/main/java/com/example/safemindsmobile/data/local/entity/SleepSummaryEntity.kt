package com.example.safemindsmobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_summaries")
data class SleepSummaryEntity(
    @PrimaryKey val dataID: String,
    val timeStamp: Long,
    val userID: String,
    val hrMean: Float?,
    val hrMin: Float?,
    val hrMax: Float?,
    val movementMean: Float,
    val movementVariance: Float,
    val totalEpochs: Int
)
