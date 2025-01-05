package com.example.store.domain.use_case

import android.util.Log
import com.example.store.data.repository.UtilizadorRepositoryImpl
import com.example.store.domain.model.Utilizador

class UtilizadorUseCase(private val repository: UtilizadorRepositoryImpl) {

    suspend fun getUtilizador(email: String): Utilizador? {
        return try {
             repository.getUserById(email)
        }catch(ex: Exception){
            Log.d("Tag","${ex.message}")
            null
        }
    }
}