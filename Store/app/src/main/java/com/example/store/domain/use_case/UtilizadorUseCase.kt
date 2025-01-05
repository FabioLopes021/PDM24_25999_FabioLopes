package com.example.store.domain.use_case

import android.util.Log
import androidx.media3.common.util.Util
import com.example.store.data.repository.UtilizadorRepositoryImpl
import com.example.store.domain.model.Utilizador

class UtilizadorUseCase(private val repository: UtilizadorRepositoryImpl) {

    suspend fun getUtilizador(email: String): Utilizador? {
        return try {
             repository.getUserByEmail(email)
        }catch(ex: Exception){
            Log.d("Tag","${ex.message}")
            null
        }
    }

    suspend fun AdicionarUtilizador(user: Utilizador): Boolean{
        return repository.addUtilizador(user)
    }

}