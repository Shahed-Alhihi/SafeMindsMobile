package com.example.safemindsmobile.ui.components.vitalsComponents

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.safemindsmobile.data.model.RecommendationType
import com.example.safemindsmobile.data.model.VitalRecommendation
import com.example.safemindsmobile.ui.components.screensComponents.SafeMindsCard
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.warningColor


@Composable
fun VitalRecommendation (recommendations: VitalRecommendation){
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
    }}



