package com.example.harrypotter.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface HarryPotterApiService {
    @GET("api/characters")
    suspend fun getCharacters(): List<CharacterModel>

    @GET("api/character/{id}")
    suspend fun getCharacterDetail(@Path("id") id: String): List<CharacterModel>

    @GET("api/characters/house/{house}")
    suspend fun getCharactersByHouse(@Path("house") house: String): List<CharacterModel>

    @GET("api/characters/students")
    suspend fun getStudents(): List<CharacterModel>

    @GET("api/characters/staff")
    suspend fun getStaff(): List<CharacterModel>

    @GET("api/spells")
    suspend fun getSpells(): List<SpellModel>
}