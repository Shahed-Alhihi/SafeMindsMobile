package com.example.safemindsmobile.data.model


data class dashboardData (
    val riskScore:Int,
    val riskLabel: String,
    val riskDesc: String,
    val riskLevel: RiskLevel,

    val sleepSummary: dashboardSleepSummary,
    val activitySummary: dashboardActivitySummary,
    val heartRateSummary: dashboardHeartRateSummary,

    val recommendation:List<dashboardRecommendation>
)

data class dashboardRecommendation(
    val type: RecommendationType,
    val title: String,
    val description: String
)


data class dashboardSleepSummary(
    val avgSleep: String,
    val subtitle: String,
    val days: List<String>,
    val values: List<Float>
)

data class dashboardActivitySummary(
    val steps: String,
    val subtitle: String,
    val progress: Float,
    val values:List<Float>
)

data class dashboardHeartRateSummary(
    val heartRate: String,
    val subtitle: String,
    val values: List<Float>
)


