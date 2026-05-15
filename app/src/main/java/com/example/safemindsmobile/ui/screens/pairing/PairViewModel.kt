package com.example.safemindsmobile.ui.screens.pairing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.safemindsmobile.data.wear.WearConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class PairUIState {
    object Idle: PairUIState()
    object Checking: PairUIState()
    object Connected: PairUIState()
    object notConnected: PairUIState()
    data class Error(val message: String): PairUIState()
}

class PairModel(
    application: Application
): AndroidViewModel(application
)
{

    private val manager= WearConnection(application)
    private val _state= MutableStateFlow<PairUIState>(PairUIState.Idle)
    val state: StateFlow<PairUIState> =_state

    fun checkConnection(){
        viewModelScope.launch {
            _state.value=PairUIState.Checking

            try {
                val connected = manager.isWatchConnected()

                _state.value = if (connected) {
                    PairUIState.Connected
                } else {
                    PairUIState.notConnected
                }
            }
                catch (e: Exception){
                    _state.value = PairUIState.Error("Could not check watch connection")
                }
        }

    }
}