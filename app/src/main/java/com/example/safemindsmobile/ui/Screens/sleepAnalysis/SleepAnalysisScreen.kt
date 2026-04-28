package com.example.safemindsmobile.ui.Screens.sleepAnalysis

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.ui.components.AppIndicators.SectionHeader
import com.example.safemindsmobile.ui.components.Cards.SafeMindsCard
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.warningColor

class SleepData(
    val sleepScore: Int,
    val sleepDuration: String,
    val sleepQuality: String,
    val sleepEfficiency: String,
    val sleepFragmentation: String,
    val weekData: List<Day>,
    val recommendations: List<RecommendationContent>

)

data class Day(
    val day: String,
    val hours:Float,
    val efficiency: Int,
    val maxHours: Float=9f
)

enum class RecommendationType{
    LOW_RISK,
    MEDIUM_RISK,
    HIGH_RISK,
    INFO
}

data class RecommendationContent(
    val type:RecommendationType,
    val title: String,
    val description: String
)

@Composable
fun SleepAnalysisScreen (
    data: SleepData
){
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(horizontal = Spaces.spaceL)
            .padding(bottom = Spaces.spaceL),
        verticalArrangement = Arrangement.spacedBy(Spaces.spaceL)
    ) {
        header()

        sleepScore(
            sleepScore=data.sleepScore,
            sleepDuration=data.sleepDuration,
            sleepQuality=data.sleepQuality,
            sleepFragmentation=data.sleepFragmentation,
            sleepEfficiency=data.sleepEfficiency

        )

        SectionHeader(
            label = "Sleep patterns",
            action = "7 day overview",
            click = {}
        )

        screenCard(
            weekData = data.weekData
        )

        SectionHeader(
            label = "Sleep recommendations",
            action = "",
            click = {}
        )

        data.recommendations.forEach{
            recommendation ->
            sleepRecommendition(
                recommendation=recommendation
            )
        }


    }
}
@Composable
private fun header(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top=Spaces.spaceM),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spaces.spaceS)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(MaterialTheme.shapes.small)
                .background(primaryColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ){
            Icon(
                imageVector = Icons.Outlined.Bedtime,
                contentDescription = null,
                tint = primaryColor,
                modifier = Modifier.size(23.dp)
            )
        }

        Column {
            Text(
                text = "Sleep Analysis",
                style= MaterialTheme.typography.headlineMedium,
                color= MaterialTheme.colorScheme.onBackground
            )
            Text(
                text="Overall sleep quality",
                style= MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

}

@Composable
private fun sleepScore(
    sleepScore: Int,
    sleepDuration: String,
    sleepQuality: String,
    sleepEfficiency: String,
    sleepFragmentation: String
    ){
    var trigger by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        trigger = true
    }

    val animateScore by animateIntAsState(
        targetValue = if (trigger) sleepScore else 0,
        animationSpec = tween(1400,easing = FastOutSlowInEasing),
        label = "score"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(Brush.linearGradient(
                listOf(
                    Color(0xFF2A4A5A),
                    Color(0xFF1C6B6B),
                    Color(0xFF2A7A7A)
                )
            ))
            .padding(Spaces.spaceL)
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(Spaces.spaceL)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Spaces.spaceS),
                ) {
                    stateItem(label="duration", value = sleepDuration)
                    stateItem(label="efficiency",value=sleepEfficiency)
                    stateItem(label="fragmentation",value=sleepFragmentation)

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Overall sleep score",
                        style = MaterialTheme.typography.bodySmall.copy(letterSpacing = 0.5.sp),
                        color=Color.White.copy(alpha=0.7f)
                    )

                    Text(
                        text = "$animateScore%",
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 52.sp),
                        color=Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = sleepQuality,
                        style= MaterialTheme.typography.bodyMedium,
                        color=Color.White.copy(alpha=0.75f)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(Color.White.copy(alpha = 0.2f))
            )

            Text(
                text = "Based on last night's watch data",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.5f)
            )
        }
    }


}

@Composable
private fun stateItem(
    label: String,
    value: String
) {
    Column{
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha=0.65f)

    )
}
}
@Composable
private fun screenCard(
    weekData: List<Day>
){
    var trigger by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        trigger = true
    }


    SafeMindsCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spaces.spaceS)
        ) {
            weekData.forEachIndexed { index, day ->
                val barFraction by animateFloatAsState(
                    targetValue = if (trigger) (day.hours / day.maxHours).coerceIn(0.1f,1f) else 0.01f,
                    animationSpec = tween(durationMillis = 900, delayMillis = index*100, easing = FastOutSlowInEasing),
                    label = "bar$index"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spaces.spaceS)
                ) {
                    Text(
                        text = day.day,
                        style = MaterialTheme.typography.bodyMedium,
                        color= MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.width(32.dp)
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.CenterStart
                    )
                    {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(barFraction)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(50))
                                .background(sleepEfficiencyColor(day.efficiency)),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(horizontal = Spaces.spaceM),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${day.hours}h",
                                    style= MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                    color= Color.White.copy(alpha=0.85f)

                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spaces.spaceXS))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f))
            )

            Spacer(modifier = Modifier.height(Spaces.spaceXS))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                item(color=successColor, label = "Good >= 85%")
                item(color=warningColor, label = "Fair 75-84%")
                item(color=highRiskColor, label = "Poor <75%")

            }
        }
    }

}
@Composable
private fun item(
    color: Color,
    label: String
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)

    ) {
        Box(modifier = Modifier
            .size(width = 18.dp, height = 6.dp)
            .clip(CircleShape)
            .background(color)
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}


@Composable
private fun sleepRecommendition(
    recommendation: RecommendationContent
){
    var trigger by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {trigger=true }


    val offset by animateFloatAsState(
        targetValue = if (trigger) 0f else 20f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "offset"

    )

    val alpha by animateFloatAsState(
        targetValue = if (trigger) 1f else 0f,
        animationSpec = tween(500),
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .offset(y=offset.dp)
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
                        .background(recommendationColor(recommendation.type))
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = recommendation.title,
                        style = MaterialTheme.typography.titleMedium,
                        color= MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = recommendation.description,
                        style= MaterialTheme.typography.bodyMedium,
                        color= MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}



private fun sleepEfficiencyColor(efficiency: Int): Color {
    return when{
        efficiency >=85 -> successColor
        efficiency >=75 -> warningColor
        else-> highRiskColor
    }
}


private fun recommendationColor(
    type: RecommendationType
): Color{
    return when (type) {
        RecommendationType.LOW_RISK -> successColor
        RecommendationType.MEDIUM_RISK -> warningColor
        RecommendationType.HIGH_RISK -> highRiskColor
        RecommendationType.INFO -> primaryColor
    }

}

