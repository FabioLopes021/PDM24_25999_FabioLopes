package com.example.store.domain.use_case

import android.text.BoringLayout
import android.util.Log
import androidx.media3.common.util.Util
import com.example.store.data.repository.UtilizadorRepositoryImpl
import com.example.store.domain.model.Utilizador
import kotlinx.coroutines.flow.Flow

class UtilizadorUseCase(private val repository: UtilizadorRepositoryImpl) {



    fun GetUtilizadoresCarrinhoPartilhado(): Flow<List<Utilizador>>{
        return repository.GetUtilizadoresCarrinhoPartilhado()
    }

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

    suspend fun alterarVisbilidadeCarrinho(utilizador: Utilizador, utilizadorId: String): Boolean{
        return repository.alterarVisibilidadeCarrinho(utilizador, utilizadorId)
    }

}