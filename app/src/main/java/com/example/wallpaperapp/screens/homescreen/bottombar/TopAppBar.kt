package com.example.wallpaperapp.screens.homescreen.bottombar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarLayout(){
    val scrollBehabior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val isDarkTheme = isSystemInDarkTheme()
    val fontColor = if(isDarkTheme) Color.White else Color.Black
    val robotoFamily = FontFamily(Font(R.font.roboto_regular))
    val righteousFamily = FontFamily(Font(R.font.righteous_regular))
    val poppinsFamily = FontFamily(Font(R.font.poppins_regular))

    CenterAlignedTopAppBar(
        title = {
            Text(text = "ArtWall", fontFamily = righteousFamily, fontSize = 26.sp)
        },
        modifier = Modifier
            .fillMaxWidth()
            ,
        scrollBehavior = scrollBehabior,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
@Preview
fun TopAppBarPreview(){
    TopAppBarLayout()
}