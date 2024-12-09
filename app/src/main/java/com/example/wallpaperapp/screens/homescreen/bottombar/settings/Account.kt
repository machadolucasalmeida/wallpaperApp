package com.example.wallpaperapp.screens.homescreen.bottombar.settings


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.R
import com.example.wallpaperapp.loginscreen.viewmodel.LoginScreenViewModel
import com.example.wallpaperapp.setUserLoggedIn
import okhttp3.internal.ws.RealWebSocket

@Composable
fun AccountLayout(viewmodel: LoginScreenViewModel, goToLoginScreen:() -> Unit, goToSettingsScreen:() -> Unit){
    val errorMessage by viewmodel.errorMessage.collectAsState()
    val signedOut by viewmodel.signedOut.collectAsState()
    val loading by viewmodel.loading.collectAsState()
    val context = LocalContext.current
    val currentUser by viewmodel.currentUser.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            IconButton({goToSettingsScreen()}){
                Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = "")
            }
            IconButton({}){
                Icon(imageVector = Icons.Default.Edit, contentDescription = "")
            }
        }
        Text("Account", fontSize = 42.sp)
        Row(){
            Text("Photo")
            Image(painter = painterResource(R.drawable.icon_avatar), contentDescription = "", modifier = Modifier.size(42.dp))
        }
        Row(){
            Text("Name")
            Text("Lucas Machado")
        }
        Row(){
            Text("Gender")
            Image(painter = painterResource(R.drawable.ic_male), contentDescription = "", modifier = Modifier.size(42.dp))
            Image(painter = painterResource(R.drawable.ic_female), contentDescription = "", modifier = Modifier.size(42.dp))
        }
        Row(){
            Text("Age")
            Text("21")
        }
        Row(){
            Text("Email")
            Text(currentUser.toString())
        }
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