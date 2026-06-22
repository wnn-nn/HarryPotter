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
    private val repository = AppRepository(AppDatabase.getDatabase(application))

    private val _characters = MutableStateFlow<List<CharacterModel>>(emptyList())
    val characters = _characters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val favoriteCharacters = repository.getAllFavorites()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val apiData = repository.getCharacters()

                val data = apiData.filter {
                    it.name.isNotBlank() &&
                            it.image.isNotBlank() &&
                            it.gender.isNotBlank() &&
                            it.ancestry.isNotBlank() &&
                            it.house.isNotBlank() &&
                            it.actor.isNotBlank()
                }
                _characters.value = data
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}