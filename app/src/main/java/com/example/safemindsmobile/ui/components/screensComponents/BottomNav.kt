package com.example.safemindsmobile.ui.components.screensComponents

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.theme.secondaryColor

@Composable
fun BottomNav(
    controller: NavController,
    appScreens: List<AppScreens>
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        modifier = Modifier.height(72.dp)
    ) {
        val currentStack by controller.currentBackStackEntryAsState()
        val currentScreen = currentStack?.destination?.route
        //these are used to get the current selected screen

        appScreens.forEach { screen ->
            val selected = currentScreen == screen.flow

            NavigationBarItem(
                selected = selected, //selected means is this tab is selected if it is in the current tab
                //currentScreen: the name of the opened screen right-now
                //this is used to know if the opened screen is the same as the selected screen
                onClick = {
                    controller.navigate(screen.flow) { //when the user click it, it retrieves them to the correct screen
                        //this creates new screen above the prev one in the stack
                        popUpTo(controller.graph.startDestinationId) { //this is used if we open redundant screens (dashboard->vitals->dashboard), instead of adding them in a redundant way in the stack, it returns to the first one (back) and delete what above it
                            saveState =
                                true //save the screen's state before I leave the screen
                        }
                        launchSingleTop =
                            true //if the screen is already opened don't make from it new instance
                        restoreState =
                            true //return the screen's state when I come back again
                    }

                },
                icon = {
                    NavBarIcon(
                        icon = screen.navIcon(),
                        selected = selected
                    )

                },
                label = {
                    Text(
                        text = screen.navLabel(),
                        style = MaterialTheme.typography.bodySmall
                    )

                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}


@Composable
private fun NavBarIcon(
    icon: ImageVector, selected: Boolean) {
    val bubble by animateDpAsState(
        targetValue = if (selected)56.dp else 40.dp,
        animationSpec = tween(300, easing = FastOutSlowInEasing),
        label = "bubble"
    )

    Box(
        modifier = Modifier
            .width(bubble)
            .height(32.dp)
            .clip(RoundedCornerShape(50))

            .background(
                if (selected) secondaryColor.copy(alpha = 0.25f)
                else MaterialTheme.colorScheme.surface
            ),
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(22.dp),
            tint = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant
        )

    }
}



private fun AppScreens.navLabel(): String=when (this){

    AppScreens.DashboardScreen ->"dashboard"
    AppScreens.SleepPatternsScreen ->"sleep"
    AppScreens.VitalsAnalysisScreen ->"vitals"
    AppScreens.RiskAnalysisScreen ->"risk"
    else ->""

}



private fun AppScreens.navIcon(): ImageVector =when (this){
    AppScreens.DashboardScreen -> Icons.Outlined.Dashboard
    AppScreens.SleepPatternsScreen -> Icons.Outlined.Bedtime
    AppScreens.VitalsAnalysisScreen -> Icons.Outlined.MonitorHeart
    AppScreens.RiskAnalysisScreen -> Icons.Outlined.Psychology
    else -> Icons.Outlined.Dashboard

}

