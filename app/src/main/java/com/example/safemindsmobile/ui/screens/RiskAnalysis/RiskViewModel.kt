package com.example.safemindsmobile.ui.screens.RiskAnalysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safemindsmobile.data.model.RiskData
import com.example.safemindsmobile.data.mapper.toRiskData
import com.example.safemindsmobile.data.repository.SafeMindsRep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class RiskUIState{
    object Loading: RiskUIState()
    data class Success(val data: RiskData): RiskUIState()
    data class Error(val message: String): RiskUIState()

}
class RiskViewModel : ViewModel(){
    private val repository = SafeMindsRep()

    private val _state=MutableStateFlow<RiskUIState>(RiskUIState.Loading)
    val state: StateFlow<RiskUIState> =_state

    fun loadRisk(userId: String?){
        if (userId==null){
            _state.value=RiskUIState.Error("User ID is not found, please login again!")
            return
        }
        viewModelScope.launch {
            _state.value= RiskUIState.Loading
            try {
                val response=repository.getLatestCsi(userId)
                if (response.success && response.data!=null){
                    _state.value=RiskUIState.Success(response.data.toRiskData())

                }
                else{
                    _state.value=RiskUIState.Error(response.message?:"No CSI data found")

                }
            }
            catch (e:Exception){
                _state.value=RiskUIState.Error("Failed to load risk data")
            }
        }


    }


}