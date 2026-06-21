package com.example.harrypotter.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface HarryPotterApiService {
    @GET("api/characters")
    suspend fun getCharacters(): List<CharacterModel>

    @GET("api/character/{id}")
    suspend fun getCharacterDetail(@Path("id") id: String): List<CharacterModel>

}