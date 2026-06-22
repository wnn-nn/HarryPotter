package com.example.harrypotter.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val city: String = "",
    val province: String = "",
    val country: String = "",
    val imageUrl: String = ""
)