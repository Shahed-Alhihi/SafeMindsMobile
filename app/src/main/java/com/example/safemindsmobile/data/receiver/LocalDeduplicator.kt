package com.example.safemindsmobile.data.receiver

import android.content.Context
//************************************
// should be deleted *****************
//************************************
class LocalDeduplicator(context: Context) {
    private val prefers = context.getSharedPreferences("dedup", Context.MODE_PRIVATE)

    fun isDuplicate(id: String): Boolean{
        return prefers.contains(id)
    }
    fun markAsSaved(id: String){
        prefers.edit().putBoolean(id, true).apply()
    }
}