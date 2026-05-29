package com.example.safemindsmobile.data.remote.model

data class SessionRequest(
    val userID: String,
    val dataID: String,
    val timeStamp: Long,
    val sessionType: String,
    val sessionStart: String,
    val sessionEnd: String,
    val hrMean: Float,
    val hrMin: Float,
    val hrMax: Float,
    val movementMean: Float,
    val movementVariance: Float,
    val totalEpochs: Int,
    val age: Int,
    val gender: String,
    val bmi: Float,
    val insomniaScore: Int,
    val sleepinessScore: Int,
    val chronotypeScore: Int
)