package com.example.safemindsmobile.ui.screens.vitals

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.safemindsmobile.R
import com.example.safemindsmobile.data.model.VitalsData
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.appIndicators.SectionHeader
import com.example.safemindsmobile.ui.components.cards.SafeMindsCard
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.warningColor
import com.example.safemindsmobile.data.model.ActivityLevel
import com.example.safemindsmobile.data.model.HRZone
import com.example.safemindsmobile.data.model.DailyHR
import com.example.safemindsmobile.data.model.VitalRecommendation
import com.example.safemindsmobile.data.model.RecommendationType



@Composable
fun VitalsAnalysisScreen (
    data: VitalsData,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize().verticalScroll(rememberScrollState())
            .padding(horizontal = Spaces.spaceL)
            .padding(bottom = Spaces.spaceL),
        verticalArrangement = Arrangement.spacedBy(Spaces.spaceL)
    ) {
        header(navController)
        ECGCard(data)
        SectionHeader(
            label="Heart rate zones",
            action = "Today",
            click = {}
        )

        HRcard(data.HRZones)
        SectionHeader(
            label="Weekly HR trend",
            action = "",
            click = {}
        )

        weeklyHR(data.weeklyHR)
        SectionHeader(
            label="Recommendations",
            action = "",
            click = {}
        )

      data.recommendations.forEach { recommendation ->
          recommendationCard(recommendation)
      }



    }
}

    @Composable
    private fun header(
        navController: NavController
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spaces.spaceM),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
           Row(
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.spacedBy(Spaces.spaceS)
           ) {
               Box(
                   modifier = Modifier.size(38.dp)
                       .clip(MaterialTheme.shapes.small)
                       .background(MaterialTheme.colorScheme.surfaceVariant),
                   contentAlignment = Alignment.Center
               ){
                   Image(
                       painter = painterResource(R.drawable.logo),
                       contentDescription = null,
                       modifier = Modifier.size(28.dp)
                   )
               }

               Column {
                   Text(
                       text = "SafeMinds",
                       style = MaterialTheme.typography.titleLarge.copy(
                           fontWeight = FontWeight.Bold
                       ),
                       color = MaterialTheme.colorScheme.onBackground)

                    Text(
                        text = "HeartRate Analysis",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
               }
           }

            TextButton(onClick = {
                navController.navigate(AppScreens.LoginScreen.flow){
                    popUpTo(AppScreens.Main.flow){
                        inclusive=true
            }
                    launchSingleTop=true
                }
            }){
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }
}


@Composable
private fun ECGCard(data: VitalsData) {
    var triggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        triggered = true
    }

    val reveal by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1400, easing = FastOutSlowInEasing),
        label = "ECGReveal"
    )

    val activityLabel = when (data.activityLevel) {
        ActivityLevel.LOW -> "Low"
        ActivityLevel.MEDIUM -> "Medium"
        ActivityLevel.HIGH -> "High"
    }

    val activityColor = when (data.activityLevel) {
        ActivityLevel.LOW -> successColor
        ActivityLevel.MEDIUM -> warningColor
        ActivityLevel.HIGH -> highRiskColor

    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(Color(0xFF2F3A3D))
            .padding(Spaces.spaceL)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spaces.spaceM)
        ) {
            Text(
                text = "Live Signal",
                style = MaterialTheme.typography.labelMedium.copy(
                    letterSpacing = 2.sp,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color.White.copy(alpha = 0.85f)
            )

            Canvas(modifier = Modifier.fillMaxWidth().height(44.dp)) {

                val points = listOf(
                    Offset(0f, size.height * 0.50f),
                    Offset(size.width * 0.10f, size.height * 0.50f),
                    Offset(size.width * 0.13f, size.height * 0.18f),
                    Offset(size.width * 0.16f, size.height * 0.85f),
                    Offset(size.width * 0.19f, size.height * 0.42f),
                    Offset(size.width * 0.22f, size.height * 0.50f),
                    Offset(size.width * 0.40f, size.height * 0.50f),
                    Offset(size.width * 0.43f, size.height * 0.15f),
                    Offset(size.width * 0.46f, size.height * 0.88f),
                    Offset(size.width * 0.49f, size.height * 0.40f),
                    Offset(size.width * 0.52f, size.height * 0.50f),
                    Offset(size.width * 0.70f, size.height * 0.50f),
                    Offset(size.width * 0.73f, size.height * 0.20f),
                    Offset(size.width * 0.76f, size.height * 0.82f),
                    Offset(size.width * 0.79f, size.height * 0.44f),
                    Offset(size.width * 0.82f, size.height * 0.50f),
                    Offset(size.width, size.height * 0.50f)
                )

                clipRect(right = size.width * reveal) {
                    val path = Path().apply {
                        moveTo(points[0].x, points[0].y)
                        points.drop(1).forEach {
                            lineTo(it.x, it.y)
                        }

                    }

                    drawPath(
                        path = path,
                        color = successColor,
                        style = Stroke
                            (
                            width = 1.8.dp.toPx(),
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )


                }

                if (reveal > 0.85f) {
                    val dotsAlpha = ((reveal - 0.85f) / 0.15f).coerceIn(0f, 1f)

                    listOf(0.88f, .092f, 0.96f).forEachIndexed { index, f ->
                        drawCircle(
                            color = primaryColor.copy(alpha = dotsAlpha * (1f - index * 0.3f)),
                            radius = 3.dp.toPx(),
                            center = Offset(size.width * f, size.height * 0.50f)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spaces.spaceS)
            ) {
                listOf(
                    Triple("${data.averageHR}", "Average bpm", Color.White),
                    Triple("${data.restingHR}", "Resting", successColor),
                    Triple("${data.peakHR}", "Peak", highRiskColor),
                    Triple(activityLabel, "Activity", activityColor)
                ).forEach { (value, label, color) ->
                    Box(
                        modifier = Modifier.weight(1f)
                            .clip(MaterialTheme.shapes.medium)
                            .background(Color.White.copy(alpha = 0.10f))
                            .padding(vertical = Spaces.spaceS),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {
                            Text(
                                text = value,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = color
                            )

                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                color = Color.White.copy(0.75f)
                            )

                        }
                    }


                }
            }



            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(0.5.dp)
                    .background(Color.White.copy(alpha = 0.18f))
            )
            Text(
                text = "Based on Today's watch data",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = Color.White.copy(alpha = 0.75f)
            )
        }
    }
}




@Composable
private fun HRcard(zone:List<HRZone>){
    var triggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        triggered=true
    }


    val zonesColor=listOf(
        successColor,
        warningColor,
        highRiskColor,
        primaryColor
    )


    SafeMindsCard{
        Column(verticalArrangement = Arrangement.spacedBy(Spaces.spaceS) ){
            zone.forEachIndexed { index, zones ->

                val fill by animateFloatAsState(
                    targetValue = if (triggered){
                        (zones.min.toFloat()/zones.maxMin).coerceIn(0.01f,1f)
                    }
                    else
                    {
                        0.01f
                    },

                    animationSpec = tween(900,index*100, easing = FastOutSlowInEasing),
                    label = "zones$index"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spaces.spaceS)

                ) {
                    Column(
                        modifier = Modifier.width(72.dp)
                    ) {
                        Text(
                            text=zones.label,
                            style= MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color= MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = zones.range,
                            style= MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            color= MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }


                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(7.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.CenterStart)
                    {
                        Box(
                            modifier = Modifier.fillMaxWidth(fill)
                                .fillMaxHeight().clip(CircleShape)
                                .background(zonesColor[index])
                        )
                    }

                    Text(
                        text = "${zones.min}m",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color= MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.width(34.dp)

                    )

                }

            }

        }
    }
}




@Composable
private fun weeklyHR(days: List<DailyHR>){
    var triggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        triggered=true
    }

    SafeMindsCard {
        Row(modifier = Modifier
            .fillMaxWidth().padding(bottom = Spaces.spaceM),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            listOf(
                Triple("71","Avg", MaterialTheme.colorScheme.onSurface),
                Triple("57","Min",successColor),
                Triple("124","Max",highRiskColor)
            ).forEach {
                (value,label,color)->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = color
                    )
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )


                }

            }
        }


        Row(
            modifier = Modifier.fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            days.forEachIndexed { index, day ->
                val bar by animateFloatAsState(
                    targetValue = if (triggered) day.value.coerceAtLeast(0.05f) else 0.01f,
                    animationSpec = tween(900, index * 80, easing = FastOutSlowInEasing),
                    label ="HRBar$index"
                )


                Box(
                    modifier = Modifier.weight(1f)
                        .fillMaxHeight(bar)
                        .clip(
                            RoundedCornerShape(
                                topStart = 4.dp,
                                topEnd = 4.dp
                            )
                        )
                        .background(
                            when{
                                day.value>0.80f -> highRiskColor
                                index==days.lastIndex ->primaryColor
                                else ->successColor.copy(alpha=0.6f)
                            }
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            days.forEachIndexed { index, day ->
                Text(
                    text=day.day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color=if (index==days.lastIndex){
                        MaterialTheme.colorScheme.onSurface
                    }
                    else{
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }

}



@Composable
private fun recommendationCard(recommendations: VitalRecommendation){
    val color=when(recommendations.type){
        RecommendationType.URGENT_ALERT->highRiskColor
        RecommendationType.INFO->primaryColor
        RecommendationType.WARNING->warningColor
        RecommendationType.GOOD->successColor
    }

    var triggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        triggered=true
    }

    val offset by animateFloatAsState(
        targetValue = if (triggered) 0f else 20f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "offset"
    )

    val alpha by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(500),
        label = "alpha"
    )

    Box(
        modifier = Modifier.offset(y=offset.dp)
            .graphicsLayer(alpha=alpha)
    ){
        SafeMindsCard {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spaces.spaceM),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(color)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = recommendations.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color= MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text=recommendations.description,
                        style= MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color= MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

        }
    }


}