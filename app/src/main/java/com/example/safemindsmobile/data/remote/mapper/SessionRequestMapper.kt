package com.example.safemindsmobile.data.remote.mapper

import com.example.safemindsmobile.data.model.SessionData
import com.example.safemindsmobile.data.remote.model.SessionRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class SessionRequestMapper {

    fun map(session: SessionData, currentUserId: String): SessionRequest {
        return SessionRequest(
            userID = currentUserId,
            dataID = session.sessionId,
            timeStamp = session.sessionEnd,
            sessionType = session.sessionType,
            sessionStart = millisToIso(session.sessionStart),
            sessionEnd = millisToIso(session.sessionEnd),
            hrMean = session.summary.hrMean ?: 0f,
            hrMin = session.summary.hrMin ?: 0f,
            hrMax = session.summary.hrMax ?: 0f,
            movementMean = session.summary.movementMean,
            movementVariance = session.summary.movementVariance,
            totalEpochs = session.summary.totalEpochs,

            age = 0,
            gender = "unknown",
            bmi = 0f,
            insomniaScore = 0,
            sleepinessScore = 0,
            chronotypeScore = 0
        )
    }

    private fun millisToIso(value: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date(value))
    }
}