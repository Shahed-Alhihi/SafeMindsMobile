package com.example.safemindsmobile.data.remote

data class SignupRequest(
    val username: String,
    val fullName: String,
    val password: String,
    val ageRange: String,
    val gender: String
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
