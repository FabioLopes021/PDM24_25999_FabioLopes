package com.example.lojasocial.presentation.Loading

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojasocial.data.repository.AuthRepositoryImpl
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.use_case.AuthUseCase
import com.example.lojasocial.utils.getDadosUtilizador
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoadingViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repository = AuthRepositoryImpl(auth)
    private val authUseCase = AuthUseCase(repository)

    private val _isLoggedIn = MutableLiveData<Boolean?>()
    val isLoggedIn: LiveData<Boolean?> = _isLoggedIn

    init {
        //_isLoggedIn.value = authUseCase.isLoggedIn()
    }

    fun isLoggedIn(context: Context){
        val user = getDadosUtilizador(context)
        if(user != null)
            _isLoggedIn.value = true
        else
            _isLoggedIn.value = false
    }
}