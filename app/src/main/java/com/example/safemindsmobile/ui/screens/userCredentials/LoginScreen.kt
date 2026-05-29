package com.example.safemindsmobile.ui.screens.userCredentials

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.safemindsmobile.R
import com.example.safemindsmobile.data.local.UserSessionManager
import com.example.safemindsmobile.data.repository.SafeMindsRep
import com.example.safemindsmobile.data.session.UserSession
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.screensComponents.SafeMindsPrimaryButtons
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.primaryColor
import com.example.safemindsmobile.ui.theme.secondaryColor
import kotlinx.coroutines.launch

@Composable
fun LoginScreen (navController: NavController) {
var userName by remember {
    mutableStateOf("")
}
    var password by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    val scope= rememberCoroutineScope()
    val rep=remember { SafeMindsRep() }

    val context = LocalContext.current
    val userSessionManager = remember {
        UserSessionManager(context)
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
                    onValueChange = {
                        userName=it
                        errorMessage=null
                                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {Text("UserName")},
                    leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                    singleLine = true,
                    shape= MaterialTheme.shapes.extraLarge,
                    enabled = !isLoading,
                    colors= OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )

                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {password=it
                        errorMessage=null
                                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {Text("Password")},
                    leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    shape= MaterialTheme.shapes.extraLarge,
                    enabled = !isLoading,
                    colors= OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )

                )
                errorMessage?.let {
                    Text(
                        text=it,
                        color = MaterialTheme.colorScheme.error,
                        style= MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(Spaces.spaceXS))

                if (isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else{
                    SafeMindsPrimaryButtons(
                        label = "Login",
                        onClick = {
                            if (userName.isBlank()|| password.isBlank()){
                                errorMessage="Please fill all fields"
                                return@SafeMindsPrimaryButtons

                            }

                            scope.launch {
                                isLoading=true
                                errorMessage=null

                                try {
                                    val response = rep.login(
                                        username = userName.trim(),
                                        password = password
                                    )

                                    if (response.success && response.data != null) {
                                        UserSession.userId = response.data.user_id
                                        UserSession.username = response.data.username
                                        UserSession.fullName = response.data.full_name

                                        userSessionManager.saveUserId(response.data.user_id)

                                        navController.navigate(AppScreens.PairWithWatch.flow) {
                                            popUpTo(AppScreens.LoginScreen.flow) {
                                                inclusive = true
                                            }
                                        }
                                    } else {
                                        errorMessage = response.message
                                    }
                            }
                                catch (e:Exception){
                                        errorMessage="Could not connect to the server"
                                    }
                                finally {
                                    isLoading=false
                                }


                }



                    }

                )
            }

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