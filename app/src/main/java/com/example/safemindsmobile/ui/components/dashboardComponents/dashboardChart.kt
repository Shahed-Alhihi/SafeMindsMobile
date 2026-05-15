package com.example.safemindsmobile.ui.components.dashboardComponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.data.model.DashboardActivitySummary
import com.example.safemindsmobile.data.model.DashboardHeartRateSummary
import com.example.safemindsmobile.data.model.DashboardSleepSummary
import com.example.safemindsmobile.ui.components.screensComponents.ChartCards
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.successColor

@Composable
 fun dashboardSleepChart(
    data: DashboardSleepSummary
) {
    metricChart(
        label = "Weekly sleep overview",
        value = data.avgSleep,
        subTitle = data.subtitle
    ){
        screenBarChart(
            values=data.values,
            labels=data.days,
            color = successColor
        )
    }

}

@Composable
fun dashboardActivityChart(
    data: DashboardActivitySummary
) {
    metricChart(
        label = "Activity level",
        value = data.activityLevel.toString(),
        subTitle = "Based on movement data"
    ) {
        screenProgressBar(
            progress = data.progress,
            color = primaryColor
        )

        Spacer(modifier = Modifier.height(Spaces.spaceM))

        screenLineChart(
            points = data.activityChart,
            color = primaryColor
        )

    }
}

@Composable
fun dashboardHeartRateChart(
    data: DashboardHeartRateSummary
) {
    metricChart(
        label = "Heart rate",
        value = "${data.averageHr.toInt()} bpm",
        subTitle = "Latest heart rate summary"
    ) {
        if(data.heartRateChart.isNotEmpty())
        screenLineChart(
            points = data.heartRateChart,
            color = highRiskColor
        )
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
       val minValue=points.minOrNull() ?:0f
        val maxValue=points.maxOrNull() ?:1f
        val range=(maxValue-minValue).takeIf { it!= 0f } ?: 1f

        val lineChartPoints=points.mapIndexed { index, data ->
            val normalized=(data-minValue)/ range

            Offset(
                x=index *eachStep,
                y=size.height * (1f - normalized)
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







