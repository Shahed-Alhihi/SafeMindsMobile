package com.example.safemindsmobile.data.validation

import com.example.safemindsmobile.data.model.SessionData

class MissingDataHandler {
    fun handle(session: SessionData): SessionData{
        val summary = session.summary
        return session.copy(
            summary = summary.copy(
                hrMean = summary.hrMean ?: -1f,
                hrMin = summary.hrMin ?: -1f,
                hrMax = summary.hrMax ?: -1f
            )
        )
    }
}