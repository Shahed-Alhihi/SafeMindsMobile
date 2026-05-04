package com.example.safemindsmobile.ui.screens.dashboard

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.R
import com.example.safemindsmobile.ui.components.appIndicators.SafeMindsStatus
import com.example.safemindsmobile.ui.components.appIndicators.SectionHeader
import com.example.safemindsmobile.ui.components.appIndicators.StatusContainer
import com.example.safemindsmobile.ui.components.cards.ChartCards
import com.example.safemindsmobile.ui.components.cards.SafeMindsCard
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.surfaceVariantColor
import com.example.safemindsmobile.ui.theme.warningColor

@Composable
fun DashboardScreen (
    onLogout:()->Unit,
    onSleepAnalysis:()->Unit,
    onVitalsAnalysis:()->Unit,
){
    val recommendation=listOf(
        Recommendation(
            color=successColor,
            title = "Improve sleep consistency",
            body = "Your last 3 nights varied by 90 min, try a fixed bedtime"
        ),
        Recommendation(
            color = primaryColor,
            title = "2,760 steps to your goal",
            body = "A short evening walk would close today's activity gap"
        ),

        Recommendation(
            color=warningColor,
            title = "Afternoon HR spike",
            body = "Heart rate hit 102 bpm at 3 PM, consider a short break "
        )

    )



    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spaces.spaceL)
            .padding(bottom = Spaces.spaceL),
        verticalArrangement = Arrangement.spacedBy(Spaces.spaceL)
    ) {
        header(onLogout)
        scoreCard(
            riskScore = 72,
            label = "Med-risk",
            status = SafeMindsStatus.MEDIUM_RISK,
            desc = "medium risk detected according to your data for the previous week"
        )

        SectionHeader(label = "Sleep quality", action = "See all", click = onSleepAnalysis)
        sleepAnalysisChart()

        SectionHeader(label = "Activity", action = "See all", click = onVitalsAnalysis)
        activityChart()

        SectionHeader(label = "Heart rate", action = "See all", click = onVitalsAnalysis)
        heartRateChart()

        SectionHeader(label = "Recommendations")

        recommendation.forEach { recommendationCard(item = it) }



    }
}



@Composable
private fun header(onLogout: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spaces.spaceS)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            )
            {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "SafeMinds logo",
                    modifier = Modifier.size(28.dp)
                )
            }

            Column {
                Text(
                    text = "SafeMinds",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Your wellness overview",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }
        }
        TextButton(onClick = {
            onLogout()
        }) {
            Text(
                text = "Logout",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}




@Composable
private fun scoreCard(
    riskScore: Int,
    label: String,
    status: SafeMindsStatus,
    desc: String
){
    val color=when(status){
        SafeMindsStatus.LOW_RISK -> successColor
        SafeMindsStatus.MEDIUM_RISK -> warningColor
        SafeMindsStatus.HIGH_RISK -> highRiskColor
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
                        status=status
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

@Composable
private fun sleepAnalysisChart() {
    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val values = listOf(0.52f, 0.63f, 0.42f, 0.78f, 0.58f, 0.72f, 0.88f)


    metricChart(
        label = "Weekly sleep overview",
        value = "6.8h",
        subTitle = "Average sleep duration"
    ){
        screenBarChart(
            values=values,
            labels=days,
            color = successColor
        )
    }
}




private data class Recommendation(
    val color: Color,
    val title: String,
    val body: String
)

@Composable
private fun recommendationCard(
    item: Recommendation
){
    SafeMindsCard{
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spaces.spaceM),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(9.dp)
                    .clip(CircleShape)
                    .background(item.color)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text=item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color= MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text=item.body,
                    style= MaterialTheme.typography.bodyMedium,
                    color= MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

}



@Composable
private fun screenLineChart(
    points: List<Float>,
    color: Color
){
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {
        if (points.size<2) return@Canvas

        val eachStep=size.width/(points.size-1)
        val lineChartPoints=points.mapIndexed { index, data ->
            Offset(
                x=index*eachStep,
                y=size.height*(1f-data)
            )
    }

        val fillPath=Path().apply {
            moveTo(lineChartPoints.first().x,lineChartPoints.first().y)

            lineChartPoints.drop(1).forEach {
                lineTo(it.x, it.y)
            }

            lineTo(lineChartPoints.last().x,size.height)
            lineTo(0f,size.height)
            close()
            }


        val linePath=Path().apply {
            moveTo(lineChartPoints.first().x,lineChartPoints.first().y)
            lineChartPoints.drop(1).forEach {
                lineTo(it.x, it.y)
            }
        }


        drawPath(
            path=fillPath,
            color=color.copy(alpha=0.08f)
        )

        drawPath(
            path=linePath,
            color=color,
            style= Stroke(
                width = 2.dp.toPx(),
                cap=StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
        }
}


@Composable
private fun screenProgressBar(
    progress: Float,
    color: Color
){
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(7.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .clip(CircleShape)
                .background(color)
        )
    }
}


@Composable
private fun screenBarChart(
    values: List<Float>,
    labels: List<String>,
    color: Color
){
    Row(modifier = Modifier
        .fillMaxWidth().height(65.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        values.forEachIndexed { index, f ->
            Box(
                modifier = Modifier
                    .weight(1f).fillMaxHeight(f)
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    .background(
                        if (index==values.lastIndex) color
                        else color.copy(alpha = 0.45f)
                    )
            )
        }
    }

    Spacer(modifier = Modifier.height(4.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        labels.forEachIndexed { index, day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp),
                color=if (index==labels.lastIndex) MaterialTheme.colorScheme.onSurface
        else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

}



@Composable
private fun metricChart(
    label: String,
    value: String,
    subTitle: String,
    content: @Composable () -> Unit
){
    ChartCards(
        label=label
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )


        Text(
            text = subTitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Spaces.spaceM))

        content()
    }
}




@Composable
private fun heartRateChart(){
    metricChart(
        label = "Heart rate",
        value = "63 bpm",
        subTitle = "Resting heart rate"
    ) {
        screenLineChart(
            points = listOf(
                0.50f,
                0.58f,
                0.35f,
                0.85f,
                0.20f,
                0.55f,
                0.62f,
                0.45f,
                0.70f,
                0.52f
            ),
            color = highRiskColor
        )
    }
}



@Composable
private fun activityChart() {
    metricChart(
        label = "Daily activity",
        value = "7,240",
        subTitle = "Steps today, 10K goal"
    ) {
        screenProgressBar(
            progress = 0.724f,
            color = primaryColor
        )

        Spacer(modifier = Modifier.height(Spaces.spaceM))

        screenLineChart(
            points = listOf(0.85f, 0.78f, 0.45f, 0.55f, 0.32f, 0.22f),
            color = primaryColor
        )

    }
}
