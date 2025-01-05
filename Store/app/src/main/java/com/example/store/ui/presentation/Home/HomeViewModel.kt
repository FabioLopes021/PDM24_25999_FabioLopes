package com.example.store.ui.presentation.Home

import android.view.View
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import androidx.media3.common.util.Util
import com.example.store.data.repository.AuthRepositoryImpl
import com.example.store.data.repository.ProdutoRepositoryImpl
import com.example.store.data.repository.UtilizadorRepositoryImpl
import com.example.store.domain.model.Produto
import com.example.store.domain.model.Utilizador
import com.example.store.domain.use_case.AuthUseCase
import com.example.store.domain.use_case.ProdutoUseCase
import com.example.store.domain.use_case.UtilizadorUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val repository = AuthRepositoryImpl()
    private val authUseCase = AuthUseCase(repository)
    private val productsRepository = ProdutoRepositoryImpl()
    private val productUseCase = ProdutoUseCase(productsRepository)
    private val utilizadoresRepository = UtilizadorRepositoryImpl()
    private val utilizadoresUseCase = UtilizadorUseCase(utilizadoresRepository)


    private val _produtos = MutableStateFlow<List<Produto>>(emptyList())
    val produtos: StateFlow<List<Produto>> get() =_produtos

    init {
        getProdutos()
    }

    suspend fun logout(){
        try {
            authUseCase.logout()
        }catch (e: Exception){
            throw IllegalArgumentException(e.message)
        }
    }

    private fun observeProducts(){
        viewModelScope.launch {
            productUseCase()
                .catch {e ->
                    Log.e("Produtos view model", "Erro ao observar produtos: $e")
                }
                .collect{produtosAtualizados ->
                    _produtos.value = produtosAtualizados
                }
        }
    }

    private fun getProdutos(){
        viewModelScope.launch {
            productUseCase.getProdutos()
                .catch {ex->
                    Log.e("Produtos view model", "Erro ao observar produtos: $ex")
                }
                .collect { produtosAtualizado ->
                    _produtos.value = produtosAtualizado
            }
        }
    }

    suspend fun fetchUtilizador(email: String?): Utilizador?{
        //return utilizadoresRepository.getUserById(email!!)
        return email?.let {
            utilizadoresRepository.getUserById(it)
        }
    }

}