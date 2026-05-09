package com.example.safemindsmobile.data.model

data class WatchSensorsData (
    val sessionId: String,
    val timestamp: String,
    val heartRate: Int,
    val steps: Int,
    val sleepHours: Float,
 //   val stressLevel: Int

)