package com.example.safemindsmobile.data.model

import com.google.gson.annotations.SerializedName


data class DashboardData (
    val riskScore:Int,
    val riskLabel: String,
    val riskDesc: String,
    val riskLevel: RiskLevel,

    val sleepSummary: DashboardSleepSummary,
    val activitySummary: DashboardActivitySummary,
    val heartRateSummary: DashboardHeartRateSummary,

    val recommendation:List<DashboardRecommendation>
)

data class DashboardRecommendation(
    val type: RecommendationType,
    val title: String,
    val description: String
)


data class DashboardSleepSummary(
    val avgSleep: String,
    val subtitle: String,
    val days: List<String>,
    val values: List<Float>
)

data class DashboardActivitySummary(
    @SerializedName("activity_level")
    val activityLevel: Float,

    @SerializedName("activity_chart")
    val activityChart: List<Float> = emptyList(),

    //val steps: String,
    //val subtitle: String,
    val progress: Float= 0f,
)

data class DashboardHeartRateSummary(
    @SerializedName("average_hr")
    val averageHr: Float,

    @SerializedName("heart_rate_chart")
    val heartRateChart: List<Float> = emptyList()

    //val heartRate: String,
    //val subtitle: String,
    //val values: List<Float>
)


