package com.example.wallpaperapp.firebaseauths

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.selects.whileSelect
import kotlinx.coroutines.tasks.await
// Try to implement this in a viewmodel file
// Convert this class to an interface

sealed class AuthResult{
    object Success: AuthResult()
    data class Error(val errorMessage: String): AuthResult()
}

interface FirebaseAuthRepository {
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signUp(email: String, password: String): AuthResult
    suspend fun signOut(): AuthResult
}

class FirebaseAuthRepositoryImpl: FirebaseAuthRepository{
    private var auth = Firebase.auth

    private fun getError(e: Exception): String{
        return when(e){
            is FirebaseAuthInvalidCredentialsException -> "Incorrect email or password. Please try again."
            is FirebaseAuthInvalidUserException -> "User does not exist. Please check your email."
            else -> "Log in failed. Please try again."
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult {
        return try{
            auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success
        }catch (e: Exception){
            Log.w(TAG, "signInWithEmail:failure", e)
            AuthResult.Error(getError(e))
        }
    }

    override suspend fun signUp(email: String, password: String): AuthResult {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Failed sign up")
        }
    }
    override suspend fun signOut(): AuthResult{
       return try{
           auth.signOut()
           AuthResult.Success
       }catch (e: Exception){
           AuthResult.Error(e.message ?: "Failed sign out")
       }
    }
}