package com.example.safemindsmobile.ui.Screens.userCredentials

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.safemindsmobile.R
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.Buttons.SafeMindsPrimaryButtons
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.secondaryColor

@Composable
fun LoginScreen (navController: NavController) {
var userName by remember {
    mutableStateOf("")
}
    var password by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        screenBackground()

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = Spaces.spaceXL)
                .padding(vertical = Spaces.spaceXL)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id=R.drawable.logo),
                contentDescription = "SafeMinds logo",
                modifier = Modifier.size(220.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(Spaces.spaceM))

            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineMedium,
                color= MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(Spaces.spaceS))

            Text(
                text="Login to continue to SafeMinds",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(Spaces.spaceXL))


            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                        shape = MaterialTheme.shapes.extraLarge
                    )

                    .padding(Spaces.spaceL),
                verticalArrangement = Arrangement.spacedBy(Spaces.spaceM)
            ) {
                OutlinedTextField(
                    value = userName,
                    onValueChange = {userName=it},
                    modifier = Modifier.fillMaxWidth(),
                    label = {Text("UserName")},
                    leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                    singleLine = true,
                    shape= MaterialTheme.shapes.extraLarge,
                    colors= OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )

                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {password=it},
                    modifier = Modifier.fillMaxWidth(),
                    label = {Text("Password")},
                    leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    shape= MaterialTheme.shapes.extraLarge,
                    colors= OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )

                )

                Spacer(Modifier.height(Spaces.spaceXS))

                SafeMindsPrimaryButtons(
                    label = "Login",
                    onClick = {
                        navController.navigate(AppScreens.PairWithWatch.flow) {
                            popUpTo(AppScreens.LoginScreen.flow) {
                                inclusive = true
                            }
                        }
                    }

                )
            }

        }
    }

}

@Composable
private fun screenBackground(){
    Box(Modifier.fillMaxSize()){
        Box(
            Modifier.padding(top = 80.dp, start = Spaces.spaceL)
                .height(180.dp)
                .fillMaxWidth(0.45f)
                .alpha(0.12f)
                .background(secondaryColor, CircleShape)

        )

        Box(
            Modifier.padding(top = 420.dp, start = 220.dp)
                .height(220.dp)
                .fillMaxWidth(0.35f)
                .alpha(0.10f)
                .background(primaryColor,CircleShape)
        )
    }
}