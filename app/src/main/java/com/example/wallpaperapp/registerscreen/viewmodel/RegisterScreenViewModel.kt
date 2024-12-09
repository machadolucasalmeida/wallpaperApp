package com.example.wallpaperapp.registerscreen.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.firebaseauths.AuthResult
import com.example.wallpaperapp.firebaseauths.FirebaseAuthRepository
import com.example.wallpaperapp.firebaseauths.FirebaseAuthRepositoryImpl
import com.example.wallpaperapp.registerscreen.model.DataSourceRepositoryImpl
import com.example.wallpaperapp.registerscreen.model.RegisterScreenModel
import com.example.wallpaperapp.registerscreen.model.RegisterScreenRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private val registerScreenrepository: RegisterScreenRepository): ViewModel()
{

    private val _stateViewer = MutableStateFlow(registerScreenrepository.getData())
    val stateViewer = _stateViewer.asStateFlow()

    private val _authSignUn = FirebaseAuthRepositoryImpl()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _isRegisterSuccessful = MutableStateFlow(false)
    val isRegisterSuccessful = _isRegisterSuccessful.asStateFlow()

    fun setData(email: String, password: String, firstName: String, lastName: String, phoneNumber: String, dateOfBirth: String, error: String?){
        registerScreenrepository.setData(email, password, firstName, lastName, phoneNumber, dateOfBirth, error)
        _stateViewer.value = RegisterScreenModel(email, password, firstName, lastName, phoneNumber, dateOfBirth, error)
    }
    fun signUp(email:String, password:String, firstName: String, lastName: String, phoneNumber: String, dateOfBirth: String){
        _isRegisterSuccessful.value = false
        viewModelScope.launch{
            if(email.isNotEmpty() || password.isNotEmpty() || firstName.isNotEmpty() || lastName.isNotEmpty() || phoneNumber.isNotEmpty() || dateOfBirth.isNotEmpty()){
                when(val result = _authSignUn.signUp(email, password)){
                    is AuthResult.Success -> {
                        val auth = FirebaseAuth.getInstance()
                        val userId = auth.currentUser?.uid
                        if(userId != null){
                            val dataSourceRepository = DataSourceRepositoryImpl()
                            val name = "$firstName $lastName"
                            dataSourceRepository.addUserData(userId, name, phoneNumber, dateOfBirth)
                            _errorMessage.value = ""
                            _isRegisterSuccessful.value = true
                        }
                    }
                    is AuthResult.Error -> {
                        _errorMessage.value = result.errorMessage
                    }
                }
            }else{
                _errorMessage.value = "Please, all fields must be filled"
            }
        }
    }
    fun resetStateViewModel(){
        _errorMessage.value = ""
        _isRegisterSuccessful.value = false
        registerScreenrepository.setData("", "", "", "", "", "", "")
    }
}