package com.example.safemindsmobile.data.remote.mapper

import com.example.safemindsmobile.data.model.SessionData
import com.example.safemindsmobile.data.remote.model.SessionRequest

class SessionRequestMapper {
    fun map(session: SessionData): SessionRequest{
        return SessionRequest(
            dataID = session.dataId,
            timeStamp = session.timestamp,
            sessionType = session.sessionType,
            userID = session.userId,
            hrMean = session.summary.hrMean,
            hrMin = session.summary.hrMin,
            hrMax = session.summary.hrMax,
            movementMean = session.summary.movementMean,
            movementVariance = session.summary.movementVariance,
            totalEpochs = session.summary.totalEpochs
        )

    }
}