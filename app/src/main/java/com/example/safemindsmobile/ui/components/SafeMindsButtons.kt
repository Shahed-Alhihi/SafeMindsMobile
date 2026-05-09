package com.example.safemindsmobile.ui.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// it contains primary and secondary buttons in the app

@Composable
fun SafeMindsPrimaryButtons (
    label: String,
    onClick:()-> Unit,
    modifier: Modifier= Modifier,
    isEnabled: Boolean=true,
    isLoading: Boolean=false
){
    Button(
        onClick=onClick,
        enabled =isEnabled && !isLoading,
        modifier =modifier
            .fillMaxWidth()
            .height(52.dp),

        shape = MaterialTheme.shapes.medium){
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
            else{
                Text(
                    text=label,
                    style= MaterialTheme.typography.labelLarge
                )

            }
        }
    }
