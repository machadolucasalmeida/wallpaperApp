package com.example.wallpaperapp.registerscreen.model

data class RegisterScreenModel(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val error: String? = null
)
