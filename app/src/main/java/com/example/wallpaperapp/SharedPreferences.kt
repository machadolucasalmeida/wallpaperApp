package com.example.wallpaperapp

import android.content.Context
import android.util.Log

fun isFirstLaunch(context: Context): Boolean{
    val sharedPreferences = context.getSharedPreferences("onboardingPref", Context.MODE_PRIVATE)
    val ifFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

    if(ifFirstLaunch){
        sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
    }
    return ifFirstLaunch
}

fun setUserLoggedIn(context: Context, isLoggedIn: Boolean) {
    val sharedPreferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
}

fun isUserLoggedIn(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
    val loggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
    Log.d("WallpaperApp", "isUserLoggedIn: $loggedIn")
    return loggedIn
}