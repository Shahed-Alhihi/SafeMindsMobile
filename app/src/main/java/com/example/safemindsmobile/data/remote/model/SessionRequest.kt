package com.example.safemindsmobile.data.remote.model

data class SessionRequest(
    val dataID: String,
    val timeStamp: Long,
    val sessionType: String,
    val userID: String,
    val hrMean: Float?,
    val hrMin: Float?,
    val hrMax: Float?,
    val movementMean: Float,
    val movementVariance: Float,
    val totalEpochs: Int

)
