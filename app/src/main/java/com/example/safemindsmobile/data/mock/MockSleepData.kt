package com.example.safemindsmobile.data.mock

import com.example.safemindsmobile.data.model.Day
import com.example.safemindsmobile.data.model.RecommendationContent
import com.example.safemindsmobile.data.model.SleepData
import com.example.safemindsmobile.data.model.SleepRecommendationType


val MockSleepData = SleepData(
    sleepScore = 82,
    sleepDuration = "6.8h",
    sleepQuality = "Good",
    sleepEfficiency = "86%",
    sleepFragmentation = "Low",
    weekData = listOf(
        Day("Sun", 6.2f, 80),
        Day("Mon", 7.1f, 88),
        Day("Tue", 5.9f, 72),
        Day("Wed", 6.8f, 84),
        Day("Thu", 7.4f, 90),
        Day("Fri", 6.5f, 78),
        Day("Sat", 8.0f, 92)
    ),
    recommendations = listOf(
        RecommendationContent(
            SleepRecommendationType.LOW_RISK,
            "Keep consistent bedtime",
            "Your sleep routine is improving this week."
        )
    )
)