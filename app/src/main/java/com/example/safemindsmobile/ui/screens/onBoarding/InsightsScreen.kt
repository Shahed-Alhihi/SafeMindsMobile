package com.example.safemindsmobile.ui.screens.onBoarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.safemindsmobile.R
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.screensComponents.SafeMindsPrimaryButtons
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.secondaryColor
import kotlinx.coroutines.delay

@Composable
fun InsightsScreen (        navController: NavHostController
) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(200); isVisible = true
    }

    Column(modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(horizontal = Spaces.spaceXL, vertical = Spaces.spaceXL),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(Spaces.spaceXL))
        AnimatedVisibility(
            visible =isVisible,
            enter= fadeIn(tween ( 500 ))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spaces.spaceS)
            ) {
                ScreenTitle()

                Spacer(modifier = Modifier.height(Spaces.spaceS))


                animation()


            }



        }

        AnimatedVisibility(
            visible=isVisible,
            enter= fadeIn(tween ( 900 )) + slideInVertically (
                initialOffsetY = {it/6},
                animationSpec = tween(900, easing = FastOutSlowInEasing)
            )
        ) {
            InsightDesc()

        }

        AnimatedVisibility(
            visible=isVisible,
            enter= fadeIn(tween ( 1100 ))
        )
        {
            SafeMindsPrimaryButtons(
                label = "Next",
                onClick = {
                    navController.navigate(AppScreens.SupportScreen.flow)
                }
            )

        }






    }


}


@Composable
private fun ScreenTitle(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "INSIGHTS",
            style = MaterialTheme.typography.labelLarge.copy(
                color= primaryColor.copy(alpha = 0.6f),
                letterSpacing = 3.sp,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light
            )
        )

        Spacer(modifier = Modifier.height(Spaces.spaceS))

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spaces.spaceXS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            onBoardingType(isActive=false)
            onBoardingType(isActive=true)
            onBoardingType(isActive=false)

        }
    }
}


@Composable
private fun onBoardingType(isActive: Boolean){
    Box(
        modifier = Modifier.width(if (isActive) 24.dp else 8.dp)
            .height(8.dp)
            .clip(CircleShape)
            .background(if (isActive) primaryColor else
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
    )
}

@Composable
private fun InsightDesc(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spaces.spaceS)
    ) {
        Text(
            text = "SafeMinds",
            style = MaterialTheme.typography.headlineMedium,
            color= MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Turns daily data into meaningful insights",
            style = MaterialTheme.typography.headlineMedium.copy(
                color= primaryColor,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spaces.spaceXS))


    }
}


@Composable
private fun animation(){
    val transition= rememberInfiniteTransition(label = "watch")


    val heart by transition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation= tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heart"

    )

    val orbit by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation= tween(16000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit"

    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth().height(260.dp))
    {
        Canvas(
            modifier = Modifier.fillMaxWidth().height(260.dp)
        )
        {
            val centerX=size.width/2f
            val centerY=size.height/2f

            drawCircle(
                brush = Brush.radialGradient(
                    colors=listOf(
                        secondaryColor.copy(alpha = 0.30f),
                        Color.Transparent
                    ),
                    center=Offset(centerX,centerY),
                    radius = 160.dp.toPx()
                ),
                center=Offset(centerX,centerY),
                radius = 160.dp.toPx()
            )

            drawCircle(
                color = secondaryColor.copy(alpha = 0.12f),
                center=Offset(centerX,centerY),
                radius = 80.dp.toPx(),
                style = Stroke(width = 1.dp.toPx()
                )
            )

            val angles=listOf(0f,120f,240f)
            angles.forEachIndexed { index, angle ->

                val rad=Math.toRadians((angle+orbit).toDouble())
                val x=centerX+115.dp.toPx()*kotlin.math.cos(rad).toFloat()
                val y=centerY+115.dp.toPx()*kotlin.math.sin(rad).toFloat()


                val orbColor=when (index){
                    0 ->primaryColor
                    1 -> secondaryColor
                    else -> primaryColor.copy(alpha = 0.6f)
                }

                drawCircle(
                    color=orbColor.copy(alpha=0.18f),
                    center= Offset(x,y),
                    radius = 10.dp.toPx()
                )

                drawCircle(
                    color=orbColor,
                    center= Offset(x,y),
                    radius = 4.dp.toPx()
                )

            }

        }

        Image(
            painter = painterResource(id=R.drawable.watch),
            contentDescription = "smartwatch",
            modifier = Modifier.size(250.dp)
                .graphicsLayer{
                    scaleX=heart
                    scaleY=heart

                },
            contentScale = ContentScale.Fit
        )
    }


}