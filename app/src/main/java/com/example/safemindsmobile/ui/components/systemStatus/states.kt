package com.example.safemindsmobile.ui.components.systemStatus

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.safemindsmobile.ui.components.screensComponents.SafeMindsPrimaryButtons
import com.example.safemindsmobile.ui.theme.Spaces


@Composable
fun LoadingState (
    info: String="Preparing Your insights..",
    modifier: Modifier =Modifier
){
    Column(
        modifier=modifier.fillMaxWidth()
            .padding(Spaces.spaceXL),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color= MaterialTheme.colorScheme.primary
        )
        Spacer(modifier= Modifier.height(Spaces.spaceM))

        Text(
            text=info,
            style= MaterialTheme.typography.bodyMedium,
            color= MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}



@Composable
fun ErrorState (
    retry:()-> Unit,
    label: String="We couldn't load your information right now",
    info: String="Please try again...",
  //  modifier: Modifier =Modifier,
    //button:String="Retry"
){
    Column(
      //  modifier=modifier.fillMaxWidth()
        //    .padding(Spaces.spaceXL),

      //  verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier.padding(Spaces.spaceXL)
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
          //  modifier = Modifier.size(32.dp)
        )

        Spacer(modifier= Modifier.height(Spaces.spaceM))

        Text(
            text=label,
            style= MaterialTheme.typography.titleMedium,
         //   color= MaterialTheme.colorScheme.onSurface
        )
       // Spacer(modifier= Modifier.height(Spaces.spaceS))

        Text(
            text=info,
            style= MaterialTheme.typography.bodyMedium,
         //   color= MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier= Modifier.height(Spaces.spaceL))

        SafeMindsPrimaryButtons(
            label = "Retry",
            onClick = retry
        )
    }
}


