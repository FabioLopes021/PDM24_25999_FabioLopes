package com.example.store.ui.presentation.SplashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.data.repository.AuthRepositoryImpl
import com.example.store.domain.use_case.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {
    private val authRepo: AuthRepositoryImpl = AuthRepositoryImpl();
    private val useCase: AuthUseCase = AuthUseCase(authRepo)

    private val _isLoggedIn = MutableStateFlow<String?>("")
    val isLoggedIn: StateFlow<String?> get() = _isLoggedIn

    init {
        viewModelScope.launch {
            CheckIfUserIsLoggedIn()
        }
    }

    suspend fun CheckIfUserIsLoggedIn(){
        _isLoggedIn.value = useCase.getCurrentUser()
    }
}