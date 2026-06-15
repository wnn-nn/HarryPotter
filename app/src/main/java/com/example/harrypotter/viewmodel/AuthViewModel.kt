package com.example.harrypotter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val loginSuccess = _loginSuccess.asStateFlow()

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

    fun login(username: String, password: CharSequence) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getUser(username)
            if (user != null && user.password == password) {
                sessionManager.saveSession(username)
                _loginSuccess.value = true
            }
        }
    }

    private fun loadUserProfile(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getUser(username)
            _currentUserProfile.value = user
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