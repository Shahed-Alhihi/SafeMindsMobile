package com.example.safemindsmobile.ui.components.vitalsComponents

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.data.model.ActivityLevel
import com.example.safemindsmobile.data.model.VitalsData
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.warningColor

@Composable
fun ECGCard (
    data: VitalsData) {
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
