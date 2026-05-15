package com.example.safemindsmobile.data.model

data class SessionData(
    val sessionId: String,
val userId: String?,
val sessionStart: Long,
val sessionEnd: Long,
val sessionType: String,
val summary: SessionSummary,
val epochs: List<Any>,
val epochCount: Int
)
