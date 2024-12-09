package com.example.wallpaperapp.screens.homescreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.wallpaperapp.wallpaperApiSetting.WallpaperViewModel


@Composable
fun HomeScreen(wallpaperViewModel: WallpaperViewModel){
    val selectedFilter = remember{mutableStateOf<FilterItems?>(null)}
    val wallpaperState = wallpaperViewModel.wallpaperItem.collectAsState()
    Column{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)

        ){
            FilterItems.values().forEach {item->

                FilterChip(
                    selected = selectedFilter.value == item,
                    onClick = {
                        selectedFilter.value = if (selectedFilter.value == item){
                            null
                        }else{item

                        }
                        if(item.text == "Latest"){
                            wallpaperViewModel.fetchWallpapers("date_added")
                        }else{
                            wallpaperViewModel.fetchWallpapers(selectedFilter.value?.text?.lowercase() ?: "random")
                        }
                              },
                    label = {
                        Icon(imageVector = item.icon, contentDescription = "Icons")
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(text = item.text)
                            },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White,
                        disabledSelectedContainerColor = Color.Transparent,
                        disabledLabelColor = MaterialTheme.colorScheme.primary,
                        )
                )
            }
        }
        HomeScreenWallpapers(wallpaperState.value)
    }
}

enum class FilterItems(val text: String, val icon:ImageVector){
    Relevance("Relevance", Icons.Filled.AutoAwesome),
    Latest("Latest", Icons.Filled.HourglassBottom),
    Random("Random", Icons.Filled.AllInclusive),
    Hot("Hot", Icons.Filled.Whatshot),
    Views("Views", Icons.Filled.Bolt), // You may want to change the icon for clarity
    Toplist("Toplist", Icons.Filled.Leaderboard)
}