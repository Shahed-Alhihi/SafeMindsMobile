package com.example.safemindsmobile.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safemindsmobile.data.mapper.toRiskData
import com.example.safemindsmobile.data.model.SleepData
import com.example.safemindsmobile.data.model.VitalsData
import com.example.safemindsmobile.data.model.DashboardData
import com.example.safemindsmobile.data.model.RiskData
import com.example.safemindsmobile.data.remote.dto.CsiHistoryItem
import com.example.safemindsmobile.data.remote.model.SessionRequest
import com.example.safemindsmobile.data.repository.SafeMindsRep
import com.example.safemindsmobile.data.session.UserSession
import com.example.safemindsmobile.ui.states.UIStates
import kotlinx.coroutines.launch

class MainView: ViewModel() {
    private val rep= SafeMindsRep()



    var dashboardState by mutableStateOf<UIStates<DashboardData>>(UIStates.Loading)
        private set

    var sleepState by mutableStateOf<UIStates< SleepData>>(UIStates.Loading)
        private set

    var vitalsState by mutableStateOf<UIStates<VitalsData>>(UIStates.Loading)
        private set

    var riskState by mutableStateOf<UIStates<RiskData>>(UIStates.Loading)
        private set

    var csiHistoryState by mutableStateOf<UIStates<List<CsiHistoryItem>>>(UIStates.Loading)
        private set
    var ingestState by mutableStateOf<UIStates<Unit>>(UIStates.Success(Unit))
        private set


    fun dashboardLoading(){
        val userId= UserSession.userId
        if (userId.isNullOrBlank()){
            dashboardState=UIStates.Error("User ID is not found, please login again!")
            return
        }
        viewModelScope.launch {
            dashboardState=UIStates.Loading
            val data =rep.getDashboard(userId)
            dashboardState=
                if (data!=null){
                    UIStates.Success(data)
                }
                else{
                    UIStates.Error("No dashboard data found, Please sync your watch")
                }
        }

    }

    fun sleepLoading(){
        val userId= UserSession.userId
        if (userId.isNullOrBlank()){
            sleepState=UIStates.Error("User ID is not found, please login again!")
            return
        }
        viewModelScope.launch {
            sleepState=UIStates.Loading
            val data =rep.getSleep(userId)
            sleepState=
                if (data!=null){
                    UIStates.Success(data)
                }
                else{
                    UIStates.Error("No sleep data found, Please sync your watch")
                }
        }

    }

    fun vitalsLoading() {
        val userId= UserSession.userId
        if (userId.isNullOrBlank()){
            vitalsState=UIStates.Error("User ID is not found, please login again!")
            return
        }
        viewModelScope.launch {
            vitalsState=UIStates.Loading
            val data =rep.getVitals(userId)
            vitalsState=
                if (data!=null){
                    UIStates.Success(data)
                }
                else{
                    UIStates.Error("No vitals data found, Please sync your watch")
                }
        }

    }



    fun riskLoading(
    ) {
        val userId= UserSession.userId
        if (userId.isNullOrBlank()){
            riskState=UIStates.Error("User ID is not found, please login again!")
            return
        }

        viewModelScope.launch {
            riskState=UIStates.Loading

            try {
                val response=rep.getLatestCsi(userId)

                riskState=
                    if (response.success && response.data!=null){
                        UIStates.Success(response.data.toRiskData())
                    }
                    else{
                        UIStates.Error(response.message?:"No CSI data found")
                    }

            }
            catch (e: Exception){
                riskState=UIStates.Error("Failed to load risk data")

            }
        }
    }








fun csiHistoryLoading(
) {
    val userId= UserSession.userId

    if (userId.isNullOrBlank()){
        csiHistoryState=UIStates.Error("User session is not found!")
        return
    }

    viewModelScope.launch {
        csiHistoryState=UIStates.Loading

        try {
            val response=rep.getCsiHistory(userId)

            csiHistoryState=
                if (response.success && response.data!=null){
                    UIStates.Success(response.data.history)
                }
                else{
                    UIStates.Error(response.message?:"No history data found")
                }

        }
        catch (e: Exception){
            csiHistoryState=UIStates.Error("Failed to load history data")

        }
    }
}



    fun sendSensorData(
        req: SessionRequest
    ) {
        viewModelScope.launch {
            ingestState=UIStates.Loading
            try {
                val response=rep.ingestData(req)

                ingestState=
                    if (response.success){
                        UIStates.Success(Unit)
                    }
                else{
                        UIStates.Error(response.message)

                    }

        }
            catch (e: Exception){
                ingestState=UIStates.Error("Could not send data to server")
            }

        }
    }
}