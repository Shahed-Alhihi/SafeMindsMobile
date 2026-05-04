package com.example.safemindsmobile.data.model



data class RiskData (
    val riskScore:Int,
    val riskLevel:RiskLevel,
    val desc: String,
    val scoreBreakdown: String,
    val CSIExplanation:String,
    val recommendations:List<Recommendations>

    )


enum class RiskLevel{
    LOW,
    MEDIUM,
    HIGH
}

data class Recommendations(
    val type:RecommendationType,
    val title:String,
    val description:String
)

enum class RecommendationType{
    URGENT_ALERT,
    INFO,
    WARNING,
    GOOD
}