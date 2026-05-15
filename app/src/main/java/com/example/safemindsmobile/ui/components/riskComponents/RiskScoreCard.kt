package com.example.safemindsmobile.ui.components.riskComponents

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.data.model.RiskData
import com.example.safemindsmobile.data.model.RiskLevel
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.warningColor

@Composable
fun RiskScoreCard(
    data:RiskData){
    var triggered by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        triggered=true
    }

    val animatedScore by animateIntAsState(
        targetValue = if (triggered) data.riskScore else 0,
        animationSpec = tween(1400, easing = FastOutSlowInEasing),
        label = "riskScore"
    )

    val color=when (data.riskLevel){
        RiskLevel.LOW -> successColor
        RiskLevel.MEDIUM -> warningColor
        RiskLevel.HIGH -> highRiskColor

    }

    val label= when (data.riskLevel) {
        RiskLevel.LOW -> "Low Risk"
        RiskLevel.MEDIUM -> "Medium Risk"
        RiskLevel.HIGH -> "High Risk"
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF2A4A5A),
                        Color(0xFF1C6B6B),
                        Color(0xFF2A7A7A))
                ))
            .padding(Spaces.spaceXL),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spaces.spaceM)

        ) {
            Text(
                text = "Composite stress index",
                style = MaterialTheme.typography.bodySmall.copy(letterSpacing = 2.sp, fontSize = 9.sp),
                color=Color.White.copy(alpha=0.55f)

            )

            Text(
                text = "$animatedScore",
                style= MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 64.sp
                ),
                color=Color.White,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color.copy(alpha=0.20f))
                    .padding(horizontal = Spaces.spaceL, vertical = Spaces.spaceS),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = color
                )

            }

            Text(
                text = data.desc,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha=0.60f),
                textAlign = TextAlign.Center
            )

        }
    }

}