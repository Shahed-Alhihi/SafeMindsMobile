package com.example.safemindsmobile.data.model

data class SessionData(
//    val dataId: String,
//    val summary: SessionSummary,
//    val timestamp: Long,
//    val sessionType: String,
//    val userId: String,
//    val epochs: List<String>

val sessionId: String,
val userId: String?,
val sessionStart: Long,
val sessionEnd: Long,
val sessionType: String,
val summary: SessionSummary,
//val epochs: List<EpochData>,
val epochs: List<Any>,
val epochCount: Int
)
