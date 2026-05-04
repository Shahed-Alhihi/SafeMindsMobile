package com.example.safemindsmobile.navigation

sealed class AppScreens (val flow:String){
    data object EntryScreen: AppScreens("entryScreen") //logo screen
    data object AwarenessScreen: AppScreens("awarenessScreen") //on boarding screen1
    data object InsightsScreen: AppScreens("insightsScreen") //on boarding screen2
    data object SupportScreen: AppScreens("supportScreen") //on boarding screen3
    data object SignUpScreen: AppScreens("signUpScreen")
    data object LoginScreen: AppScreens("login")
    data object PairWithWatch: AppScreens("pairWithWatch")
    data object Main: AppScreens("main")


    //nav screen
    data object DashboardScreen: AppScreens("dashboard")
    data object SleepPatternsScreen: AppScreens("sleep")
    data object VitalsAnalysisScreen: AppScreens("vitals")
    data object RiskAnalysisScreen: AppScreens("risk")

}