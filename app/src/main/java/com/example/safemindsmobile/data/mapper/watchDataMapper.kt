package com.example.safemindsmobile.data.mapper

import com.example.safemindsmobile.data.model.WatchSensorsData
import com.example.safemindsmobile.data.remote.SensorDataRequest

fun WatchSensorsData.toSensorDataRequest(userId: String): SensorDataRequest {
    return SensorDataRequest(
        user_id = userId,
        session_id = sessionId,
        timestamp = timestamp,
        heart_rate = heartRate,
        steps = steps,
        sleep_hours =sleepHours,
        session_type = "daily_check"
    )

}
