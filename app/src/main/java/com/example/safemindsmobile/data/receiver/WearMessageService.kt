package com.example.safemindsmobile.data.receiver

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

class WearMessageService: WearableListenerService() {
    private val mapper = SessionRequestMapper()

    override fun onMessageReceived(messageEvent: MessageEvent) {

        Log.d("WearReceiver", "Message received: ${messageEvent.path}")

        if (messageEvent.path == "/safeminds/session-transfer") {
            val json = String(messageEvent.data)
            Log.d("WearReceiver", "Payload: $json")

            try {
                val session = Gson().fromJson(json, SessionData::class.java)

                CoroutineScope(Dispatchers.IO).launch {
                    saveToDatabase(session)
                }

            } catch (e: Exception) {
                Log.e("WearReceiver", "Parsing failed", e)
            }
        }
    }
    suspend fun saveToDatabase(session: SessionData) {
        val DB = AppDatabase.getInstance(applicationContext)
        val Dao = DB.sessionDao()

        if (session.sessionType == "NIGHT_SESSION"){
            val entity = SleepSummaryEntity(
                dataID = session.dataId,
                timeStamp = session.timestamp,
                userID = session.userId,
                hrMean = session.summary.hrMean,
                hrMin = session.summary.hrMin,
                hrMax = session.summary.hrMax,
                movementMean = session.summary.movementMean,
                movementVariance = session.summary.movementVariance,
                totalEpochs = session.summary.totalEpochs
            )
            Dao.insertSleep(entity)
        }
        else{
            val entity = HourlyCheckEntity(
                dataID = session.dataId,
                timeStamp = session.timestamp,
                userID = session.userId,
                hrMean = session.summary.hrMean,
                movementMean = session.summary.movementMean
            )
            Dao.insertHourly(entity)
        }
        Dao.insertSyncState(SyncStateEntity(session.dataId, false))
        val request = mapper.map(session)
        sendToBackend(request)
    }
    fun sendToBackend(request: SessionRequest){
        Log.d("SafeMindsMobile", "Sending to backend: $request")
    }

}