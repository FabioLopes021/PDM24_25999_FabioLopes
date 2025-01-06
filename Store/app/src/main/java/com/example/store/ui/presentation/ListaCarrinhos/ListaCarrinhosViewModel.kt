package com.example.store.ui.presentation.ListaCarrinhos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.data.repository.CarrinhoRepositoryImpl
import com.example.store.data.repository.ProdutoRepositoryImpl
import com.example.store.data.repository.UtilizadorRepositoryImpl
import com.example.store.domain.model.Produto
import com.example.store.domain.model.ProdutoApresentacao
import com.example.store.domain.model.Utilizador
import com.example.store.domain.use_case.CarrinhoUseCase
import com.example.store.domain.use_case.ProdutoUseCase
import com.example.store.domain.use_case.UtilizadorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ListaCarrinhosViewModel(): ViewModel() {
    //Utilizadores
    private val utilizadoresRepository = UtilizadorRepositoryImpl()
    private val utilizadoresUseCase = UtilizadorUseCase(utilizadoresRepository)

    //Produtos CarrinhoUtilizador
    private val _CarrinhosPartilhados = MutableStateFlow<List<Utilizador>>(emptyList())
    val CarrinhosPartilhados: StateFlow<List<Utilizador>> get() = _CarrinhosPartilhados


    init {
        lerUtilizadoresComCarrinhoPublico()
    }

    private fun lerUtilizadoresComCarrinhoPublico(){
        viewModelScope.launch {
            utilizadoresUseCase.GetUtilizadoresCarrinhoPartilhado()
                .catch {ex ->
                    Log.e("Produtos view model", "Erro ao observar produtos: $ex")
                }
                .collect{ utilizadores ->
                    _CarrinhosPartilhados.value =utilizadores
                }
        }
    }


}