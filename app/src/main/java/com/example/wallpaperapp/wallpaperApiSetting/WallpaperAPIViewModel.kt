package com.example.wallpaperapp.wallpaperApiSetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class WallpaperViewModel: ViewModel(){
    private val _wallpaperItem = MutableStateFlow(WallpaperState())
    val wallpaperItem = _wallpaperItem.asStateFlow()

    init {
        fetchWallpapers("random")
    }
    fun fetchWallpapers(filter: String){
        viewModelScope.launch {
            _wallpaperItem.value = WallpaperState(loading = true)
            try {
                val response = apiService.getRandomWallpapers(sorting = filter)
                _wallpaperItem.value = _wallpaperItem.value.copy(loading = false, imagesList = response.data, error = null)

            }catch(e : Exception){
                _wallpaperItem.value = _wallpaperItem.value.copy(loading = false, imagesList = emptyList(),  error = "Error fetching wallpapers.\n${e.message}")
            }
        }
    }
    data class WallpaperState(
        val loading: Boolean = true,
        val imagesList: List<WallpaperModel> = emptyList(),
        val error: String? = null
    )
}