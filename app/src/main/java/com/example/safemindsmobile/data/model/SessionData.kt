package com.example.safemindsmobile.data.model

data class SessionData(
    val dataId: String,
    val summary: SessionSummary,
    val timestamp: Long,
    val sessionType: String,
    val userId: String,
    val epochs: List<String>
)