package com.example.store.ui.presentation.Home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.store.data.remote.model.ProdutoCarrinhoDto
import com.example.store.data.repository.AuthRepositoryImpl
import com.example.store.data.repository.CarrinhoRepositoryImpl
import com.example.store.data.repository.ProdutoRepositoryImpl
import com.example.store.data.repository.UtilizadorRepositoryImpl
import com.example.store.domain.model.Produto
import com.example.store.domain.model.ProdutoApresentacao
import com.example.store.domain.model.Utilizador
import com.example.store.domain.use_case.AuthUseCase
import com.example.store.domain.use_case.CarrinhoUseCase
import com.example.store.domain.use_case.ProdutoUseCase
import com.example.store.domain.use_case.UtilizadorUseCase
import com.example.store.utils.showToastMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    //Auth
    private val repository = AuthRepositoryImpl()
    private val authUseCase = AuthUseCase(repository)

    //Produtos
    private val productsRepository = ProdutoRepositoryImpl()
    private val productUseCase = ProdutoUseCase(productsRepository)

    //Utilizadores
    private val utilizadoresRepository = UtilizadorRepositoryImpl()
    private val utilizadoresUseCase = UtilizadorUseCase(utilizadoresRepository)

    //Carrinho
    private val carrinhoRepository = CarrinhoRepositoryImpl()
    private val carrinhoUseCase = CarrinhoUseCase(carrinhoRepository)

    //Produtos
    private val _produtos = MutableStateFlow<List<Produto>>(emptyList())
    val produtos: StateFlow<List<Produto>> get() = _produtos

    //Produtos CarrinhoUtilizador
    private val _produtosCarrinho = MutableStateFlow<List<ProdutoApresentacao>>(emptyList())
    val produtosCarrinho: StateFlow<List<ProdutoApresentacao>> get() = _produtosCarrinho

    private val _produtosCarrinho1 = MutableStateFlow<List<ProdutoCarrinhoDto>>(emptyList())
    val produtosCarrinho1: StateFlow<List<ProdutoCarrinhoDto>> get() = _produtosCarrinho1

//    private val _produtosCarrinho2 = MutableStateFlow<List<ProdutoCarrinhoDto>>(emptyList())
    val produtosCarrinho2: StateFlow<List<ProdutoApresentacao>> = _produtosCarrinho1
    .flatMapLatest {produtosCarrinho ->
        val detalhesFlowList = produtosCarrinho.map {produtoCarrinho ->
            productUseCase.getProdutoByIdFlow(produtoCarrinho.produtoId)
                .map {produto ->
                    produtoCarrinho.toProdutosApresentacao(produto.nome, produto.descricao, produto.foto, produto.preco)
                }
        }

        combine(detalhesFlowList) {it.toList()}
    }.catch {ex ->
        Log.e("CarrinhoViewModel", "Erro ao combinar dados: $ex")
    }
    .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())



    init {
        getProdutos()
    }

    suspend fun logout() {
        try {
            authUseCase.logout()
        } catch (e: Exception) {
            throw IllegalArgumentException(e.message)
        }
    }


    suspend fun alterarVisibilidadeCarrinho(email: String, context: Context){
        var utilizador = utilizadoresUseCase.getUtilizador(email!!)
        if(utilizador != null){
            if( !utilizadoresUseCase.alterarVisbilidadeCarrinho(utilizador, utilizador.id))
                showToastMessage(context, "Erro ao alterar Visibilidade do carrinho")
            else
                showToastMessage(context, "Visibilidade do carrinho alterada para ${!utilizador.visibilidadeCarrinho}")
        }
    }


//    private fun observeProducts(){
//        viewModelScope.launch {
//            productUseCase.getProdutos()
//                .catch {e ->
//                    Log.e("Produtos view model", "Erro ao observar produtos: $e")
//                }
//                .collect{produtosAtualizados ->
//                    _produtos.value = produtosAtualizados
//                }
//        }
//    }

    private fun getProdutos() {
        viewModelScope.launch {
            productUseCase.getProdutos()
                .catch { ex ->
                    Log.e("Produtos view model", "Erro ao observar produtos: $ex")
                }
                .collect { produtosAtualizado ->
                    _produtos.value = produtosAtualizado
                }
        }
    }

    fun observeCarrinho(id: String) {
        viewModelScope.launch {
            carrinhoUseCase.observeDadosCarrinho(id)
                .catch { ex ->
                    Log.e("Produtos view model", "Erro ao observar produtos: $ex")
                }
                .collect { produtosAtualizado ->
                    _produtosCarrinho1.value = produtosAtualizado
                }
        }
    }

//    fun observeCarrinho(id: String) {
//        viewModelScope.launch {
//            carrinhoUseCase.observeDadosCarrinho(id)
//                .catch { ex ->
//                    Log.e("Produtos view model", "Erro ao observar produtos: $ex")
//                }
//                .collect { produtosAtualizado ->
//                    //_produtosCarrinho1.value = produtosAtualizado
//                    _produtosCarrinho.value = produtosAtualizado
//                }
//        }
//    }

        suspend fun fetchUtilizador(email: String?): Utilizador? {
            //return utilizadoresRepository.getUserById(email!!)
            return email?.let {
                Log.d("email", utilizadoresUseCase.getUtilizador(it).toString())
                utilizadoresUseCase.getUtilizador(it)
            }
        }

        suspend fun adicionarProdutoCarrinho(produto: Produto, utilizadorId: String) {
            Log.d("Carrinho", "Adicionar PRoduto")
            carrinhoUseCase.adicionarProdutoCarrinho(produto, utilizadorId)
        }


        suspend fun removerProdutoCarrinho(produto: Produto, utilizadorId: String) {
            Log.d("Carrinho", "Remover PRoduto")
            carrinhoUseCase.removerProdutoCarrinho(produto, utilizadorId)
        }


}
