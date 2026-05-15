package com.example.safemindsmobile.data.remote.mapper

import com.example.safemindsmobile.data.model.SessionData
import com.example.safemindsmobile.data.remote.model.SessionRequest

class SessionRequestMapper {

    fun map(session: SessionData): SessionRequest {
        val userId = session.userId ?: "unknown_user"

        return SessionRequest(
            dataID = session.sessionId,
            timeStamp = session.sessionEnd,
            sessionType = session.sessionType,
            userID = userId,
            sessionStart = session.sessionStart,
            sessionEnd = session.sessionEnd,
            hrMean = session.summary.hrMean,
            hrMin = session.summary.hrMin,
            hrMax = session.summary.hrMax,
            movementMean = session.summary.movementMean,
            movementVariance = session.summary.movementVariance,
            totalEpochs = session.summary.totalEpochs
        )
    }
}


