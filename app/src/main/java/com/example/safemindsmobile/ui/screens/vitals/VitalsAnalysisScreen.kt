package com.example.safemindsmobile.ui.screens.vitals

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
import com.example.safemindsmobile.data.model.VitalsData
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.screensComponents.SectionHeader
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.components.vitalsComponents.ECGCard
import com.example.safemindsmobile.ui.components.vitalsComponents.VitalHR
import com.example.safemindsmobile.ui.components.vitalsComponents.VitalHeader
import com.example.safemindsmobile.ui.components.vitalsComponents.VitalRecommendation
import com.example.safemindsmobile.ui.components.vitalsComponents.VitalZones
import com.example.safemindsmobile.ui.states.UIStates
import com.example.safemindsmobile.ui.viewModel.MainView


@Composable
fun VitalsAnalysisScreen (
    navController: NavController,
    mainViewModel: MainView = viewModel()
) {
    val state =mainViewModel.vitalsState
    LaunchedEffect(Unit) {
        mainViewModel.vitalsLoading()
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
            vitalsContent(
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
                    text="No vitals data available",
                    style= MaterialTheme.typography.bodyMedium
                )
            }
        }

    }

}




@Composable
private fun vitalsContent(
    data: VitalsData,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize().verticalScroll(rememberScrollState())
            .padding(horizontal = Spaces.spaceL)
            .padding(bottom = Spaces.spaceL),
        verticalArrangement = Arrangement.spacedBy(Spaces.spaceL)
    ) {
        VitalHeader(
            onLogout= {
                navController.navigate(AppScreens.LoginScreen.flow) {
                    popUpTo(AppScreens.Main.flow) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }

        )

        ECGCard(data=data)
        SectionHeader(
            label="Heart rate zones",
            action = "Today",
            click = {}
        )

        VitalZones(zone=data.HRZones)
        SectionHeader(
            label="Weekly HR trend",
            action = "",
            click = {}
        )

        VitalHR(
            days = data.weeklyHR,
            avgHr = data.averageHR,
            minHr = data.restingHR,
            maxHr = data.peakHR
        )
        SectionHeader(
            label="Recommendations",
            action = "",
            click = {}
        )

        data.recommendations.forEach { recommendation ->
            VitalRecommendation(recommendation)
        }



    }
}







