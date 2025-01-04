package com.example.store.ui.presentation.Home

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.store.data.repository.AuthRepositoryImpl
import com.example.store.domain.use_case.AuthUseCase
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: ViewModel() {
    private val repository = AuthRepositoryImpl()
    private val authUseCase = AuthUseCase(repository)

    suspend fun logout(){
        try {
            authUseCase.logout()
        }catch (e: Exception){
            throw IllegalArgumentException(e.message)
        }
    }
}