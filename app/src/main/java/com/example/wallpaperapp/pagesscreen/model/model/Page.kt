package com.example.wallpaperapp.pagesscreen.model.model

import com.example.wallpaperapp.R

data class Page(
    val image: Int,
    val title: String,
    val description: String
)


    val pages = listOf(
        Page(
            image = R.drawable.spiderman_pages,
            title = "Transform Your Screen",
            description = "Discover a world of stunning wallpapers to personalize your device. From vibrant landscapes to minimalistic designs, find the perfect look to match your style "
        ),
        Page(
            image = R.drawable.kimetsu_pages,
            title = "Wallpapers for Every Mood",
            description = "Browse through an ever-growing gallery of wallpapers tailored to fit your mood and preferences."
        ),
        Page(
            image = R.drawable.voyager_pages,
            title = "Without Limits",
            description = "Unleash your creativity with an endless collection of wallpapers. No boundaries, no restrictions—just limitless options to customize your device and make it truly yours!"
        )
    )
