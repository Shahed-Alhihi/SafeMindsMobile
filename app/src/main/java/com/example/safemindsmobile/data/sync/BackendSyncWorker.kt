package com.example.safemindsmobile.data.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.safemindsmobile.data.local.database.AppDatabase
import com.example.safemindsmobile.data.remote.model.SessionRequest
import com.example.safemindsmobile.data.repository.SafeMindsRep
import com.google.gson.Gson

class BackendSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val repository = SafeMindsRep()
    private val gson = Gson()

    override suspend fun doWork(): Result {
        val db = AppDatabase.getInstance(applicationContext)
        val dao = db.sessionDao()

        val unsyncedSessions = dao.getUnsyncedSessions()

        if (unsyncedSessions.isEmpty()) {
            Log.d("BackendSyncWorker", "No unsynced sessions to retry")
            return Result.success()
        }

        Log.d("BackendSyncWorker", "Retrying ${unsyncedSessions.size} unsynced sessions")

        var hasFailure = false

        for (state in unsyncedSessions) {
            try {
                if (state.payloadJson.isBlank()) {
                    dao.markFailed(state.dataID, "Missing payloadJson")
                    hasFailure = true
                    continue
                }

                val request = gson.fromJson(
                    state.payloadJson,
                    SessionRequest::class.java
                )

                Log.d("BackendSyncWorker", "Retrying session ${state.dataID}")

                val response = repository.ingestData(request)

                if (response.success) {
                    dao.markSynced(state.dataID)
                    Log.d("BackendSyncWorker", "Session synced successfully: ${state.dataID}")
                } else {
                    dao.markFailed(state.dataID, response.message)
                    hasFailure = true
                    Log.e("BackendSyncWorker", "Backend rejected session ${state.dataID}: ${response.message}")
                }

            } catch (e: Exception) {
                dao.markFailed(state.dataID, e.message)
                hasFailure = true
                Log.e("BackendSyncWorker", "Retry failed for ${state.dataID}", e)
            }
        }

        return if (hasFailure) {
            Result.retry()
        } else {
            Result.success()
        }
    }
}