package com.example.safemindsmobile.ui.screens.pairing

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material.icons.outlined.Watch
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.buttons.SafeMindsPrimaryButtons
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.secondaryColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.warningColor


sealed interface PairWatchStates{
    data object InitialState: PairWatchStates
    data object Loading: PairWatchStates
    data object Success: PairWatchStates
    data class Failure(val messg: String): PairWatchStates

}
@Composable
fun PairWithWatch (navController: NavHostController,
                   userName: String="there",
                   onPairClicked: ()->Unit={},
                   onContinueClicked: ()->Unit={
                       navController.navigate(AppScreens.Main.flow){
                           popUpTo(AppScreens.PairWithWatch.flow){
                               inclusive=true
                           }
                       }
                   },


) {
    var states by remember { mutableStateOf<PairWatchStates>(PairWatchStates.InitialState) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        screenBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spaces.spaceXL)
                .padding(vertical = Spaces.spaceL)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(Spaces.spaceL))


            //header
            Text(
                text = "Pair with your watch",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground

            )
            Spacer(Modifier.height(Spaces.spaceS))

            //description
            Text(
                text = "Hi $userName, connect your smartwatch to securely sync sensor data with your phone",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(Spaces.spaceXL))

            screenVisuals(
                states = states,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)

            )

            Spacer(Modifier.height(Spaces.spaceL))

            //card status
            card(
                states = states,
            )

            Spacer(Modifier.weight(1f))

            //button

            AnimatedContent(
                targetState = states,
                label = "button"

            ) { state ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Spaces.spaceS)
                ) {
                    when (state) {
                        PairWatchStates.InitialState -> {
                            SafeMindsPrimaryButtons(
                                label = "Pair Watch",
                               // onClick = { states = pairWatchStates.loading; onPairClicked() }
                                onClick = {
                                    states = PairWatchStates.Success
                                } //just to test delete it later
                            )
                        }

                        PairWatchStates.Loading -> {
                            SafeMindsPrimaryButtons(
                                label = "Pairing in progress",
                                onClick = {},
                                isLoading = true
                            )
                        }

                        PairWatchStates.Success -> {
                            SafeMindsPrimaryButtons(
                                label = "Continue",
                                onClick = onContinueClicked
                            )
                        }

                        is PairWatchStates.Failure -> {
                            SafeMindsPrimaryButtons(
                                label = "Try again",
                                onClick = {
                                    states = PairWatchStates.Loading;
                                    onPairClicked()
                                }
                            )

                        }
                    }
                }
            }
            Spacer(Modifier.height(Spaces.spaceS))

        }
    }

}


@Composable
private fun screenVisuals(
    states: PairWatchStates, modifier: Modifier=Modifier
){
    val shape by rememberInfiniteTransition(label = "shape")
        .animateFloat(
            initialValue = 0.95f,
            targetValue = 1.08f,
            animationSpec = infiniteRepeatable(tween(
                1800, easing = FastOutSlowInEasing
            ), repeatMode = RepeatMode.Reverse),
            label = "Scale"
        )

    Box(
        modifier=modifier, contentAlignment = Alignment.Center
    )
    {
        //glow ring
        Box(
            Modifier.size(240.dp)
                .scale(shape)
                .alpha(0.15f)
                .background(primaryColor, CircleShape)
        )

        //watch card
        Box(
            Modifier.size(190.dp)
            .background(
               color= MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                shape= MaterialTheme.shapes.extraLarge
            )
                .border(1.dp, MaterialTheme.colorScheme.surface,
                    MaterialTheme.shapes.extraLarge),
            contentAlignment = Alignment.Center
            ){
            val (icon, tint) =when (states){
                PairWatchStates.InitialState ->Icons.Outlined.Watch to primaryColor
                PairWatchStates.Loading -> Icons.Outlined.Sync to primaryColor
                PairWatchStates.Success -> Icons.Outlined.CheckCircle to successColor
                is PairWatchStates.Failure -> Icons.Outlined.WarningAmber to highRiskColor

            }
            Icon(icon, contentDescription = null, modifier=Modifier.size(74.dp),
                tint=tint)
        }
        // watch linked
        AnimatedVisibility(
            visible = states== PairWatchStates.Success,
            enter = fadeIn()+ scaleIn(),
            exit = fadeOut()
        ) {
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(top=200.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(50))
                    .padding(horizontal = Spaces.spaceM, vertical = Spaces.spaceS)
            ){
                Text(
                    text="Watch Linked",
                    style=MaterialTheme.typography.labelLarge,
                    color= MaterialTheme.colorScheme.onSurface)

            }
        }
    }

}



@Composable
private fun card(states: PairWatchStates, modifier: Modifier= Modifier){
    val (title, desc, accent)=when (states){
        PairWatchStates.InitialState -> Triple("Ready to connect", "Make sure WI-FI or Bluetooth is enabled and your watch is neraby",primaryColor)
        PairWatchStates.Loading -> Triple("Pairing in progress","We are linking this watch to the current SafeMinds watch",
            warningColor)

        PairWatchStates.Success -> Triple("Paired Successfully","Your watch is now linked with your phone and start syncing data",successColor)
       is PairWatchStates.Failure -> Triple("Connection failed",states.messg,highRiskColor)
    }

    Column(
        modifier=modifier
            .fillMaxWidth().background(MaterialTheme.colorScheme.surface.copy(0.72f)
            , MaterialTheme.shapes.large)
            .border(1.dp,accent.copy(alpha = 0.20f), MaterialTheme.shapes.large)
            .padding(Spaces.spaceL)
    ) {
        Box(
            Modifier.size(width=42.dp, height = 5.dp)
                .background(accent,CircleShape)

        )
        Spacer(Modifier.height(Spaces.spaceM))

        Text(
            title, style = MaterialTheme.typography.titleMedium,
            color= MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(Spaces.spaceS))

        Text(
            desc, style = MaterialTheme.typography.bodyMedium,
            color= MaterialTheme.colorScheme.onSurfaceVariant
        )


    }
}




@Composable
private fun screenBackground(){
    Box(Modifier.fillMaxSize()){
        Box(Modifier.size(240.dp).padding(top=60.dp,
            start = 30.dp).alpha(0.16f).background(secondaryColor,CircleShape))

        Box(Modifier.size(280.dp).padding(top=260.dp, start = 180.dp).alpha(0.12f).background(primaryColor,CircleShape))
    }
}




