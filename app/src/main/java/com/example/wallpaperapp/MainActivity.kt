package com.example.wallpaperapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.wallpaperapp.ui.theme.WallpaperAppTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.wallpaperapp.pagesscreen.model.view.OnBoardingScreen
import com.google.firebase.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {false}
        setContent {
            WallpaperAppTheme {
                WallpaperApp()

            }
        }
    }
}