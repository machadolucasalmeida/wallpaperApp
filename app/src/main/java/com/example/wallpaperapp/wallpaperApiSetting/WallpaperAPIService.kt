package com.example.wallpaperapp.wallpaperApiSetting

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private val retrofit = Retrofit
    .Builder()
    .baseUrl("https://wallhaven.cc/api/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("search")
    suspend fun getRandomWallpapers(
        @Query("sorting") sorting: String = "random",
        @Query("apikey") apiKey: String = "NsHinnvcDAwn3YD6IDY0L1pMn27TGfAG",
        @Query("purity") purity: String = "100",
        @Query("ratios") ratios: String = "9x16, 9x18, 3x5, 10x16, 9x21",
    ): WallpaperResponse
}