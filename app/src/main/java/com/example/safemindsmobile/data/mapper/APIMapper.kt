package com.example.safemindsmobile.data.mapper

import com.example.safemindsmobile.data.model.*
import com.example.safemindsmobile.data.remote.dto.CsiResultDto
import com.example.safemindsmobile.data.remote.dto.HomeData
import com.example.safemindsmobile.data.remote.dto.SleepDto
import com.example.safemindsmobile.data.remote.dto.VitalsDto

fun CsiResultDto.toRiskData(): RiskData{
    val level=when(risk_level.lowercase()){
        "high" ->RiskLevel.HIGH
        "medium" ->RiskLevel.MEDIUM
        else -> RiskLevel.LOW

    }
    return RiskData(
        riskScore =csi_score,
        riskLevel = level,
        desc =
            when(level){
                RiskLevel.LOW ->"Your current cognitive risk indicators look stable"
                RiskLevel.MEDIUM ->"Some changes were detected in your recent health patterns"
                RiskLevel.HIGH ->"Several concerning changes were detected in your health patterns"
            },
        scoreBreakdown = drivers.joinToString(separator = "\n"){"• $it"},
        CSIExplanation = "CSI is calculated from your heart rate, sleep,activity and changes from your personal baseline",
        recommendations = recommendations.map {
            Recommendations(
                type = when(level){
                    RiskLevel.LOW -> RecommendationType.GOOD
                    RiskLevel.MEDIUM -> RecommendationType.WARNING
                    RiskLevel.HIGH -> RecommendationType.URGENT_ALERT
                },
                title = it,
                description = "Recommended based on your latest CSI result"
            )
        }
    )
}


fun HomeData.toDashboardData(): DashboardData {
    val level=dashboard.risk_level.toRiskLevel()
    return DashboardData(
        riskScore = dashboard.csi_score,
        riskLabel = dashboard.risk_level.uppercase(),
        riskDesc = dashboard.risk_description,
        riskLevel = level,

        sleepSummary = DashboardSleepSummary(
            avgSleep = "${dashboard.sleep_hours} h",
            subtitle = "Latest sleep summary",
            days=sleep.weekly_sleep.map { it.day },
            values = sleep.weekly_sleep.map { it.hours }
        ),

        activitySummary = DashboardActivitySummary(
            activityLevel = dashboard.activity_level,
            activityChart = dashboard.activity_chart,
            progress = 0.5f
        ),

        heartRateSummary = DashboardHeartRateSummary(
            averageHr = dashboard.average_hr,
            heartRateChart = dashboard.heart_rate_chart
        ),

        recommendation = dashboard.recommendations.map{
            DashboardRecommendation(
                type=it.type.toRecommendationType(),
                title = it.title,
                description = it.description
            )
        }
    )

}




fun SleepDto.toSleepData(): SleepData {
    return SleepData(
        sleepScore = sleep_efficiency.toInt(),
        sleepDuration = "${average_sleep_hours} h",
        sleepQuality = sleep_quality.toString(),
        sleepEfficiency = "$sleep_efficiency%",
        sleepFragmentation = movement_variance.toString(),
        weekData = weekly_sleep.map {
            Day(
                day = it.day,
                hours = it.hours,
                efficiency = sleep_efficiency.toInt()
            )
        },
        recommendations = recommendations.map {
            RecommendationContent(
                type=it.type.toSleepRecommendationType(),
                title = it.title,
                description = it.description)
        }


    )
}


fun VitalsDto.toVitalsData(): VitalsData {
    return VitalsData(
        averageHR = average_hr.toInt(),
        restingHR = resting_hr.toInt(),
        peakHR = peak_hr.toInt(),
        activityLevel = activity_status.toActivityLevel(),
        HRZones = hr_zones.map {
            HRZone(
                label = it.label,
                range = it.range,
                min = it.min,
                max= it.max
            )
        },
        weeklyHR = weekly_hr.map {
            DailyHR(
                day = it.day,
                value = it.value
            )
        },

        recommendations = recommendations.map {
            VitalRecommendation(
                type=it.type.toRecommendationType(),
                title = it.title,
                description = it.description)
        }


    )}


fun String.toActivityLevel(): ActivityLevel{
    return when (uppercase()){
        "HIGH" -> ActivityLevel.HIGH
        "LOW" -> ActivityLevel.LOW
        else -> ActivityLevel.MEDIUM

    }
}



fun String.toSleepRecommendationType(): SleepRecommendationType{
    return when (uppercase()){
        "HIGH_RISK", "URGENT_ALERT" -> SleepRecommendationType.HIGH_RISK
        "MEDIUM_RISK" , "WARNING"-> SleepRecommendationType.MEDIUM_RISK
        "LOW_RISK", "GOOD" -> SleepRecommendationType.LOW_RISK
        else -> SleepRecommendationType.INFO
    }

    }



fun String.toRiskLevel(): RiskLevel{
    return when(uppercase()){
        "HIGH" -> RiskLevel.HIGH
        "MEDIUM" -> RiskLevel.MEDIUM
        else -> RiskLevel.LOW
    }
}


fun String.toRecommendationType(): RecommendationType{
    return when (uppercase()){
        "URGENT_ALERT" -> RecommendationType.URGENT_ALERT
        "WARNING" -> RecommendationType.WARNING
        "GOOD" -> RecommendationType.GOOD
        else -> RecommendationType.INFO
    }

}