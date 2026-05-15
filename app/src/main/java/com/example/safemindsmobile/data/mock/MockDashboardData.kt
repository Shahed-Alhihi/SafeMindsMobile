package com.example.safemindsmobile.data.mock

import com.example.safemindsmobile.data.model.RecommendationType
import com.example.safemindsmobile.data.model.RiskLevel
import com.example.safemindsmobile.data.model.DashboardActivitySummary
import com.example.safemindsmobile.data.model.DashboardData
import com.example.safemindsmobile.data.model.DashboardHeartRateSummary
import com.example.safemindsmobile.data.model.DashboardRecommendation
import com.example.safemindsmobile.data.model.DashboardSleepSummary

val MockDashboardData = DashboardData(
    riskScore = 72,
    riskLabel = "Medium Risk",
    riskDesc = "Vitals stable — minor sleep irregularities detected this week.",
    riskLevel= RiskLevel.MEDIUM,
    sleepSummary = DashboardSleepSummary(
        avgSleep = "6.8h",
        subtitle = "Average sleep duration",
        days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"),
        values = listOf(0.52f, 0.63f, 0.42f, 0.78f, 0.58f, 0.72f, 0.88f)
    ),

    activitySummary = DashboardActivitySummary(
      activityLevel = 0.65f,
        activityChart = listOf(0.85f, 0.78f, 0.45f, 0.55f, 0.32f),
        progress=0.65f
    ),

    heartRateSummary = DashboardHeartRateSummary(
        averageHr = 63f,
        heartRateChart = listOf(60f, 62f, 61f, 65f, 63f)
    ),

    recommendation = listOf(
        DashboardRecommendation(
            type = RecommendationType.GOOD,
            title = "Improve sleep consistency",
            description = "Your last 3 nights varied by 90 min, try a fixed bedtime."
        ),
        DashboardRecommendation(
            type = RecommendationType.INFO,
            title = "2,760 steps to your goal",
            description = "A short evening walk would close today's activity gap."
        ),
        DashboardRecommendation(
            type = RecommendationType.WARNING,
            title = "Heart rate spike noticed",
            description = "A short rest period may help reduce stress indicators."
        )
    )
)