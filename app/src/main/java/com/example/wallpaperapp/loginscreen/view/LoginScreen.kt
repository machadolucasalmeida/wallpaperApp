package com.example.wallpaperapp.loginscreen.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.R
import com.example.wallpaperapp.loginscreen.model.LoginScreenRepositoryImpl
import com.example.wallpaperapp.loginscreen.viewmodel.LoginScreenViewModel
import com.example.wallpaperapp.setUserLoggedIn
import com.example.wallpaperapp.ui.theme.darkBackgroundSecondary
import com.example.wallpaperapp.ui.theme.facebookColor
import com.example.wallpaperapp.ui.theme.lightBackgroundSecondary

@Composable
fun LoginScreen(
    goToRegisterScreen: () -> Unit,
    forgotPassword: () -> Unit,
    goToHomeScreen:() -> Unit,
    viewModel: LoginScreenViewModel
) {
    val isDarkTheme = isSystemInDarkTheme()
    val logo = if (isDarkTheme) R.drawable.ic_splashscreen_dark else R.drawable.ic_splashscreen_light
    val fontColor = if(isDarkTheme) Color.White else Color.Black
    val robotoFamily = FontFamily(Font(R.font.roboto_regular))
    val righteousFamily = FontFamily(Font(R.font.righteous_regular))
    val poppinsFamily = FontFamily(Font(R.font.poppins_regular))


    val state by viewModel.stateViewer.collectAsState()
    val email = rememberSaveable{mutableStateOf(state.email)}
    val password = rememberSaveable{mutableStateOf(state.password)}
    val errorMessage by viewModel.errorMessage.collectAsState()
    val loginStatus by viewModel.loginStatus.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val context = LocalContext.current



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 26.dp, end = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){





            // Logo + Title
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(120.dp)
                )
                Text(text = "ArtWall", fontSize = 36.sp, fontFamily = righteousFamily)
            }

            // Login fields and buttons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
                ){
                OutlinedTextField(
                    value = email.value,
                    onValueChange = {email.value = it},
                    label = { Text(text = "E-mail")},
                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = password.value,
                    onValueChange = {password.value = it},
                    label = { Text(text = "Password")},
                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()){
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Checkbox(
                            checked = state.rememberPassword,
                            onCheckedChange = {
                                viewModel.setData(
                                    email = state.email,
                                    password = state.password,
                                    rememberPassword = !state.rememberPassword
                                )
                            },
                            )
                        Text(
                            text = "Remember Password",
                            fontFamily = robotoFamily)
                    }
                    Text(
                        text = "Forgot Password?",
                        fontFamily = robotoFamily,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {forgotPassword()},
                        textAlign = TextAlign.End)
                }
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = {
                        viewModel.setData(email = email.value, password = password.value, rememberPassword = state.rememberPassword)
                        viewModel.login(email.value, password.value)
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(46.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Login", fontSize = 18.sp, fontFamily = robotoFamily, color = Color.White)
                }
                if(errorMessage != ""){
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    viewModel.clearErrorMessage()
                }
                if(loading){
                    CircularProgressIndicator()
                }
                LaunchedEffect(loginStatus){
                    if(loginStatus){
                        goToHomeScreen()
                        setUserLoggedIn(context, true)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {goToRegisterScreen()},
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(46.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Register",
                        fontSize = 18.sp,
                        fontFamily = robotoFamily,
                        color = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(modifier = Modifier.height(42.dp))
            // Divider line third parties login
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    Divider(modifier = Modifier.width(130.dp), color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "OR", fontFamily = robotoFamily, color = fontColor)
                    Spacer(modifier = Modifier.width(20.dp))
                    Divider(modifier = Modifier.width(130.dp), color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(42.dp))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(46.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = facebookColor)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically){
                        //TODO: Add icon
                        Image(
                            painter = painterResource(id = R.drawable.facebook_icon),
                            contentDescription = "Facebook Icon")
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = "Sign up with Facebook", fontSize = 16.sp, fontFamily = robotoFamily, color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(46.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.buttonColors(containerColor = if(isDarkTheme) darkBackgroundSecondary else lightBackgroundSecondary)) {
                    Row(verticalAlignment = Alignment.CenterVertically){
                        //TODO: Add icon
                        Image(painter = painterResource(id = R.drawable.google_icon), contentDescription = "")
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = "Sign up with Google", color = fontColor)
                        println("Teste")
                    }
                }
            }
        }

}

@Preview
@Composable
fun LoginScreenPreview() {
    val viewModel = LoginScreenViewModel(loginRepository = LoginScreenRepositoryImpl())
    LoginScreen({}, {}, {}, viewModel)
}