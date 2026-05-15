package com.example.safemindsmobile.data.model


data class SleepData (
    val sleepScore: Int,
    val sleepDuration: String,
    val sleepQuality: String,
    val sleepEfficiency: String,
    val sleepFragmentation: String,
    val weekData: List<Day>,
    val recommendations: List<RecommendationContent>

)

data class Day(
    val day: String,
    val hours:Float,
    val efficiency: Int,
    val maxHours: Float=9f
)

enum class SleepRecommendationType{
    LOW_RISK,
    MEDIUM_RISK,
    HIGH_RISK,
    INFO
}

data class RecommendationContent(
    val type:SleepRecommendationType,
    val title: String,
    val description: String
)