package com.example.safemindsmobile.data.receiver

import android.content.Context
import android.util.Log
import com.example.safemindsmobile.data.local.database.AppDatabase
import com.example.safemindsmobile.data.local.entity.HourlyCheckEntity
import com.example.safemindsmobile.data.local.entity.SleepSummaryEntity
import com.example.safemindsmobile.data.local.entity.SyncStateEntity
import com.example.safemindsmobile.data.model.SessionData
import com.example.safemindsmobile.data.remote.mapper.SessionRequestMapper
import com.example.safemindsmobile.data.remote.model.SessionRequest
import com.example.safemindsmobile.data.validation.MissingDataHandler
import com.example.safemindsmobile.data.validation.SessionValidator
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WearMessageManager(private val context: Context) {
    private val messageClient = Wearable.getMessageClient(context)
    private val missingHandler = MissingDataHandler()
    private val validator = SessionValidator()

    private val mapper = SessionRequestMapper()


    private val listener = MessageClient.OnMessageReceivedListener { messageEvent ->
        if (messageEvent.path == "/session_data") {
            val json = String(messageEvent.data)
            Log.d("SafeMindsMobile", "Received session: $json")
            handleIncomingData(json)
        }
    }
    private fun handleIncomingData(json: String) {

            // parse json + save locally + deduplication
            try {
                val gson = Gson()
                val session = gson.fromJson(json, SessionData::class.java)

                val fixedSession = missingHandler.handle(session)
                val isValid = validator.isValid(fixedSession)

                if (!isValid) {
                    Log.e("SafeMindsMobile", "Validation failed")
                    return
                }

                val DB = AppDatabase.getInstance(context)
                val Dao = DB.sessionDao()

                CoroutineScope(Dispatchers.IO).launch {
                    val exists = Dao.exists(session.dataId)
                    if (!exists){
                        saveToDatabase(session)
                        Log.d("SafeMindsMobile", "Saved to DB")
                    }
                    else{
                        Log.d("SafeMindsMobile", "Duplicate ignored (DB)")
                    }
                }
            } catch (e: Exception){
                Log.e("SafeMindsMobile", "Parsing failed", e)
            }
        }

    suspend fun saveToDatabase(session: SessionData) {
        val DB = AppDatabase.getInstance(context)
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

    fun start(){
        messageClient.addListener(listener)
        Log.d("SafeMindsMobile", "Wear listener started")
    }

    fun stop(){
        messageClient.removeListener(listener)
        Log.d("SafeMindsMobile", "Wear lister stopped")
    }
}