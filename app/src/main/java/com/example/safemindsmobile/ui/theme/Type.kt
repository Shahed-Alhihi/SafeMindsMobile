package com.example.safemindsmobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.SafeMindsMobile

// Set of Material typography styles to start with
val  SafeMindsTypography = Typography(
    //screen title
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize=30.sp
    ),
    //section title
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize=24.sp
    ),
    //card title
    titleLarge = TextStyle(
        fontFamily= FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize=20.sp
    ),
    titleMedium = TextStyle(
        fontFamily= FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize=16.sp

    ),
    //body
    bodyLarge = TextStyle(
        fontFamily= FontFamily.SansSerif,
        fontSize=16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily= FontFamily.SansSerif,
        fontSize=14.sp
    ),

    bodySmall = TextStyle(
        fontFamily= FontFamily.SansSerif,
        fontSize=12.sp
    ),
    labelLarge = TextStyle(
        fontFamily= FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize=14.sp
    )

//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)