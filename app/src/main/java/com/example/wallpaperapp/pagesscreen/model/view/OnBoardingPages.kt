package com.example.wallpaperapp.pagesscreen.model.view

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.R
import com.example.wallpaperapp.pagesscreen.model.model.Page
import com.example.wallpaperapp.pagesscreen.model.model.pages

@Composable
fun OnBoardingPages(modifier: Modifier = Modifier, page: Page) {
    Column(modifier = Modifier){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f) // Image takes up 60% of the screen height
        ) {
            // Image
            Image(
                painter = painterResource(id = page.image),
                contentDescription = null,
                contentScale = ContentScale.Crop, // Adjust for image cropping
                modifier = Modifier.fillMaxSize()
            )

            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                            startY = 500f // Adjust as needed for the gradient's start
                        )
                    )
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = page.title,
            fontSize = 28.sp,
            modifier = Modifier.padding(horizontal = 18.dp),
            color = colorResource(id = R.color.display_small)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = page.description,
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 18.dp),
            letterSpacing = 1.sp,
            color = colorResource(id = R.color.text_medium)
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingPages(page = pages[0])
}