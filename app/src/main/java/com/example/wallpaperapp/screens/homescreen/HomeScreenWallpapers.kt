package com.example.wallpaperapp.screens.homescreen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wallpaperapp.wallpaperApiSetting.WallpaperViewModel

@Composable
fun HomeScreenWallpapers(wallpaperState: WallpaperViewModel.WallpaperState) {
    val wallpaperViewModel: WallpaperViewModel = viewModel()
    val viewState = wallpaperViewModel.wallpaperItem.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                wallpaperState.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                wallpaperState.error != null -> {
                    Text(text = viewState.value.error.toString())
                }
                else -> {
                    WallpaperScreen(wallpapers = wallpaperState.imagesList, modifier = Modifier.fillMaxSize().padding(top = 16.dp))
                }
            }
        }
    }
}




