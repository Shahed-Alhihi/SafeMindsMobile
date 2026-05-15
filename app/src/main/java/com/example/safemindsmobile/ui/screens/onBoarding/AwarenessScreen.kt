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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.screensComponents.SafeMindsPrimaryButtons
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.secondaryColor
import kotlinx.coroutines.delay
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun  AwarenessScreen (
    navController: NavHostController
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
            AwarenessDesc()

        }

        AnimatedVisibility(
            visible=isVisible,
            enter= fadeIn(tween ( 1100 ))
            )
        {
            SafeMindsPrimaryButtons(
                label = "Next",
                onClick = {
                    navController.navigate(AppScreens.InsightsScreen.flow)
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
            text = "AWARENESS",
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
            onBoardingType(isActive=true)
            onBoardingType(isActive=false)
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
private fun AwarenessDesc(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spaces.spaceS)
    ) {
        Text(
            text = "Small changes happen every day",
            style = MaterialTheme.typography.headlineMedium,
            color= MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Text(
            text = "We help you notice them",
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
    val transition= rememberInfiniteTransition(label = "animation")


    val heart by transition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation= tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heart"

    )

    val rotate by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation= tween(16000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotate"

    )

    Canvas(
        modifier= Modifier.fillMaxWidth().height(280.dp)
    )
    {
        val xCenter=size.width/2f
        val yCenter=size.height/2f
        val radius=size.minDimension *0.65f

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    secondaryColor.copy(alpha = 0.12f), Color.Transparent
                ),
                center= Offset(xCenter,yCenter),
                radius=radius *2.5f
            ),

            center= Offset(xCenter,yCenter),
            radius=radius *2.5f

        )

        drawCircle(
            color = secondaryColor.copy(alpha = 0.35f),
            center=Offset(xCenter,yCenter),
            radius = radius*1.5f*heart,
            style = Stroke(
                width = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f,8f),0f)
            )
        )
        drawCircle(
            color = secondaryColor.copy(alpha = 0.2f),
            center=Offset(xCenter,yCenter),
            radius = radius,
            style = Stroke(width = 1.dp.toPx()))

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    secondaryColor.copy(alpha = 0.22f),
                    primaryColor.copy(alpha=0.08f)
                ),
                center=Offset(xCenter,yCenter),
                radius = radius*0.8f,
            ),
            center=Offset(xCenter,yCenter),
            radius = radius*0.8f,
        )


        val angles=listOf(0f,90f, 180f, 270f)
        val shapeRadius=radius

        angles.forEachIndexed { index, angle ->
            val radian= toRadians((angle +rotate).toDouble())
            val xAngle=xCenter +shapeRadius* cos(radian).toFloat()
            val yAngle=yCenter +shapeRadius* sin(radian).toFloat()

            drawLine(
                color=primaryColor.copy(alpha = 0.08f),
                start = Offset(xCenter,yCenter),
                end=Offset(xAngle,yAngle),
                strokeWidth = 0.8.dp.toPx()
            )

            drawCircle(
                color = if (index %2==0) primaryColor else
                secondaryColor,
                center= Offset(xAngle,yAngle),
                radius = 5.dp.toPx()
            )
        }

        val lineY=yCenter+radius*0.1f
        val width=radius*0.8f


        val points=listOf(
            Offset(xCenter-width/2,lineY),
            Offset(xCenter-width/4,lineY),
            Offset(xCenter-width/4,lineY -radius*0.22f),
            Offset(xCenter,lineY+radius*0.16f),
            Offset(xCenter+width/8,lineY-radius*0.1f),
            Offset(xCenter+width/4,lineY) ,
            Offset(xCenter+width/2,lineY)



        )

        for (i in 0 until points.lastIndex){
            drawLine(
                color=primaryColor.copy(alpha = 0.75f),
                start = points[i],
                end=points[i+1],
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        }



    }

}