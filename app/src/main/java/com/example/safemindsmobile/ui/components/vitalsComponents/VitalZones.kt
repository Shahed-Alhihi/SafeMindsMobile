package com.example.safemindsmobile.ui.components.vitalsComponents

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.data.model.HRZone
import com.example.safemindsmobile.ui.components.SafeMindsCard
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.warningColor

@Composable
fun VitalZones(
    zone:List<HRZone>){
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