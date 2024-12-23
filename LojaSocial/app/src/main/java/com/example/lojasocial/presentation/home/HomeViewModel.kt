package com.example.lojasocial.presentation.home

import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.AuthRepositoryImpl
import com.example.lojasocial.domain.use_case.AuthUseCase
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel(): ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repository = AuthRepositoryImpl(auth)
    private val authUseCase = AuthUseCase(repository)

    suspend fun logout(){
        try {
            authUseCase.logout()
        }catch (e: Exception){
            throw IllegalArgumentException(e.message)
        }
    }
}