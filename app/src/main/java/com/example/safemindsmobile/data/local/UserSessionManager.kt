package com.example.safemindsmobile.data.local

import android.content.Context

class UserSessionManager(context: Context) {

    private val prefs = context.getSharedPreferences(
        "safeminds_user_session",
        Context.MODE_PRIVATE
    )

    fun saveUserId(userId: String) {
        prefs.edit()
            .putString("user_id", userId)
            .apply()
    }

    fun getUserId(): String? {
        return prefs.getString("user_id", null)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}