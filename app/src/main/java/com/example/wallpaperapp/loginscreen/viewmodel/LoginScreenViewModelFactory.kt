package com.example.wallpaperapp.loginscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wallpaperapp.loginscreen.model.LoginScreenRepository

class LoginScreenViewModelFactory(private val _loginScreenRepository: LoginScreenRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginScreenViewModel(_loginScreenRepository) as T
    }
}