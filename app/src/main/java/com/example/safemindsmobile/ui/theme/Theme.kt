package com.example.safemindsmobile.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MovableContent
import androidx.compose.ui.platform.LocalContext
private val LightColors = lightColorScheme(
    primary = primaryColor,
    secondary = secondaryColor,
    tertiary = primaryHelper,
    onSecondaryContainer = secondaryHelper,

    background = backgroundColor,
    surface = backgroundHelper,
    surfaceVariant = SurfaceVariant,


    onPrimary = textPrimary,
    onSecondary = textSecondary,
    error=errorColor

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val SafeMindsShapes= Shapes(
    small= RoundedCornerShape(Spaces.smallAngil),
    medium= RoundedCornerShape(Spaces.MedAngil),
    large= RoundedCornerShape(Spaces.LargeAngil),
    extraLarge=RoundedCornerShape(Spaces.XLAngil)
)

@Composable
fun SafeMindsMobileTheme(
  content: @Composable ()-> Unit
){

    MaterialTheme(
        colorScheme = LightColors,
        typography = SafeMindsTypography,
        shapes = SafeMindsShapes,
        content=content
    )
}