package com.example.wallpaperapp.registerscreen.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.NavigationPaths
import com.example.wallpaperapp.R
import com.example.wallpaperapp.registerscreen.model.RegisterScreenRepositoryImpl
import com.example.wallpaperapp.registerscreen.viewmodel.RegisterScreenViewModel
import com.example.wallpaperapp.ui.theme.WhiteGray
import com.example.wallpaperapp.ui.theme.darkBackgroundSecondary
import com.example.wallpaperapp.ui.theme.lightBackgroundSecondary
import kotlin.concurrent.fixedRateTimer

@Composable
fun RegisterScreenStep2(viewModel: RegisterScreenViewModel, goToHomeScreen:() -> Unit){
                val isDarkTheme = isSystemInDarkTheme()
                val robotoFamily = FontFamily(Font(R.font.roboto_regular))
                val righteousFamily = FontFamily(Font(R.font.righteous_regular))
                val poppinsFamily = FontFamily(Font(R.font.poppins_regular))
                val fontColor = if(isDarkTheme) Color.White else Color.Black
                val state by viewModel.stateViewer.collectAsState()
                val isRegistrationSuccessful by viewModel.isRegisterSuccessful.collectAsState()
                val firstName = remember{mutableStateOf(state.firstName)}
                val lastName = remember{mutableStateOf(state.lastName)}
                val phoneNumber = remember{mutableStateOf(state.phoneNumber)}
                val dateOfBirth = remember{mutableStateOf(state.dateOfBirth)}
                Scaffold(modifier = Modifier.fillMaxSize()){ paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .padding(start = 26.dp, end = 26.dp, top = 16.dp, bottom = 16.dp)){
                        Column {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                                Divider(modifier = Modifier
                                    .width(148.dp)
                                    .height(6.dp), color = WhiteGray)
                                Divider(modifier = Modifier
                                    .width(148.dp)
                                    .height(6.dp), color = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                            }
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Column(modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text(text = "Complete Your Profile", fontFamily = righteousFamily, fontSize = 26.sp, textAlign = TextAlign.Center)
                            Image(painter = painterResource(id = R.drawable.icon_avatar), contentDescription = "")
                        }
                        Spacer(modifier = Modifier.padding(16.dp))
                        Column(modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedTextField(
                                value = firstName.value,
                                label = { Text(text = "First Name") },
                                onValueChange = {firstName.value = it},
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp)
                            )
                            OutlinedTextField(
                                value = lastName.value,
                                label = { Text(text = "Last Name") },
                                onValueChange = {lastName.value = it},
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp)
                            )
                            OutlinedTextField(
                                value = phoneNumber.value,
                                label = { Text(text = "Phone Number") },
                                onValueChange = {phoneNumber.value = it},
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp)
                            )
                            OutlinedTextField(
                                value = dateOfBirth.value,
                                label = { Text(text = "Date of Birth") },
                                onValueChange = {dateOfBirth.value = it},
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp)
                            )
                            Spacer(modifier = Modifier.padding(12.dp))
                            Button(onClick = {
                                if(firstName.value.isNotEmpty() && lastName.value.isNotEmpty() && phoneNumber.value.isNotEmpty() && dateOfBirth.value.isNotEmpty()){
                                    viewModel.setData(state.email, state.password, firstName.value, lastName.value, phoneNumber.value, dateOfBirth.value, state.error)
                                    viewModel.signUp(state.email, state.password, firstName.value, lastName.value, phoneNumber.value, dateOfBirth.value)
                                }
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)) {
                                Text(text = "Next")
                            }
                            LaunchedEffect(isRegistrationSuccessful){
                                if(isRegistrationSuccessful){
                                    goToHomeScreen()
                                    viewModel.resetStateViewModel()
                                }
                            }
                            Spacer(modifier = Modifier.padding(2.dp))
                            Button(
                                onClick = { /*TODO*/ },
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if(isDarkTheme) darkBackgroundSecondary else lightBackgroundSecondary),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)) {
                                Text(text = "Back", color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
}

@Preview
@Composable
fun RegisterScreenLayoutPreview2(){
    val viewModel = RegisterScreenViewModel(registerScreenrepository = RegisterScreenRepositoryImpl())
    RegisterScreenStep2(viewModel, {})
}