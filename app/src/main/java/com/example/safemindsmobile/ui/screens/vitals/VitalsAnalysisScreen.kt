package com.example.safemindsmobile.ui.screens.vitals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.safemindsmobile.data.model.VitalsData
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.SectionHeader
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.components.vitalsComponents.ECGcard
import com.example.safemindsmobile.ui.components.vitalsComponents.VitalHR
import com.example.safemindsmobile.ui.components.vitalsComponents.VitalHeader
import com.example.safemindsmobile.ui.components.vitalsComponents.VitalRecommendation
import com.example.safemindsmobile.ui.components.vitalsComponents.VitalZones


@Composable
fun VitalsAnalysisScreen (
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

        ECGcard(data=data)
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
            days=data.weeklyHR)
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








