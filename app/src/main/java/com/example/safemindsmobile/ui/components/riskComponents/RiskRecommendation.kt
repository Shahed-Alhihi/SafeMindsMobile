package com.example.safemindsmobile.ui.components.riskComponents

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.safemindsmobile.data.model.RecommendationType
import com.example.safemindsmobile.data.model.Recommendations
import com.example.safemindsmobile.ui.components.SafeMindsCard
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.warningColor

@Composable
fun RiskRecommendation (recommendation: Recommendations){
    val color=when(recommendation.type) {
        RecommendationType.GOOD -> successColor
        RecommendationType.WARNING -> warningColor
        RecommendationType.INFO -> primaryColor
        RecommendationType.URGENT_ALERT -> highRiskColor
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
        Modifier.offset(y=offset.dp)
            .graphicsLayer(alpha=alpha)
    ){
        SafeMindsCard{
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spaces.spaceM),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(9.dp)
                        .clip(CircleShape)
                        .background(color)
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
                        text=recommendation.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color= MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}