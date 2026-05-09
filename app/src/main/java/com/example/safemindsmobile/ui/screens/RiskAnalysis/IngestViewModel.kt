package com.example.safemindsmobile.ui.screens.RiskAnalysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safemindsmobile.data.remote.SensorDataRequest
import com.example.safemindsmobile.data.repository.SafeMindsRep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class IngestUIState{
    object Idle: IngestUIState()
    object Loading: IngestUIState()
    object Success: IngestUIState()
    data class Error(val message: String): IngestUIState()

}
class IngestViewModel: ViewModel() {
    private val repository = SafeMindsRep()
    private val _state=MutableStateFlow<IngestUIState>(IngestUIState.Idle)
    val state: StateFlow<IngestUIState> =_state

    suspend fun sendSensorData(request: SensorDataRequest){
    viewModelScope.launch {
        _state.value=IngestUIState.Loading
    }
            try {
                val response=repository.ingestData(request)
                if (response.success ){
                    _state.value=IngestUIState.Success

                }
                else{
                    _state.value=IngestUIState.Error(response.message)

                }
            }
            catch (e:Exception){
                _state.value=IngestUIState.Error("Could not send data to server")
            }
        }


    }