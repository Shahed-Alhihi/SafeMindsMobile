package com.example.safemindsmobile.ui.components.vitalsComponents

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.data.model.DailyHR
import com.example.safemindsmobile.ui.components.screensComponents.SafeMindsCard
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.successColor


@Composable
fun VitalHR(
    days: List<DailyHR>,
    avgHr: Int,
    minHr: Int,
    maxHr: Int
) {
    var triggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        triggered = true
    }

    SafeMindsCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spaces.spaceM),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            listOf(
                Triple(avgHr.toString(), "Avg", MaterialTheme.colorScheme.onSurface),
                Triple(minHr.toString(), "Min", successColor),
                Triple(maxHr.toString(), "Max", highRiskColor)
            ).forEach { (value, label, color) ->
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
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            days.forEachIndexed { index, day ->
                val bar by animateFloatAsState(
                    targetValue = if (triggered) day.value.coerceAtLeast(0.05f) else 0.01f,
                    animationSpec = tween(900, index * 80, easing = FastOutSlowInEasing),
                    label = "HRBar$index"
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(bar)
                        .clip(
                            RoundedCornerShape(
                                topStart = 4.dp,
                                topEnd = 4.dp
                            )
                        )
                        .background(
                            when {
                                day.value > 0.80f -> highRiskColor
                                index == days.lastIndex -> primaryColor
                                else -> successColor.copy(alpha = 0.6f)
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
                    text = day.day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = if (index == days.lastIndex) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}