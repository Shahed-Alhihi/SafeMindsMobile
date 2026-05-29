package com.example.safemindsmobile.data.wear

import android.content.Context
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.tasks.await

class WearConnection (
    private val context: Context
){
    companion object{
        //change it to false for real watch
        private const val TEST=false
    }
    suspend fun isWatchConnected(): Boolean{
        if (TEST){
            return true
        }

        val nodes= Wearable.getNodeClient(context).connectedNodes.await()
        return nodes.isNotEmpty()

    }

}