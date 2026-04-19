package com.example.safemindsmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.safemindsmobile.data.receiver.WearMessageManager
import com.example.safemindsmobile.ui.theme.SafeMindsMobileTheme

class MainActivity : ComponentActivity() {
    private lateinit var wearMessageManager: WearMessageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        wearMessageManager = WearMessageManager(this)

        setContent {
            SafeMindsMobileTheme {
                SafeMindsMobile()

            }
        }
    }

    override fun onStart() {
        super.onStart()
        wearMessageManager.start()
    }

    override fun onStop() {
        super.onStop()
        wearMessageManager.stop()
    }





    @Composable
    fun SafeMindsMobile(){
        Text("Safe Minds")
    }

}


