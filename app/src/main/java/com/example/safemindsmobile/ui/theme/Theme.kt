package com.example.safemindsmobile.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
private val LightColors = lightColorScheme(
    primary = primaryColor,
    onPrimary = onPrimaryColor,

    secondary = secondaryColor,
    onSecondary = onSurfaceColor,


    background = backgroundColor,
    onBackground = onBackgroundColor,


    surface = surfaceColor,
    onSurface = onSurfaceColor,


    surfaceVariant = surfaceVariantColor,
    onSurfaceVariant = onSurfaceVariantColor,

    //state
    error=errorColor,
    onError = onPrimaryColor
)

private val SafeMindsShapes= Shapes(
    small= RoundedCornerShape(Radius.small),
    medium= RoundedCornerShape(Radius.medium),
    large= RoundedCornerShape(Radius.large),
    extraLarge=RoundedCornerShape(Radius.xlarge)
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