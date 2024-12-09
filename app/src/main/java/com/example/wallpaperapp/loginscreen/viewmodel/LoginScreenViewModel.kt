package com.example.wallpaperapp.loginscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.firebaseauths.AuthResult
import com.example.wallpaperapp.firebaseauths.FirebaseAuthRepository
import com.example.wallpaperapp.firebaseauths.FirebaseAuthRepositoryImpl
import com.example.wallpaperapp.loginscreen.model.LoginScreenModel
import com.example.wallpaperapp.loginscreen.model.LoginScreenRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(private val loginRepository: LoginScreenRepository): ViewModel() {
    private val _stateViewer = MutableStateFlow(loginRepository.getData())
    val stateViewer = _stateViewer.asStateFlow()

    private val authSignIn = FirebaseAuthRepositoryImpl()
    private val _loginStatus = MutableStateFlow(false)
    val loginStatus = _loginStatus.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()
    private val _signedOut = MutableStateFlow<Boolean?>(null)
    val signedOut = _signedOut.asStateFlow()
    private val _currentUser = MutableStateFlow<String?>("")
    val currentUser = _currentUser.asStateFlow()


    fun setData(email: String, password: String, rememberPassword: Boolean){
        loginRepository.setData(email, password, rememberPassword)
        _stateViewer.value = LoginScreenModel(email, password, rememberPassword)
    }

    fun login(email: String, password: String){
        _loading.value = true
        _loginStatus.value = false
        viewModelScope.launch {
            if(email.isNotEmpty() || password.isNotEmpty()){
                when(val result = authSignIn.signIn(email, password)){
                    is AuthResult.Success -> {
                        _loginStatus.value = true
                        _errorMessage.value = ""
                        _loading.value = false
                        val auth = FirebaseAuth.getInstance()
                        _currentUser.value = auth.currentUser?.email
                    }
                    is AuthResult.Error -> {
                        _errorMessage.value = result.errorMessage
                        _loading.value = false
                    }
                }
            }else{
                _errorMessage.value = "Please fill all fields"
                _loading.value = false
            }
        }
    }
    fun clearErrorMessage(){
        _errorMessage.value = ""
    }

    fun signOut(){
        _loading.value = true
        viewModelScope.launch {
            when(val result = authSignIn.signOut()){
                is AuthResult.Success -> {
                    _signedOut.value = true
                    _errorMessage.value = ""
                }
                is AuthResult.Error -> {
                    _signedOut.value = false
                    _errorMessage.value = result.errorMessage
                }
            }
        }
        _loading.value = false
    }
    fun clearLoginStatus(){
        _loginStatus.value = false
        _signedOut.value = null
    }


}