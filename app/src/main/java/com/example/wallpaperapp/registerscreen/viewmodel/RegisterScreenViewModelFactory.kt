package com.example.wallpaperapp.registerscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wallpaperapp.registerscreen.model.RegisterScreenRepository

class RegisterScreenViewModelFactory(private val _registerScreenRepository: RegisterScreenRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterScreenViewModel(_registerScreenRepository) as T
        }

}