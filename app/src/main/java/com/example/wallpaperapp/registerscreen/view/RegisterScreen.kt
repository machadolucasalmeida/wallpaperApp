package com.example.wallpaperapp.registerscreen.view

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.R
import com.example.wallpaperapp.firebaseauths.FirebaseAuthRepository
import com.example.wallpaperapp.registerscreen.model.RegisterScreenModel
import com.example.wallpaperapp.registerscreen.model.RegisterScreenRepositoryImpl
import com.example.wallpaperapp.registerscreen.viewmodel.RegisterScreenViewModel
import com.example.wallpaperapp.ui.theme.WhiteGray
import com.example.wallpaperapp.ui.theme.darkBackgroundSecondary
import com.example.wallpaperapp.ui.theme.facebookColor
import com.example.wallpaperapp.ui.theme.lightBackgroundSecondary
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navigateToRegisterScreenStep2: () -> Unit,
    viewModel: RegisterScreenViewModel
){
    val isDarkTheme = isSystemInDarkTheme()
    val robotoFamily = FontFamily(Font(R.font.roboto_regular))
    val righteousFamily = FontFamily(Font(R.font.righteous_regular))
    val poppinsFamily = FontFamily(Font(R.font.poppins_regular))
    val fontColor = if(isDarkTheme) Color.White else Color.Black

    val state by viewModel.stateViewer.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val email = remember{mutableStateOf("")}
    val emailVerification = remember{mutableStateOf("")}
    val password = remember{mutableStateOf("")}
    val passwordVerification = remember{mutableStateOf("")}

    Scaffold(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(start = 26.dp, end = 26.dp, top = 16.dp, bottom = 16.dp)){
            Column {
                AnimatedVisibility(visible = state.error != null) {
                    state.error?.let {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red)){
                            Text(text = it, color = Color.White, modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                    Divider(modifier = Modifier
                        .width(148.dp)
                        .height(6.dp), color = MaterialTheme.colorScheme.primary)
                    Divider(modifier = Modifier
                        .width(148.dp)
                        .height(6.dp), color = WhiteGray)
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Create an Account", fontFamily = righteousFamily, fontSize = 26.sp, textAlign = TextAlign.Center)
                Text(text = "Create your account to have access to all wallpapers available", fontFamily = robotoFamily, fontSize = 20.sp, textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = email.value,
                    label = { Text(text = "E-mail")},
                    onValueChange = {email.value = it},
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                OutlinedTextField(
                    value = emailVerification.value,
                    label = { Text(text = "Confirm E-mail")},
                    onValueChange = {emailVerification.value = it},
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                OutlinedTextField(
                    value = password.value,
                    label = { Text(text = "Password")},
                    onValueChange = {password.value = it},
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                OutlinedTextField(
                    value = passwordVerification.value,
                    label = { Text(text = "Confirm Password")},
                    onValueChange = {passwordVerification.value = it},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.padding(12.dp))
                Button(onClick = {
                    if(email.value.isNotEmpty() && password.value.isNotEmpty()){
                        if(email.value == emailVerification.value && password.value == passwordVerification.value){
                            viewModel.setData(email = email.value, password = password.value, firstName = "", lastName = "", phoneNumber = "", dateOfBirth = "", error = state.error)
                            navigateToRegisterScreenStep2()
                        }else{
                            Toast.makeText(context, "Please, email and password must match", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(context, "Please, fill all fields", Toast.LENGTH_LONG).show()
                    }
                                 },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)) {
                    Text(text = "Next")
                }
                /*
                if(errorMessage.value != ""){
                    Toast.makeText(context, errorMessage.value, Toast.LENGTH_SHORT).show()
                    viewModel.clearErrorMessage()
                }
                 */
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround){
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
                    Column {
                        Button(
                            onClick = { /*TODO*/ },
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
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(name = "default")
@Composable
fun RegisterScreenLayoutPreview(){
    val viewModel = RegisterScreenViewModel(RegisterScreenRepositoryImpl())
    RegisterScreen({}, viewModel)
}