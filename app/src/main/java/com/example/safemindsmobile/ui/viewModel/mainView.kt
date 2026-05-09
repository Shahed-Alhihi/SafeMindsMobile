package com.example.safemindsmobile.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.safemindsmobile.data.model.RiskData
import com.example.safemindsmobile.data.model.SleepData
import com.example.safemindsmobile.data.model.VitalsData
import com.example.safemindsmobile.data.model.dashboardData
import com.example.safemindsmobile.data.repository.SafeMindsRep
import com.example.safemindsmobile.ui.states.UIStates

class mainView: ViewModel() {
    private val rep= SafeMindsRep()

    var dashboardState by mutableStateOf<UIStates<dashboardData>>(UIStates.Loading)
        private set

    var sleepState by mutableStateOf<UIStates< SleepData>>(UIStates.Loading)
        private set

    var vitalsState by mutableStateOf<UIStates<VitalsData>>(UIStates.Loading)
        private set

//    var riskState by mutableStateOf<UIStates<RiskData>>(UIStates.Loading)
//        private set


    fun dashboardLoading(){
        dashboardState=UIStates.Loading
        dashboardState=UIStates.Success(rep.getDashboardData())

    }

    fun sleepLoading(){
        sleepState=UIStates.Loading
        sleepState=UIStates.Success(rep.getSleepData())

    }

    fun vitalsLoading() {
        vitalsState = UIStates.Loading
        vitalsState = UIStates.Success(rep.getVitalsData())
    }

//    fun riskLoading() {
//        riskState = UIStates.Loading
//        riskState = UIStates.Success(rep.getRiskData())
//
//    }


}