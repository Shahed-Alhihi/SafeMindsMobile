package com.example.safemindsmobile.data.mapper

import com.example.safemindsmobile.data.model.*
import com.example.safemindsmobile.data.remote.CsiResultDto

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
