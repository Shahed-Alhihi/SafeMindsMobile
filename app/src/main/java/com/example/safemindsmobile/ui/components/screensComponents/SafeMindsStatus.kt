package com.example.safemindsmobile.ui.components.screensComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.safemindsmobile.data.model.RiskLevel
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.highRiskColor
import com.example.safemindsmobile.ui.theme.lowRiskColor
import com.example.safemindsmobile.ui.theme.medRiskColor
import com.example.safemindsmobile.ui.theme.successColor
import com.example.safemindsmobile.ui.theme.warningColor


enum class SafeMindsStatus {

    SYNC,
    NOT_SYNCED

}


@Composable
fun StatusIndicator(
    status: SafeMindsStatus,
){
    val color=when(status){
        SafeMindsStatus.SYNC -> successColor
        SafeMindsStatus.NOT_SYNCED -> warningColor
    }

    val text=when(status){
        SafeMindsStatus.SYNC -> "Synced"
        SafeMindsStatus.NOT_SYNCED -> "Not synced"}

    Row(
        modifier=Modifier
            .background(
                color.copy(alpha = 0.15f), RoundedCornerShape(50))
            .padding(horizontal = Spaces.spaceM, vertical = Spaces.spaceS),
        verticalAlignment = Alignment.CenterVertically

    ){
        Box(
            modifier = Modifier.size(8.dp)
                .background(color, CircleShape)
        )

        Spacer(modifier = Modifier.width(Spaces.spaceS))
        Text(
            text=text,
            style= MaterialTheme.typography.labelMedium,
            color= color
        )
    }

}

@Composable
fun StatusContainer(
    label: String,
    status: RiskLevel,
  modifier: Modifier=Modifier

){
    val color=when(status){
        RiskLevel.LOW -> lowRiskColor
        RiskLevel.MEDIUM -> medRiskColor
        RiskLevel.HIGH -> highRiskColor
    }


    Row(
        modifier=modifier
            .background(
                color , RoundedCornerShape(50))
            .padding(horizontal = Spaces.spaceM, vertical = Spaces.spaceS)

    )
 {
        Text(
            text=label,
            style= MaterialTheme.typography.labelLarge,
            color= MaterialTheme.colorScheme.onPrimary
        )
    }

}


@Composable
fun SectionHeader(
    label:String,
    modifier: Modifier=Modifier,
    action:String? =null,
    click:(()-> Unit)?=null

    ){
    Row(
        modifier=modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text=label,
            style= MaterialTheme.typography.headlineMedium,
            color= MaterialTheme.colorScheme.onBackground
        )
        if (action !=null && click !=null){
            Text(
                text=action,
                style= MaterialTheme.typography.labelLarge,
                color= MaterialTheme.colorScheme.primary,
                modifier= Modifier.clickable{click()}
            )
        }
    }

}