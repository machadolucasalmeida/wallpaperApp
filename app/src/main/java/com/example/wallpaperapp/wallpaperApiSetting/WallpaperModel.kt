package com.example.wallpaperapp.wallpaperApiSetting

data class WallpaperResponse(
    val data: List<WallpaperModel>,
    val meta: Meta
)
data class Meta(
    val current_page: Int,
    val last_page: Int,
)
data class WallpaperModel(
    val id: String,
    val url: String,
    val short_url: String,
    val views: Int,
    val favorites: Int,
    val source: String,
    val purity: String,
    val category: String,
    val dimension_x: Int,
    val dimension_y: Int,
    val resolution: String,
    val ratio: Float,
    val file_size: Int,
    val file_type: String,
    val created_at: String,
    val colors: List<String>,
    val path: String,
    val thumbs: Thumbs
)
data class Thumbs(
    val large: String,
    val original: String,
    val small: String
)
data class Colors(
    val palette: List<String>
)