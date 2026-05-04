package com.example.safemindsmobile.ui.states

sealed interface UIStates<out T>{
    data object Loading: UIStates<Nothing>
    data class Success<T>(val data: T): UIStates<T>
    data class Error(val message: String): UIStates<Nothing>
    data object Empty: UIStates<Nothing>
}



