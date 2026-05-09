package com.example.safemindsmobile.ui.screens.sleepAnalysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.safemindsmobile.data.model.SleepData
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.SectionHeader
import com.example.safemindsmobile.ui.components.sleepComponents.SleepPatternCard
import com.example.safemindsmobile.ui.components.sleepComponents.SleepRecommendation
import com.example.safemindsmobile.ui.components.sleepComponents.SleepScoreCard
import com.example.safemindsmobile.ui.components.sleepComponents.sleepHeader
import com.example.safemindsmobile.ui.theme.Spaces

@Composable
fun SleepAnalysisScreen (
    data: SleepData
    ,
    navController: NavController
){
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





