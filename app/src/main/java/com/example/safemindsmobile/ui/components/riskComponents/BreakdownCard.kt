package com.example.safemindsmobile.ui.components.riskComponents

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.ui.components.screensComponents.SafeMindsCard

@Composable
fun BreakdownCard (
    text: String){
    SafeMindsCard {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 22.sp
        )
    }

}
