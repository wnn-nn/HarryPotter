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
import com.example.harrypotter.data.local.AppDatabase

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository

    private val _characters = MutableStateFlow<List<CharacterModel>>(emptyList())
    val characters = _characters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val favoriteCharacters = AppDatabase.getDatabase(application).favoriteDao().getAllFavorites()

    init {
        val db = AppDatabase.getDatabase(application)
        repository = AppRepository(db)
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                _characters.value = repository.getCharacters()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}