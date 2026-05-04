package com.example.safemindsmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.safemindsmobile.navigation.AppNavFlow
import com.example.safemindsmobile.ui.theme.SafeMindsMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafeMindsMobileTheme {

                val navController= rememberNavController()
                AppNavFlow(controller =navController)

            }
        }
    }
}


