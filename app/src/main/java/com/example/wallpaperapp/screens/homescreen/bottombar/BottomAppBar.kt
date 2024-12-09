package com.example.wallpaperapp.screens.homescreen.bottombar

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wallpaperapp.NavigationPaths
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable

@Composable
fun BottomAppBarLayout(navController: NavController) {
    val navigationBarItems = remember {NavigationBarItems.values()}

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val selectedIndex = navigationBarItems.indexOfFirst { item ->
        when (item) {
            NavigationBarItems.Home -> NavigationPaths.HomeScreen.route
            NavigationBarItems.Person -> NavigationPaths.BottomProfile.route
            NavigationBarItems.Categories -> NavigationPaths.BottomCategories.route
            NavigationBarItems.Menu -> NavigationPaths.BottomAppMenu.route
        } == currentRoute
    }.takeIf { it != -1 } ?: 0
    Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 24.dp)){
        AnimatedNavigationBar(
            modifier = Modifier.height(64.dp),
            selectedIndex = selectedIndex,
            cornerRadius = shapeCornerRadius(cornerRadius = 34.dp),
            ballAnimation = Parabolic(tween(300)),
            indentAnimation = Height(tween(300)),
            barColor = MaterialTheme.colorScheme.surface,
            ballColor = MaterialTheme.colorScheme.primary
        ) {
            navigationBarItems.forEach { item ->
               val route = when(item){
                    NavigationBarItems.Home -> NavigationPaths.HomeScreen.route
                    NavigationBarItems.Person -> NavigationPaths.BottomProfile.route
                    NavigationBarItems.Categories -> NavigationPaths.BottomCategories.route
                    NavigationBarItems.Menu -> NavigationPaths.BottomAppMenu.route
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .noRippleClickable {
                            navController.navigate(route){
                                launchSingleTop = true
                                popUpTo(NavigationPaths.HomeScreen.route) { inclusive = false }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        imageVector = item.icon,
                        contentDescription = "Bottom Bar Icon",
                        tint = if (selectedIndex == item.ordinal) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            }
        }
    }
}

enum class NavigationBarItems(val icon: ImageVector){
    Home(Icons.Default.Home),
    Person(Icons.Default.Person),
    Categories(Icons.Default.Category),
    Menu(Icons.Default.Menu)
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}