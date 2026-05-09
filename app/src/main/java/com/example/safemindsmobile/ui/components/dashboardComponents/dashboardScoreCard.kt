package com.example.safemindsmobile.ui.components.dashboardComponents

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.data.model.RiskLevel
import com.example.safemindsmobile.ui.components.SafeMindsCard
import com.example.safemindsmobile.ui.components.StatusContainer
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.surfaceVariantColor
import com.example.safemindsmobile.ui.theme.warningColor

@Composable
fun dashboardScoreCard(
    riskScore: Int,
    label: String,
    status: RiskLevel,
    desc: String
) {
    val color=when(status){
        RiskLevel.LOW -> successColor
        RiskLevel.MEDIUM-> warningColor
        RiskLevel.HIGH -> highRiskColor
        else -> primaryColor
    }

    var animStart by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        animStart=true
    }

    val animateArc by animateFloatAsState(
        targetValue = if (animStart) riskScore/100f else 0f,
        animationSpec = tween(1200,easing= FastOutSlowInEasing),
        label = "CSI Arc"

    )

    val animateScore by animateIntAsState(
        targetValue = if (animStart) riskScore else 0,
        animationSpec = tween(1200, easing = FastOutSlowInEasing),
        label="CSI score"

    )


    SafeMindsCard{
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(165.dp)
            ){
                Canvas(
                    modifier=Modifier.size(165.dp)
                ){
                    val stroke=12.dp.toPx()
                    val inset=stroke/2f
                    val sizeOfArc= Size(size.width-stroke,size.height-stroke)

                    drawArc(
                        color=surfaceVariantColor.copy(alpha=0.6f),
                        startAngle = 150f,
                        sweepAngle = 240f,
                        useCenter = false,
                        topLeft = Offset(inset,inset),
                        size=sizeOfArc,
                        style= Stroke(stroke, cap = StrokeCap.Round))


                    drawArc(
                        color=color,
                        startAngle = 150f,
                        sweepAngle = 240f*animateArc,
                        useCenter = false,
                        topLeft = Offset(inset,inset),
                        size=sizeOfArc,
                        style= Stroke(stroke, cap = StrokeCap.Round)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        text = "CSI Score",
                        style= MaterialTheme.typography.bodySmall.copy(letterSpacing = 2.sp, fontSize = 8.sp),
                        color= MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$animateScore%",
                        style= MaterialTheme.typography.headlineLarge,
                        color= MaterialTheme.colorScheme.onBackground
                    )

                    StatusContainer(
                        label=label,
                        status= status
                    )

                }
            }

            Spacer(modifier = Modifier.height(Spaces.spaceS))

            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                color= MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha=0.7f))
                    .padding(horizontal = Spaces.spaceM, vertical = Spaces.spaceS)
            )
        }
    }
}