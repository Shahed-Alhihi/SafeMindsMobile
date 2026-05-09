package com.example.safemindsmobile.ui.components.sleepComponents

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.ui.theme.Spaces

@Composable
fun SleepScoreCard(
    sleepScore: Int,
    sleepDuration: String,
    sleepQuality: String,
    sleepEfficiency: String,
    sleepFragmentation: String
) {
    var trigger by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        trigger = true
    }

    val animateScore by animateIntAsState(
        targetValue = if (trigger) sleepScore else 0,
        animationSpec = tween(1400, easing = FastOutSlowInEasing),
        label = "score"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF2A4A5A),
                        Color(0xFF1C6B6B),
                        Color(0xFF2A7A7A)
                    )
                )
            )
            .padding(Spaces.spaceL)
    ) {
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
                    stateItem(label = "duration", value = sleepDuration)
                    stateItem(label = "efficiency", value = sleepEfficiency)
                    stateItem(label = "fragmentation", value = sleepFragmentation)

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Overall sleep score",
                        style = MaterialTheme.typography.bodySmall.copy(letterSpacing = 0.5.sp),
                        color = Color.White.copy(alpha = 0.7f)
                    )

                    Text(
                        text = "$animateScore%",
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 52.sp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = sleepQuality,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.75f)
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