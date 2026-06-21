package com.example.harrypotter.data.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.harrypotter.data.local.UserEntity
import com.example.harrypotter.data.local.AppDatabase
import com.example.harrypotter.data.local.FavoriteEntity
import com.example.harrypotter.data.remote.HarryPotterApiService

class AppRepository(private val database: AppDatabase) {

    private val apiService = Retrofit.Builder()
        .baseUrl("https://hp-api.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HarryPotterApiService::class.java)

    // Remote API Functions
    suspend fun getCharacters() = apiService.getCharacters()
    suspend fun getCharacterDetail(id: String) = apiService.getCharacterDetail(id)
    suspend fun getSpells() = apiService.getSpells()

    // Local DB Room Functions
    suspend fun registerUser(user: UserEntity) = database.userDao().insertUser(user)
    suspend fun getUser(username: String) = database.userDao().getUserByUsername(username)

    suspend fun addFavorite(fav: FavoriteEntity) = database.favoriteDao().insertFavorite(fav)
    suspend fun removeFavorite(fav: FavoriteEntity) = database.favoriteDao().deleteFavorite(fav)
    fun getAllFavorites(): Flow<List<FavoriteEntity>> = database.favoriteDao().getAllFavorites()
    suspend fun isFavorite(id: String): Boolean = database.favoriteDao().isFavorite(id)
}