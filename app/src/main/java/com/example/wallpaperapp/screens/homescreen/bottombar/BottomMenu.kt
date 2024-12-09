package com.example.wallpaperapp.screens.homescreen.bottombar

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.loginscreen.viewmodel.LoginScreenViewModel
import com.example.wallpaperapp.setUserLoggedIn

@Composable
fun BottomAppMenuLayout(viewmodel: LoginScreenViewModel, goToLoginScreen:() -> Unit) {
    val errorMessage by viewmodel.errorMessage.collectAsState()
    val signedOut by viewmodel.signedOut.collectAsState()
    val loading by viewmodel.loading.collectAsState()
    val context = LocalContext.current
    val currentUser by viewmodel.currentUser.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Text(text = "Welcome $currentUser")
        Button(onClick = {
            viewmodel.clearLoginStatus()
            viewmodel.signOut()
        }) {
            Text(text = "Sign out", fontSize = 18.sp)
        }
        if(loading){
            CircularProgressIndicator()
        }
        LaunchedEffect(signedOut){
            if(signedOut == true){
                setUserLoggedIn(context, false)
                goToLoginScreen()
                viewmodel.clearLoginStatus()
            }else if(signedOut == false){
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                viewmodel.clearLoginStatus()
            }
        }
    }
}