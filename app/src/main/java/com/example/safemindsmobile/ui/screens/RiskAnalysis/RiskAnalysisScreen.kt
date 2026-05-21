package com.example.safemindsmobile.ui.screens.RiskAnalysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.screensComponents.SectionHeader
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.data.model.RiskData
import com.example.safemindsmobile.ui.components.riskComponents.BreakdownCard
import com.example.safemindsmobile.ui.components.riskComponents.RiskHeader
import com.example.safemindsmobile.ui.components.riskComponents.RiskReasonsCard
import com.example.safemindsmobile.ui.components.riskComponents.RiskRecommendation
import com.example.safemindsmobile.ui.components.riskComponents.RiskScoreCard
import androidx.compose.ui.platform.LocalContext
import com.example.safemindsmobile.data.model.RiskLevel
import com.example.safemindsmobile.ui.states.UIStates
import com.example.safemindsmobile.ui.viewModel.MainView
import com.example.safemindsmobile.utils.NotificationHelper

@Composable
fun RiskAnalysisScreen (
    viewModel: MainView= viewModel(),
   // data: RiskData,
    navController: NavController
){
    val state=viewModel.riskState
    val context=LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.riskLoading()
    }

    when(val currentState=state) {
        is UIStates.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()

            }
        }

        is UIStates.Error -> {
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(Spaces.spaceL),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentState.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }

        is UIStates.Success -> {
            val data = currentState.data
            HighRiskNotificationEffect(
                data = data,
                context = context
            )

            RiskContent(
                data = data,
                navController = navController
            )
        }

        UIStates.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text="No data available",
                    style=MaterialTheme.typography.bodyMedium
                )
            }
        }
    }}

@Composable
private fun HighRiskNotificationEffect(
    data: RiskData,
    context:android.content.Context
){
    LaunchedEffect(data.riskScore,data.riskLevel) {
        if(data.riskLevel==RiskLevel.HIGH){
            NotificationHelper.showHighRiskNotification(
                context=context,
                title="High risk alert!",
                score=data.riskScore
            )
}}}

@Composable
private fun RiskContent(
    data: RiskData,
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spaces.spaceL)
            .padding(bottom = Spaces.spaceL),
        verticalArrangement = Arrangement.spacedBy(Spaces.spaceL)
    ) {
        RiskHeader(
            onLogout = {
                navController.navigate(AppScreens.LoginScreen.flow) {
                    popUpTo(AppScreens.Main.flow) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )

        RiskScoreCard(
            data = data
        )

        SectionHeader(label = "Score breakdown", action = "", click = {})

        BreakdownCard(text = data.scoreBreakdown)

        SectionHeader(label = "Understanding your CSI results", action = "", click = {})

        RiskReasonsCard(data = data)

        SectionHeader(label = "Recommendations", action = "", click = {})

        data.recommendations.forEach { recommendations ->
            RiskRecommendation(recommendations)
        }

    }
}


/*
LOW → no notification
MEDIUM → no notification or optional gentle reminder
HIGH → notification

This notification only appears when the app loads the Risk page and receives HIGH risk.
 */