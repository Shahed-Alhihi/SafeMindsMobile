package com.example.safemindsmobile.ui.components.screensComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.safemindsmobile.ui.theme.Spaces


@Composable
fun SafeMindsCard(
    modifier: Modifier=Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Spaces.spaceXS
        )
    ) {
        Column(
            modifier = Modifier.padding(Spaces.spaceM),
            content = content
        )
    }
}


@Composable
fun ChartCards(
    label: String,
    modifier: Modifier=Modifier,
    content: @Composable ColumnScope.() -> Unit
){
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Spaces.spaceXS
        )
    ) {
        Column(
            modifier = Modifier.padding(Spaces.spaceM)
        ){
            Text(
                text=label,
                style= MaterialTheme.typography.titleLarge,
                color= MaterialTheme.colorScheme.onSurface
            )
            Spacer(
                modifier= Modifier.height(Spaces.spaceS)
            )
            content()
    }
}

}
