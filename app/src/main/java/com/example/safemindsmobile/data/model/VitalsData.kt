package com.example.safemindsmobile.data.model

data class VitalsData (
    val averageHR:Int,
    val restingHR:Int,
    val peakHR:Int,
    val activityLevel:ActivityLevel,
    val HRZones:List<HRZone>,
    val weeklyHR:List<DailyHR>,
    val recommendations:List<VitalRecommendation>
    )

    enum class ActivityLevel{
        LOW,
        MEDIUM,
        HIGH
    }

    data class HRZone(
        val label:String,
        val range: String,
        val min:Int,
        val max: Int
    )


    data class DailyHR(
        val day:String,
        val value: Float
    )

    data class VitalRecommendation(
        val type : RecommendationType,
        val title:String,
        val description:String
    )
