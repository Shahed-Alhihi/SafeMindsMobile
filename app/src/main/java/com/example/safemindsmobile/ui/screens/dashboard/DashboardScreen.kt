package com.example.safemindsmobile.ui.screens.dashboard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.safemindsmobile.data.model.RecommendationType
import com.example.safemindsmobile.data.model.DashboardData
import com.example.safemindsmobile.ui.components.screensComponents.SectionHeader
import com.example.safemindsmobile.ui.components.dashboardComponents.dashboardActivityChart
import com.example.safemindsmobile.ui.components.dashboardComponents.dashboardHeader
import com.example.safemindsmobile.ui.components.dashboardComponents.dashboardHeartRateChart
import com.example.safemindsmobile.ui.components.dashboardComponents.dashboardRecommendationCard
import com.example.safemindsmobile.ui.components.dashboardComponents.dashboardScoreCard
import com.example.safemindsmobile.ui.components.dashboardComponents.dashboardSleepChart
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.warningColor


@Composable
fun DashboardScreen (
    data: DashboardData,
    onLogout:()->Unit,
    onSleepAnalysis:()->Unit,
    onVitalsAnalysis:()->Unit,
){

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spaces.spaceL)
            .padding(bottom = Spaces.spaceL),
        verticalArrangement = Arrangement.spacedBy(Spaces.spaceL)
    ) {
        dashboardHeader(onLogout
        )
        dashboardScoreCard(
            riskScore = data.riskScore,
            label = data.riskLabel,
            status = data.riskLevel,
            desc = data.riskDesc
        )

        SectionHeader(label = "Sleep quality", action = "See all", click = onSleepAnalysis)
        dashboardSleepChart(
            data=data.sleepSummary)

        SectionHeader(label = "Activity", action = "See all", click = onVitalsAnalysis)
        dashboardActivityChart(
            data=data.activitySummary)

        SectionHeader(label = "Heart rate", action = "See all", click = onVitalsAnalysis)
        dashboardHeartRateChart(
            data=data.heartRateSummary)

        SectionHeader(label = "Recommendations")
        data.recommendation.forEach { item ->
            dashboardRecommendationCard(
                color=recommendationColor(item.type),
                title = item.title,
                body = item.description
            )
        }


    }
}


fun recommendationColor(type: RecommendationType): Color {
    return when (type) {
        RecommendationType.URGENT_ALERT -> highRiskColor
        RecommendationType.INFO -> primaryColor
        RecommendationType.WARNING -> warningColor
        RecommendationType.GOOD -> successColor
    }
}