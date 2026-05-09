package com.example.safemindsmobile.ui.components.riskComponents

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.data.model.RiskData
import com.example.safemindsmobile.ui.components.SafeMindsCard
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.warningColor

@Composable
fun RiskReasonsCard(
    data:RiskData){
    var triggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { triggered=true}

    val marker by animateFloatAsState(
        targetValue = if (triggered) data.riskScore/100f else 0f,
        animationSpec = tween(1200,200, easing = FastOutSlowInEasing),
        label = "marker"

    )

    SafeMindsCard{
        Column(
            verticalArrangement = Arrangement.spacedBy(Spaces.spaceM)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.horizontalGradient(
                            listOf(successColor,warningColor,highRiskColor)
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(marker.coerceAtLeast(0.01f))
                    .wrapContentWidth(Alignment.End)
            ){
                Box(
                    modifier = Modifier
                        .size(width = 2.dp, height = 14.dp)
                        .offset(y=(-3).dp)
                        .background(MaterialTheme.colorScheme.onBackground)
                )
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween
        ) {
            Text(
                "0–30 Low",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 9.sp,
                    color = successColor
                )
            )

            Text(
                "31–74 Medium",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 9.sp,
                    color = warningColor
                )
            )

            Text(
                "75–100 High",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 9.sp,
                    color = highRiskColor
                )
            )
        }
        Box(
            Modifier.fillMaxWidth().height(0.5.dp)
                .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(
                    alpha=0.15f
                ))
        )

        Text(
            text=data.CSIExplanation,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 22.sp
        )
    }
}
