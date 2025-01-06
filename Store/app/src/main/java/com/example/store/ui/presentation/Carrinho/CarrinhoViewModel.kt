package com.example.store.ui.presentation.Carrinho

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.data.remote.model.ProdutoCarrinhoDto
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarrinhoViewModel: ViewModel() {
    //Utilizadores
    private val utilizadoresRepository = UtilizadorRepositoryImpl()
    private val utilizadoresUseCase = UtilizadorUseCase(utilizadoresRepository)

    //Produtos
    private val productsRepository = ProdutoRepositoryImpl()
    private val productUseCase = ProdutoUseCase(productsRepository)
    //Carrinho
    private val carrinhoRepository = CarrinhoRepositoryImpl()
    private val carrinhoUseCase = CarrinhoUseCase(carrinhoRepository)

    //Produtos
    private val _produtos = MutableStateFlow<List<Produto>>(emptyList())
    val produtos: StateFlow<List<Produto>> get() = _produtos

    //Produtos CarrinhoUtilizador
    private val _produtosCarrinhoaux = MutableStateFlow<List<ProdutoCarrinhoDto>>(emptyList())


    val produtosCarrinho: StateFlow<List<ProdutoApresentacao>> = _produtosCarrinhoaux
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
                    _produtosCarrinhoaux.value = produtosAtualizado
                }
        }
    }

    suspend fun fetchUtilizador(email: String?): Utilizador? {
        //return utilizadoresRepository.getUserById(email!!)
        return email?.let {
            Log.d("email", utilizadoresUseCase.getUtilizador(it).toString())
            utilizadoresUseCase.getUtilizador(it)
        }
    }

}