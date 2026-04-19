package com.example.safemindsmobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "hourly_checks")
data class HourlyCheckEntity(
    @PrimaryKey val dataID: String,
    val timeStamp: Long,
    val userID: String,
    val hrMean: Float?,
    val movementMean: Float
)
