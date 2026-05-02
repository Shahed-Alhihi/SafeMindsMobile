package com.example.safemindsmobile.data.remote.model

data class SessionResponse(
    val dataID: String,
    val csi: Float,
    val riskLevel: String,
    val drivers: List<String>,
    val recommendations: List<String>
)
