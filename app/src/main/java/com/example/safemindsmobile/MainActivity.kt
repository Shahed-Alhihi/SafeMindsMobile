package com.example.safemindsmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.safemindsmobile.ui.theme.SafeMindsMobileTheme
import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.example.safemindsmobile.navigation.AppNavFlow
import com.example.safemindsmobile.utils.NotificationHelper

class MainActivity : ComponentActivity() {
    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){}

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationHelper.createNotificationChannel(this)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }


        enableEdgeToEdge()
        setContent {
            SafeMindsMobileTheme {
                SafeMindsMobile()

            }

        }
    }


    @Composable
    fun SafeMindsMobile(){
       val navController=rememberNavController()

        AppNavFlow(
            controller = navController,
            isInitialLaunch = true
        )

    }

}


