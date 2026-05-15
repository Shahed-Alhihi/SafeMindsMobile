package com.example.safemindsmobile.data.remote.dto

data class HomeResponse(
    val success: Boolean,
    val message: String?=null,
    val data:HomeData?
)


data class HomeData(
    val dashboard:DashboardDTO,
    val sleep:SleepDto,
    val vitals:VitalsDto,
    val activity: ActivityDto

)

data class DashboardDTO(
    val user_name: String?=null,
    val csi_score: Int,
    val risk_level: String,
    val risk_description: String,
    val sleep_hours: Float,
    val average_hr: Float,
    val heart_rate_chart:List<Float> =emptyList(),

    val activity_level: Float,
    val activity_chart:List<Float> =emptyList(),
    //val steps: Int,
    val recommendations:List<DashboardRecommendationDTO>)



data class DashboardRecommendationDTO(
    val type: String,
    val title: String,
    val description: String
)


data class SleepDto(
    val average_sleep_hours: Float,
    val sleep_efficiency: Float,
    val sleep_quality: String,
    val sleep_variability: Float,
    val movement_mean: Float,
    val movement_variance: Float,
    val total_epochs: Int,
    val weekly_sleep: List<WeeklySleepDto>,
    val recommendations: List<RecommendationDto>
)

data class WeeklySleepDto(
    val day: String,
    val hours: Float
)

data class RecommendationDto(
    val type: String,
    val title: String,
    val description: String
)


data class VitalsDto(
    val average_hr: Float,
    val resting_hr: Float,
    val peak_hr: Float,
    val activity_status: String,
    val weekly_hr: List<WeeklyHrDto>,
    val hr_zones: List<HrZoneDto>,
    val recommendations: List<RecommendationDto>
)


data class WeeklyHrDto(
    val day: String,
    val value: Float
)

data class HrZoneDto(
    val label: String,
    val range: String,
    val min: Int,
    val max: Int
)



data class ActivityDto(
 //   val steps:Int,
   // val activity_level: String,
   // val movement_mean: Float,
    val activity_value: Float,
    val movement_variance: Float,
    val weekly_activity: List<WeeklyActivityDto>,
  //  val weekly_steps: List<WeeklyStepsDto>
)



data class WeeklyActivityDto(
    val day: String,
    val value: Float
)