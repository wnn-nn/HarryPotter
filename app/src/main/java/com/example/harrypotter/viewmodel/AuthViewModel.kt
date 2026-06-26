package com.example.harrypotter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.harrypotter.data.repository.AppRepository
import com.example.harrypotter.data.session.SessionManager
import com.example.harrypotter.data.local.AppDatabase
import com.example.harrypotter.data.local.UserEntity

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    private val sessionManager: SessionManager

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _currentUserProfile = MutableStateFlow<UserEntity?>(null)
    val currentUserProfile = _currentUserProfile.asStateFlow()

    init {
        val db = AppDatabase.getDatabase(application)
        repository = AppRepository(db)
        sessionManager = SessionManager(application)

        viewModelScope.launch {
            sessionManager.usernameFlow.collect { username ->
                _isLoggedIn.value = username != null
                if (username != null) {
                    loadUserProfile(username)
                }
            }
        }
    }

    fun registerUser(user: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.registerUser(user)
        }
    }

    fun login(username: String, pass: String) {
        _errorMessage.value = ""
        if (username.isBlank() || pass.isBlank()) {
            _errorMessage.value = "Username dan Password tidak boleh kosong!"
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.login(username, pass)
            if (user != null) {
                sessionManager.saveSession(user.username)
                _loginSuccess.value = true
                _errorMessage.value = ""
            } else {
                _loginSuccess.value = false
                _errorMessage.value = "Username atau password salah!"
            }
        }
    }

    private fun loadUserProfile(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getUser(username)
            _currentUserProfile.value = user
        }
    }

    fun updateUserProfile(updatedUser: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(updatedUser)
            _currentUserProfile.value = updatedUser // Langsung update state UI
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _loginSuccess.value = false
            _isLoggedIn.value = false
            _currentUserProfile.value = null
        }
    }
}