package com.example.safemindsmobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.example.safemindsmobile.ui.Screens.onBoarding.AwarenessScreen
import com.example.safemindsmobile.ui.Screens.onBoarding.EntryScreen
import com.example.safemindsmobile.ui.Screens.onBoarding.InsightsScreen
import com.example.safemindsmobile.ui.Screens.onBoarding.SupportScreen
import com.example.safemindsmobile.ui.Screens.MainScreen
import com.example.safemindsmobile.ui.Screens.pairing.PairWithWatch
import com.example.safemindsmobile.ui.Screens.userCredentials.LoginScreen
import com.example.safemindsmobile.ui.Screens.userCredentials.SignUpScreen

//App navigation (outer nav controller)
@Composable
fun AppNavFlow (
    controller: NavHostController,
    isInitialLaunch: Boolean=true
){

    NavHost(
        navController=controller,
        startDestination= AppScreens.EntryScreen.flow
    ){
        composable(AppScreens.EntryScreen.flow){
            EntryScreen(
                navController=controller,
                isFirstLaunch=isInitialLaunch
            )
        }
        composable(AppScreens.AwarenessScreen.flow){
            AwarenessScreen(controller)
        }
        composable(AppScreens.InsightsScreen.flow){
            InsightsScreen(controller)
        }
        composable(AppScreens.SupportScreen.flow){
            SupportScreen(controller)
        }
        composable(AppScreens.SignUpScreen.flow){
            SignUpScreen(controller)
        }
        composable(AppScreens.PairWithWatch.flow){
            PairWithWatch(controller)
        }
        composable(AppScreens.LoginScreen.flow){
            LoginScreen(controller)
        }
        composable(AppScreens.Main.flow){
            MainScreen()
        }




    }

}

//navHost used to show the current screen based on the route, connect controller, routes
//navHostController manage app navigation (perform route changes)
//controller used to navigated between screens