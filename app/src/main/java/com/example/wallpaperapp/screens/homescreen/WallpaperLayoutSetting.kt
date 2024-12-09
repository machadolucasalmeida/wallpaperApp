package com.example.wallpaperapp.screens.homescreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.wallpaperapp.wallpaperApiSetting.WallpaperModel

@Composable
fun WallpaperScreen(wallpapers: List<WallpaperModel>, modifier : Modifier) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val outerPadding = 8.dp
    val desiredColumnWidth = 150.dp
    val numberOfColumns = ((screenWidth - outerPadding * 2) / desiredColumnWidth).toInt().coerceAtLeast(2)

    LazyVerticalGrid(
        columns = GridCells.Fixed(numberOfColumns),
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(wallpapers) { wallpaper ->
            WallpaperItem(wallpaper = wallpaper)
        }
    }
}

@Composable
fun WallpaperItem(wallpaper: WallpaperModel) {
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .aspectRatio(9 / 16f)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wallpaper.path))
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)

    ){
        Column {
            Image(
                painter = rememberAsyncImagePainter(wallpaper.thumbs.original),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(wallpaper.ratio),
                contentScale = ContentScale.Crop
            )
        }
    }
}