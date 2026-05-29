package com.example.safemindsmobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_state")
data class SyncStateEntity(
    @PrimaryKey val dataID: String,
    val synced: Boolean,
    val payloadJson: String,
    val lastError: String? = null,
    val updatedAt: Long = System.currentTimeMillis()
)