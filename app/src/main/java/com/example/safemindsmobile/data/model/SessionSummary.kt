package com.example.safemindsmobile.data.model

data class SessionSummary(
    val hrMean: Float?,
    val hrMin: Float?,
    val hrMax: Float?,
    val movementMean: Float,
    val movementVariance: Float,
    val totalEpochs: Int
)