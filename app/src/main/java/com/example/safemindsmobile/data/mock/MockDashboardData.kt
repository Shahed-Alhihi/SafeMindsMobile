package com.example.safemindsmobile.data.mock

import com.example.safemindsmobile.data.model.RecommendationType
import com.example.safemindsmobile.data.model.RiskLevel
import com.example.safemindsmobile.data.model.dashboardActivitySummary
import com.example.safemindsmobile.data.model.dashboardData
import com.example.safemindsmobile.data.model.dashboardHeartRateSummary
import com.example.safemindsmobile.data.model.dashboardRecommendation
import com.example.safemindsmobile.data.model.dashboardSleepSummary

val MockDashboardData = dashboardData(
    riskScore = 72,
    riskLabel = "Medium Risk",
    riskDesc = "Vitals stable — minor sleep irregularities detected this week.",
    riskLevel= RiskLevel.MEDIUM,
    sleepSummary = dashboardSleepSummary(
        avgSleep = "6.8h",
        subtitle = "Average sleep duration",
        days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"),
        values = listOf(0.52f, 0.63f, 0.42f, 0.78f, 0.58f, 0.72f, 0.88f)
    ),

    activitySummary = dashboardActivitySummary(
        steps = "7,240",
        subtitle = "Steps today, 10K goal",
        progress = 0.724f,
        values = listOf(0.85f, 0.78f, 0.45f, 0.55f, 0.32f, 0.22f)
    ),

    heartRateSummary = dashboardHeartRateSummary(
        heartRate = "63 bpm",
        subtitle = "Resting heart rate",
        values = listOf(0.50f, 0.58f, 0.35f, 0.85f, 0.20f, 0.55f, 0.62f, 0.45f, 0.70f, 0.52f)
    ),

    recommendation = listOf(
        dashboardRecommendation(
            type = RecommendationType.GOOD,
            title = "Improve sleep consistency",
            description = "Your last 3 nights varied by 90 min, try a fixed bedtime."
        ),
        dashboardRecommendation(
            type = RecommendationType.INFO,
            title = "2,760 steps to your goal",
            description = "A short evening walk would close today's activity gap."
        ),
        dashboardRecommendation(
            type = RecommendationType.WARNING,
            title = "Heart rate spike noticed",
            description = "A short rest period may help reduce stress indicators."
        )
    )
)