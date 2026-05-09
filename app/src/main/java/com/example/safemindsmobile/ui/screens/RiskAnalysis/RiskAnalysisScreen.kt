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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.SectionHeader
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.data.model.RiskData
import com.example.safemindsmobile.data.session.UserSession
import com.example.safemindsmobile.ui.components.riskComponents.BreakdownCard
import com.example.safemindsmobile.ui.components.riskComponents.RiskHeader
import com.example.safemindsmobile.ui.components.riskComponents.RiskReasonsCard
import com.example.safemindsmobile.ui.components.riskComponents.RiskRecommendation
import com.example.safemindsmobile.ui.components.riskComponents.RiskScoreCard


@Composable
fun RiskAnalysisScreen (
    vm: RiskViewModel= viewModel(),
   // data: RiskData,
    navController: NavController
){
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadRisk(UserSession.userId)
    }

    when(val currentState=state) {
        is RiskUIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()

            }
        }

        is RiskUIState.Error -> {
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

        is RiskUIState.Success -> {
            RiskContent(
                data = currentState.data,
                navController = navController
            )
        }


    }}



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


