package com.example.safemindsmobile.data.remote.dto
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
    val baseline_comparison:Map<String,Any> =emptyMap(),
    val model_version:String
)

data class CsiHistoryResponse(
    val success: Boolean,
    val message: String?=null,
    val data:CsiHistoryData?
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



