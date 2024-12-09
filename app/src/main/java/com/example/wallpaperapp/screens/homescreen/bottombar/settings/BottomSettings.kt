package com.example.wallpaperapp.screens.homescreen.bottombar.settings

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.R
import com.example.wallpaperapp.loginscreen.viewmodel.LoginScreenViewModel
import com.example.wallpaperapp.setUserLoggedIn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSettingsLayout(goToAccountScreen:() -> Unit) {
    val showModalBottomSheet = remember{ mutableStateOf(false)}
    val toggledState = remember{ mutableStateOf(false)}
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        Text("Settings", fontSize = 42.sp)
        Column {
            Text("Account", fontSize = 32.sp)
            Card(modifier = Modifier.fillMaxWidth(), onClick = {goToAccountScreen()}){
                Box(){
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                        Image(painter = painterResource(R.drawable.icon_avatar), contentDescription = "", modifier = Modifier.size(52.dp))
                        Column {
                            Text("Lucas Machado")
                            Text("Personal Info")
                        }
                        IconButton({goToAccountScreen()}){
                            Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "")
                        }
                    }

                }
            }
        }
        Column {
            Text("Settings", fontSize = 32.sp)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(R.drawable.ic_language), contentDescription = "", modifier = Modifier.size(52.dp))
                Text("Language", fontSize = 24.sp)
                Text("English", fontSize = 18.sp)
                IconButton({
                    if(showModalBottomSheet.value == false){
                        showModalBottomSheet.value = true
                    }else{
                        showModalBottomSheet.value = false
                    }
                }){
                    Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "")
                }
            }
            if(showModalBottomSheet.value){
                ModalBottomSheet(onDismissRequest = {showModalBottomSheet.value = false}){
                    Column(modifier = Modifier.padding(16.dp).fillMaxSize()){
                        Text("English", fontSize = 24.sp)
                        Text("Portuguese", fontSize = 24.sp)
                        Text("Spanish", fontSize = 24.sp)
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(R.drawable.ic_notification), contentDescription = "", modifier = Modifier.size(52.dp))
                Text("Notification", fontSize = 24.sp)
                IconButton({}){
                    Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "")
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(R.drawable.ic_dark_mode), contentDescription = "", modifier = Modifier.size(52.dp))
                Text("Dark Mode", fontSize = 24.sp)
                val textDarkMode = if(toggledState.value){
                    "On"
                }else{
                    "Off"
                }
                Text(textDarkMode, fontSize = 18.sp)

                Switch(checked = toggledState.value, onCheckedChange = {
                    if(toggledState.value){
                        toggledState.value = false
                    }else{
                        toggledState.value = true
                    }
                })
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(R.drawable.ic_help), contentDescription = "", modifier = Modifier.size(52.dp))
                Text("Help", fontSize = 24.sp)
                IconButton({}){
                    Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "")
                }
            }


        }
    }
}


@Preview(showBackground = true)
@Composable
fun BottomSettingsLayoutPreview(){
    BottomSettingsLayout({})
}