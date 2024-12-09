package com.example.wallpaperapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wallpaperapp.loginscreen.model.LoginScreenRepositoryImpl
import com.example.wallpaperapp.loginscreen.view.LoginScreen
import com.example.wallpaperapp.loginscreen.viewmodel.LoginScreenViewModel
import com.example.wallpaperapp.loginscreen.viewmodel.LoginScreenViewModelFactory
import com.example.wallpaperapp.pagesscreen.model.view.OnBoardingScreen
import com.example.wallpaperapp.registerscreen.model.RegisterScreenRepositoryImpl
import com.example.wallpaperapp.registerscreen.view.RegisterScreen
import com.example.wallpaperapp.registerscreen.view.RegisterScreenStep2
import com.example.wallpaperapp.registerscreen.viewmodel.RegisterScreenViewModel
import com.example.wallpaperapp.registerscreen.viewmodel.RegisterScreenViewModelFactory
import com.example.wallpaperapp.screens.homescreen.HomeScreen
import com.example.wallpaperapp.screens.homescreen.bottombar.BottomCategoriesLayout
import com.example.wallpaperapp.screens.homescreen.bottombar.BottomAppBarLayout
import com.example.wallpaperapp.screens.homescreen.bottombar.settings.BottomSettingsLayout
import com.example.wallpaperapp.screens.homescreen.bottombar.BottomProfileLayout
import com.example.wallpaperapp.screens.homescreen.bottombar.TopAppBarLayout
import com.example.wallpaperapp.screens.homescreen.bottombar.settings.AccountLayout
import com.example.wallpaperapp.wallpaperApiSetting.WallpaperViewModel

@Composable
fun WallpaperApp(){
    val navController = rememberNavController()

    val loginScreenViewModelFactory = LoginScreenViewModelFactory(LoginScreenRepositoryImpl())
    val loginScreenViewModel : LoginScreenViewModel = viewModel(factory = loginScreenViewModelFactory)

    val registerScreenVviewModelFactory = RegisterScreenViewModelFactory(
        RegisterScreenRepositoryImpl()
    )
    val registerScreenViewModel : RegisterScreenViewModel = viewModel(factory = registerScreenVviewModelFactory)

    val wallpaperViewModel = WallpaperViewModel()

    val currentRoute = navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry).value?.destination?.route
    val context = LocalContext.current

    val startNavigation = remember {
        when {
            isFirstLaunch(context) -> NavigationPaths.PagesScreen.route
            isUserLoggedIn(context) -> NavigationPaths.HomeScreen.route
            else -> NavigationPaths.LoginScreen.route
        }
    }

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf(
                    NavigationPaths.HomeScreen.route,
                    NavigationPaths.BottomSettings.route,
                    NavigationPaths.BottomProfile.route,
                    NavigationPaths.BottomCategories.route
                )) {
                BottomAppBarLayout(navController)
            }
        },
        topBar = {
            if(currentRoute == NavigationPaths.HomeScreen.route){
                TopAppBarLayout()
            }
        }
    ){
        NavHost(
            navController = navController,
            startDestination = startNavigation,
            modifier = Modifier.padding(it)
        ){
            composable(route = NavigationPaths.PagesScreen.route){
                OnBoardingScreen(navigateToLoginScreen = {navController.navigate(route = NavigationPaths.LoginScreen.route)})
            }
            composable(route = NavigationPaths.LoginScreen.route){
                LoginScreen({navController.navigate(NavigationPaths.RegisterScreen.route)}, {}, {navController.navigate(NavigationPaths.HomeScreen.route)}, loginScreenViewModel) }
            composable(route = NavigationPaths.HomeScreen.route){ HomeScreen(wallpaperViewModel) }
            composable(route = NavigationPaths.RegisterScreen.route){ RegisterScreen({navController.navigate(NavigationPaths.RegisterScreenStep2.route)}, registerScreenViewModel) }
            composable(route = NavigationPaths.RegisterScreenStep2.route){RegisterScreenStep2(registerScreenViewModel, {navController.navigate(NavigationPaths.HomeScreen.route)})}
            composable(route = NavigationPaths.BottomSettings.route){ BottomSettingsLayout({navController.navigate(NavigationPaths.AccountSettings.route)}) }
            composable(route = NavigationPaths.BottomProfile.route){BottomProfileLayout()}
            composable(route = NavigationPaths.BottomCategories.route){BottomCategoriesLayout()}
            composable(route = NavigationPaths.AccountSettings.route){AccountLayout(loginScreenViewModel,{navController.navigate(NavigationPaths.LoginScreen.route){popUpTo(NavigationPaths.HomeScreen.route) { inclusive = true }}}, {navController.navigate(NavigationPaths.BottomSettings.route)})}
        }
    }
}