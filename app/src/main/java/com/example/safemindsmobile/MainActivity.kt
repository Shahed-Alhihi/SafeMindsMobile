package com.example.safemindsmobile

import android.os.Bundle
import android.util.Log
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
import com.example.safemindsmobile.ui.theme.SafeMindsMobileTheme
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Wearable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SafeMindsMobileTheme {
                SafeMindsMobile()

            }
        }
        Wearable.getCapabilityClient(this)
            .getAllCapabilities(CapabilityClient.FILTER_ALL)
            .addOnSuccessListener { capabilities ->
                capabilities.forEach { (name, info) ->
                    Log.d("CAP_TEST", "Capability: $name, nodes=${info.nodes.size}")
                }
            }
    }

    @Composable
    fun SafeMindsMobile(){
        Text("Safe Minds")
    }

}


