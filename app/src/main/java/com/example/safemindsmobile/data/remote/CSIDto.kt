package com.example.safemindsmobile.data.remote

data class SensorDataRequest(
    val user_id: String,
    val session_id: String,
    val timestamp: String,
    val heart_rate: Int,
    val steps: Int,
    val sleep_hours: Float,
    // val stress_level: Int,
    val session_type: String="daily_check"
)

data class IngestResponse(
    val success: Boolean,
    val message: String,
    val csi: CsiResultDto?
)

data class LatestCsiResponse(
    val success: Boolean,
    val message: String?=null,
    val data:CsiResultDto?
)

data class CsiResultDto(
    val user_id: String?=null,
    val session_id: String?=null,
    val timestamp: String?=null,
    val csi_score: Int,
    val risk_level: String,
    val drivers:List<String>,
    val recommendations:List<String>,
    val baseline_comparison:Map<String,String>,
    val model_version:String
)

data class CsiHistoryResponse(
    val success: Boolean,
    val message: String?=null,
    val data:List<CsiHistoryData>?
)

data class CsiHistoryData(
    val user_id: String,
    val history: List<CsiHistoryItem>
)


data class CsiHistoryItem(
    val timestamp: String,
    val csi_score: Int,
    val risk_level: String
)



