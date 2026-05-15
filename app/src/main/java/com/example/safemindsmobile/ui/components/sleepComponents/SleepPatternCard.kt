package com.example.safemindsmobile.ui.components.sleepComponents

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.safemindsmobile.data.model.Day
import com.example.safemindsmobile.ui.components.screensComponents.SafeMindsCard
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.warningColor


@Composable
fun SleepPatternCard (
    weekData: List<Day>
) {
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
                    targetValue = if (trigger) (day.hours / day.maxHours).coerceIn(
                        0.1f,
                        1f
                    ) else 0.01f,
                    animationSpec = tween(
                        durationMillis = 900,
                        delayMillis = index * 100,
                        easing = FastOutSlowInEasing
                    ),
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
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                    color = Color.White.copy(alpha = 0.85f)

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
            ) {
                item(color = successColor, label = "Good >= 85%")
                item(color = warningColor, label = "Fair 75-84%")
                item(color = highRiskColor, label = "Poor <75%")

            }
        }
    }}


    @Composable
     private fun item(
        color: Color,
        label: String
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)

        ) {
            Box(
                modifier = Modifier
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



private fun sleepEfficiencyColor(efficiency: Int): Color {
    return when{
        efficiency >=85 -> successColor
        efficiency >=75 -> warningColor
        else-> highRiskColor
    }
}

