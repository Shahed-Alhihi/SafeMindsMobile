package com.example.safemindsmobile.ui.screens.sleepAnalysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.safemindsmobile.data.model.SleepData
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.screensComponents.SectionHeader
import com.example.safemindsmobile.ui.components.sleepComponents.SleepPatternCard
import com.example.safemindsmobile.ui.components.sleepComponents.SleepRecommendation
import com.example.safemindsmobile.ui.components.sleepComponents.SleepScoreCard
import com.example.safemindsmobile.ui.components.sleepComponents.sleepHeader
import com.example.safemindsmobile.ui.states.UIStates
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.viewModel.MainView

@Composable
fun SleepAnalysisScreen (
   // data: SleepData

    navController: NavController,
    mainViewModel: MainView = viewModel()
){
    val state=mainViewModel.sleepState

    LaunchedEffect(Unit) {
        mainViewModel.sleepLoading()
    }

    when(val currentState=state){
        is UIStates.Loading ->{
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }

        is UIStates.Error ->{
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(Spaces.spaceL),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text=currentState.message,
                    color= MaterialTheme.colorScheme.error,
                    style= MaterialTheme.typography.bodyMedium
                )
            }
        }

        is UIStates.Success ->{
            sleepContent(
                data=currentState.data,
                navController=navController
            )

        }

        UIStates.Empty ->{
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(Spaces.spaceL),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text="No sleep data available",
                    style= MaterialTheme.typography.bodyMedium
                )
            }
        }

    }

}




@Composable
private fun sleepContent(
    data: SleepData,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(horizontal = Spaces.spaceL)
            .padding(bottom = Spaces.spaceL),
        verticalArrangement = Arrangement.spacedBy(Spaces.spaceL)
    ) {
        sleepHeader (
            onLogout = {
                navController.navigate(AppScreens.LoginScreen.flow){
                    popUpTo(AppScreens.Main.flow){
                        inclusive=true
                    }
                    launchSingleTop=true
                }
            }
        )

        SleepScoreCard(
            sleepScore=data.sleepScore,
            sleepDuration=data.sleepDuration,
            sleepQuality=data.sleepQuality,
            sleepFragmentation=data.sleepFragmentation,
            sleepEfficiency=data.sleepEfficiency

        )

        SectionHeader(
            label = "Sleep patterns",
            action = "7 day overview",
            click = {}
        )

        SleepPatternCard(
            weekData = data.weekData
        )

        SectionHeader(
            label = "Sleep recommendations",
            action = "",
            click = {}
        )

        data.recommendations.forEach{
                recommendation ->
            SleepRecommendation(
                recommendation=recommendation
            )
        }

    }
}