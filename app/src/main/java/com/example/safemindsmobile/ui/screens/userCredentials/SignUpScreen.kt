package com.example.safemindsmobile.ui.screens.userCredentials

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.buttons.SafeMindsPrimaryButtons
import com.example.safemindsmobile.ui.theme.Spaces
import com.example.safemindsmobile.ui.theme.primaryColor

@Composable
fun SignUpScreen(navController: NavHostController) {
    var fullName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var ageRange by remember { mutableStateOf<String?>(null) }
    var gender by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Spaces.spaceXL, vertical = Spaces.spaceXL)
    )
    {
        Column(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spaces.spaceL)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spaces.spaceXS)
            ) {
                Text(
                    text = "SAFEMINDS",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = primaryColor.copy(alpha = 0.55f),
                        letterSpacing = 4.sp,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                )

                Spacer(modifier = Modifier.height(Spaces.spaceXS))

                Text(
                    text = "Create a new account",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Tell us more about yourself",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }


            textField(
                value = userName,
                changedValue = { userName = it },
                label = "Username",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.VerifiedUser,
                        contentDescription = null,
                        tint = primaryColor.copy(alpha = 0.6f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            )

            textField(
                value = fullName,
                changedValue = { fullName = it },
                label = "Full Name",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = primaryColor.copy(alpha = 0.6f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            )


            textField(
                value = password,
                changedValue = { password = it },
                label = "Password",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = primaryColor.copy(alpha = 0.6f),
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            imageVector = if (passwordVisibility) Icons.Outlined.Visibility
                            else Icons.Outlined.VisibilityOff,
                            contentDescription = if (passwordVisibility) "Hide Password"
                            else "Show Password",
                            tint = primaryColor.copy(alpha = 0.4f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },

                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password
            )



            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Spaces.spaceS)
            ) {
                Text(
                    text = "Age Range",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),

                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp

                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spaces.spaceXS)
                ) {
                    listOf("18-29", "30-44", "45-59", "60-74", "75+").forEach { option ->
                        options(
                            label = option,
                            isSelected = ageRange == option,
                            onClick = {
                                ageRange = option

                            },
                            modifier = Modifier.weight(1f)
                        )

                    }
                }
            }



            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spaces.spaceS)
            ) {
                Text(
                    text = "Gender",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spaces.spaceXS)
                )
                {
                    listOf("Female", "Male").forEach { option ->
                        options(
                            label = option,
                            isSelected = gender == option,
                            onClick = {
                                gender = option
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spaces.spaceS)
            ) {
                Spacer(modifier = Modifier.height(Spaces.spaceXS))

                SafeMindsPrimaryButtons(
                    label = "Create account",
                    onClick = {
                        navController.navigate(AppScreens.LoginScreen.flow)
                    }
                )

                Spacer(modifier = Modifier.height(Spaces.spaceL))
            }
        }

    }
}

@Composable
private fun textField(
    value: String,
    changedValue: (String) -> Unit,
    label: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = changedValue,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
            focusedLabelColor = primaryColor,
            cursorColor = primaryColor,
            focusedContainerColor = primaryColor.copy(alpha = 0.04f),
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
private fun options(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (isSelected) primaryColor
                else MaterialTheme.colorScheme.surface
            )
            .border(
                width = 1.dp,
                color = if (isSelected) primaryColor
                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.18f),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable{onClick()}
            .padding(horizontal = 4.dp)
    )
    {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.SemiBold
                else
                    FontWeight.Normal,
                color = if (isSelected) primaryColor else
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                fontSize = 11.sp
            ),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}
