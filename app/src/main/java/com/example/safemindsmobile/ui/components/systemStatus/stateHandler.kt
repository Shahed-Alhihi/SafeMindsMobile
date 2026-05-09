package com.example.safemindsmobile.ui.components.systemStatus

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.safemindsmobile.ui.states.UIStates


@Composable
fun <T> stateHandler (
    state: UIStates<T>,
    retry:()->Unit,
    content:@Composable (T)->Unit
){
    when(state){
        UIStates.Loading->{
            LoadingState()
        }
        is UIStates.Success->{
            content(state.data)
        }
        is UIStates.Error->{
            ErrorState(info = state.message, retry = retry)
        }

        UIStates.Empty->{
            Text("Currently no data available!")
        }

    }

}