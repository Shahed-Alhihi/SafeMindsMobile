package com.example.safemindsmobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val  SafeMindsTypography = Typography(
    //screen title
    headlineLarge = TextStyle(
       // fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize=30.sp,
        lineHeight = 36.sp
    ),
    //section title
    headlineMedium = TextStyle(
     //   fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize=24.sp,
        lineHeight = 30.sp
    ),
    //card title
    titleLarge = TextStyle(
       // fontFamily= FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize=20.sp
    ),
    titleMedium = TextStyle(
       // fontFamily= FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize=16.sp

    ),
    //body
    bodyLarge = TextStyle(
      //  fontFamily= FontFamily.SansSerif,
        fontSize=16.sp,
        lineHeight = 24.sp

    ),
    bodyMedium = TextStyle(
   //     fontFamily= FontFamily.SansSerif,
        fontSize=14.sp,
        lineHeight = 20.sp
    ),

    bodySmall = TextStyle(
     //   fontFamily= FontFamily.SansSerif,
        fontSize=12.sp
    ),
    labelLarge = TextStyle(
     //   fontFamily= FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize=14.sp
    )

)