package com.example.safemindsmobile.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    val username: String,

    @SerializedName("full_name")
    val fullName: String,
    val password: String,
    @SerializedName("age_range")
    val ageRange: String,
    val gender: String,
    val height: Float,
    val weight: Float
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val data: UserDto?
)

data class UserDto(
    val user_id: String,
    val username: String,
    val full_name: String
)
