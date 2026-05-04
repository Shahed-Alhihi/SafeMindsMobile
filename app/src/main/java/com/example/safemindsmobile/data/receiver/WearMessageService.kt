package com.example.safemindsmobile.data.receiver

import android.util.Base64
import android.util.Log
import com.example.safemindsmobile.data.local.database.AppDatabase
import com.example.safemindsmobile.data.local.entity.HourlyCheckEntity
import com.example.safemindsmobile.data.local.entity.SleepSummaryEntity
import com.example.safemindsmobile.data.local.entity.SyncStateEntity
import com.example.safemindsmobile.data.model.SessionData
import com.example.safemindsmobile.data.remote.mapper.SessionRequestMapper
import com.example.safemindsmobile.data.remote.model.SessionRequest
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class WearMessageService : WearableListenerService() {

    private val gson = Gson()
    private val mapper = SessionRequestMapper()

    override fun onMessageReceived(messageEvent: MessageEvent) {
        Log.d("WearReceiver", "Message received: ${messageEvent.path}")

        if (messageEvent.path != "/safeminds/session-transfer") {
            Log.d("WearReceiver", "Ignored path: ${messageEvent.path}")
            return
        }

        try {
            val envelopeJson = String(messageEvent.data, Charsets.UTF_8)
            Log.d("WearReceiver", "Envelope received")

            val envelopeObject = JSONObject(envelopeJson)
            val sessionBase64 = envelopeObject.getString("sessionBase64")

            val sessionBytes = Base64.decode(
                sessionBase64,
                Base64.DEFAULT
            )

            val sessionJson = String(sessionBytes, Charsets.UTF_8)
            Log.d("WearReceiver", "Session JSON decoded")

            val session = gson.fromJson(sessionJson, SessionData::class.java)

            CoroutineScope(Dispatchers.IO).launch {
                saveToDatabase(session)
            }

        } catch (e: Exception) {
            Log.e("WearReceiver", "Failed to parse incoming session", e)
        }
    }

    private suspend fun saveToDatabase(session: SessionData) {
        val db = AppDatabase.getInstance(applicationContext)
        val dao = db.sessionDao()

        val userId = session.userId ?: "unknown_user"

        if (session.sessionType == "NIGHT_SESSION") {
            val entity = SleepSummaryEntity(
                dataID = session.sessionId,
                timeStamp = session.sessionEnd,
                userID = userId,
                hrMean = session.summary.hrMean,
                hrMin = session.summary.hrMin,
                hrMax = session.summary.hrMax,
                movementMean = session.summary.movementMean,
                movementVariance = session.summary.movementVariance,
                totalEpochs = session.summary.totalEpochs
            )

            dao.insertSleep(entity)
            Log.d("WearReceiver", "Saved NIGHT_SESSION to DB")

        } else {
            val entity = HourlyCheckEntity(
                dataID = session.sessionId,
                timeStamp = session.sessionEnd,
                userID = userId,
                hrMean = session.summary.hrMean,
                movementMean = session.summary.movementMean
            )

            dao.insertHourly(entity)
            Log.d("WearReceiver", "Saved hourly session to DB")
        }

        dao.insertSyncState(
            SyncStateEntity(
                session.sessionId,
                false
            )
        )

        val request = mapper.map(session)
        sendToBackend(request)
    }

    private fun sendToBackend(request: SessionRequest) {
        Log.d("SafeMindsMobile", "Sending to backend: $request")
    }
}