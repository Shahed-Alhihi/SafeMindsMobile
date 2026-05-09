package com.example.safemindsmobile.data.mock

import com.example.safemindsmobile.data.model.ActivityLevel
import com.example.safemindsmobile.data.model.DailyHR
import com.example.safemindsmobile.data.model.HRZone
import com.example.safemindsmobile.data.model.RecommendationType
import com.example.safemindsmobile.data.model.VitalRecommendation
import com.example.safemindsmobile.data.model.VitalsData

val MockVitalData= VitalsData(
    averageHR = 72,
    restingHR = 58,
    peakHR = 118,
    activityLevel = ActivityLevel.MEDIUM,
    HRZones = listOf(
        HRZone("Resting", "<60", 38, 120),
        HRZone("Fast burn", "60-100", 52, 120),
        HRZone("Cardio", "100-140", 22, 120),
        HRZone("Peak", ">140", 4, 120)
    ),
    weeklyHR = listOf(
        DailyHR("Sun", 0.60f),
        DailyHR("Mon", 0.72f),
        DailyHR("Tue", 0.55f),
        DailyHR("Wed", 0.85f),
        DailyHR("Thu", 0.62f),
        DailyHR("Fri", 0.68f),
        DailyHR("Sat", 0.78f)
    ),

    recommendations = listOf(
        VitalRecommendation(
            RecommendationType.GOOD,
            "Resting HR is healthy",
            "58 bpm is the optimal range of your profile"
        ),

        VitalRecommendation(
            RecommendationType.WARNING,
            "Thursday peak spike",
            "HR hit 118 bpm at 3 PM, consider a short break"
        ),

        VitalRecommendation(
            RecommendationType.INFO,
            "Increase cardio time",
            "Only 22 min in cardio zone, consider increasing cardio time"
        )
    ),
)

