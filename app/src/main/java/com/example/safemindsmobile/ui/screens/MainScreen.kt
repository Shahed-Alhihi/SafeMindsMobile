package com.example.safemindsmobile.ui.screens
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.screens.sleepAnalysis.SleepAnalysisScreen
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.safemindsmobile.ui.components.screensComponents.BottomNav
import com.example.safemindsmobile.ui.screens.RiskAnalysis.RiskAnalysisScreen
import com.example.safemindsmobile.ui.screens.dashboard.DashboardScreen
import com.example.safemindsmobile.ui.screens.vitals.VitalsAnalysisScreen
import com.example.safemindsmobile.ui.viewModel.MainView
import com.example.safemindsmobile.ui.components.systemStatus.stateHandler

//main screen navigation
@Composable
fun MainScreen (appNavController: NavController
) {
    val controller =
        rememberNavController() // create nav controller once and save the value even after closing it

    val mainView: MainView = viewModel()

    val screens = listOf(
        //list of tabs that are needed in the navbar
        AppScreens.DashboardScreen,
        AppScreens.SleepPatternsScreen,
        AppScreens.VitalsAnalysisScreen,
        AppScreens.RiskAnalysisScreen,
    )

    Scaffold(
        bottomBar = {
            BottomNav(
                    controller=controller,
                    appScreens=screens
                    )
        }
    )
    { screenPadding -> //used to isolate the content of the page and the bottom bar
        NavHost( //container for the current screen
            modifier = Modifier.padding(screenPadding),
            navController = controller,
            startDestination = AppScreens.DashboardScreen.flow
        ) {
            composable(AppScreens.DashboardScreen.flow) {
                LaunchedEffect(Unit) {
                    mainView.dashboardLoading()
                }
                stateHandler(
                    state = mainView.dashboardState,
                    retry = { mainView.dashboardLoading() }
                ) { dashboardData ->

                    DashboardScreen(
                        data = dashboardData,
                        onLogout = {
                            appNavController.navigate(AppScreens.LoginScreen.flow) {
                                popUpTo(AppScreens.Main.flow) {
                                    inclusive = true
                                }
                                launchSingleTop = true

                            }
                        },
                        onSleepAnalysis = {
                            controller.navigate(AppScreens.SleepPatternsScreen.flow){
                                popUpTo(controller.graph.startDestinationId){
                                    saveState=true
                                }
                                launchSingleTop=true
                                restoreState=true
                            }
                        },
                        onVitalsAnalysis = {
                            controller.navigate(AppScreens.VitalsAnalysisScreen.flow){
                                popUpTo(controller.graph.startDestinationId){
                                    saveState=true
                                }
                                launchSingleTop=true
                                restoreState=true
                            }
                        }
                    )
                }

            }



            composable(AppScreens.SleepPatternsScreen.flow) {
                LaunchedEffect(Unit) {
                    mainView.sleepLoading()
                }

                stateHandler(
                    state = mainView.sleepState,
                    retry = { mainView.sleepLoading() }
                ) {
                    SleepAnalysisScreen(
                        navController = controller
                    )
                }
            }



            composable(AppScreens.VitalsAnalysisScreen.flow) {
                LaunchedEffect(Unit) {
                    mainView.vitalsLoading()
                }

                stateHandler(
                    state = mainView.vitalsState,
                    retry = { mainView.vitalsLoading() }
                ) {
                    VitalsAnalysisScreen(
                        navController = controller
                    )
                }
            }

            composable(AppScreens.RiskAnalysisScreen.flow) {
                RiskAnalysisScreen(
                    navController = controller
                )
            }

        }}}


