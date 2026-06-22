package com.example.harrypotter.data.remote

data class CharacterModel(
    val id: String,
    val name: String,
    val house: String,
    val actor: String,
    val image: String,
    val ancestry: String,
    val gender: String,
    val wand: WandModel
)

data class WandModel(
    val wood: String,
    val core: String,
    val length: Double?
)