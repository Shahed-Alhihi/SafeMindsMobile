package com.example.safemindsmobile.data.receiver

import android.util.Base64
import android.util.Log
import com.example.safemindsmobile.data.local.UserSessionManager
import com.example.safemindsmobile.data.local.database.AppDatabase
import com.example.safemindsmobile.data.local.entity.HourlyCheckEntity
import com.example.safemindsmobile.data.local.entity.SleepSummaryEntity
import com.example.safemindsmobile.data.local.entity.SyncStateEntity
import com.example.safemindsmobile.data.model.SessionData
import com.example.safemindsmobile.data.remote.mapper.SessionRequestMapper
import com.example.safemindsmobile.data.remote.model.SessionRequest
import com.example.safemindsmobile.data.repository.SafeMindsRep
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import com.example.safemindsmobile.data.sync.BackendSyncScheduler
import com.example.safemindsmobile.utils.NotificationHelper

class WearMessageService : WearableListenerService() {

    private val repository= SafeMindsRep()
    private val gson = Gson()
    private val mapper = SessionRequestMapper()

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)

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

        val userId = UserSessionManager(applicationContext).getUserId()

        if (userId == null) {
            Log.e("WearReceiver", "Cannot sync session: no logged-in user ID found")
            return
        }

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

        val request = mapper.map(session, userId)
        val payloadJson = gson.toJson(request)

        dao.insertSyncState(
            SyncStateEntity(
                dataID = session.sessionId,
                synced = false,
                payloadJson = payloadJson
            )
        )

        sendToBackend(request)
    }

    private suspend fun sendToBackend(request: SessionRequest) {
        val db = AppDatabase.getInstance(applicationContext)
        val dao = db.sessionDao()

        try {
            Log.d("SafeMindsMobile", "Sending to backend JSON: ${gson.toJson(request)}")

            val response = repository.ingestData(request)

            Log.d("SafeMindsMobile", "Backend ingest response JSON: ${gson.toJson(response)}")

            if (response.success) {
                Log.d("SafeMindsMobile", "Session sent successfully to backend")

                val riskLevel = response.csi?.risk_level?.uppercase()
                val csiScore = response.csi?.csi_score ?: 0

                if (riskLevel == "HIGH") {
                    NotificationHelper.showHighRiskNotification(
                        context = applicationContext,
                        title = "High risk alert!",
                        score = csiScore
                    )
                }

                dao.markSynced(request.dataID)
            } else {
                Log.e("SafeMindsMobile", "Backend rejected session: ${response.message}")
                dao.markFailed(request.dataID, response.message)
                BackendSyncScheduler.schedule(applicationContext)
            }

        } catch (e: Exception) {
            Log.e("SafeMindsMobile", "Failed to send session to backend", e)
            dao.markFailed(request.dataID, e.message)
            BackendSyncScheduler.schedule(applicationContext)
        }
    }
    }