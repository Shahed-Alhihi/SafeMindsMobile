package com.example.safemindsmobile.data.mock

import com.example.safemindsmobile.data.model.RecommendationType
import com.example.safemindsmobile.data.model.Recommendations
import com.example.safemindsmobile.data.model.RiskData
import com.example.safemindsmobile.data.model.RiskLevel


val MockRiskData = RiskData(
    riskScore = 72,
    riskLevel = RiskLevel.MEDIUM,
    desc = "Your wellness indicators show a moderate stress risk...",
    scoreBreakdown = "Your score of 72 reflects moderate risk...",
    CSIExplanation = "CSI is calculated daily...",
    recommendations = listOf(
        Recommendations(
            RecommendationType.WARNING,
            "Moderate risk detected",
            "Your CSI score is in the medium risk range."
        )
    )
)