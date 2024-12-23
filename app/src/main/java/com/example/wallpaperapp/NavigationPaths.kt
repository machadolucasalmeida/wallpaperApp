package com.example.wallpaperapp

sealed class NavigationPaths(val route: String){
    object HomeScreen : NavigationPaths("home_screen")
    object PagesScreen : NavigationPaths("page_onboarding_screen")
    object LoginScreen : NavigationPaths("login_screen")
    object RegisterScreen : NavigationPaths("register_screen")
    object RegisterScreenStep2 : NavigationPaths("register_screen2")
    object BottomSettings : NavigationPaths("settings_screen")
    object BottomCategories : NavigationPaths("categories")
    object BottomProfile : NavigationPaths("profile_screen")
    object AccountSettings : NavigationPaths("account_settings_screen")

}