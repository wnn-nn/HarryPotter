package com.example.harrypotter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.harrypotter.data.repository.AppRepository
import com.example.harrypotter.data.remote.CharacterModel
import com.example.harrypotter.data.local.FavoriteEntity
import com.example.harrypotter.data.local.AppDatabase

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository

    private val _characterDetail = MutableStateFlow<CharacterModel?>(null)
    val characterDetail = _characterDetail.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    init {
        val db = AppDatabase.getDatabase(application)
        repository = AppRepository(db)
    }

    fun getCharacterDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = repository.getCharacterDetail(id)
                if (list.isNotEmpty()) {
                    _characterDetail.value = list[0]
                    _isFavorite.value = repository.isFavorite(id)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavorite(character: CharacterModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val favEntity = FavoriteEntity(character.id, character.name, character.house, character.image)
            if (_isFavorite.value) {
                repository.removeFavorite(favEntity)
                _isFavorite.value = false
            } else {
                repository.addFavorite(favEntity)
                _isFavorite.value = true
            }
        }
    }
}