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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
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

//    fun observeCarrinho(id: String) {
//        viewModelScope.launch {
//            carrinhoUseCase.observeDadosCarrinho(id)
//                .catch { ex ->
//                    Log.e("Produtos view model", "Erro ao observar produtos: $ex")
//                }
//                .collect { produtosAtualizado ->
//                    _produtosCarrinho1.value = produtosAtualizado
//                }
//        }
//    }

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

    fun observeDetalhesCarrinho(userId: String){
        viewModelScope.launch {
            carrinhoUseCase.observeDadosCarrinho(userId)
                .flatMapConcat { produtosCarrinho ->
                    produtosCarrinho.map { produtoCarrinho ->
                        productUseCase.getProdutoByIdFlow(produtoCarrinho.produtoId) // Retorna Flow<Produto>
                            .map { produto ->
                                produtoCarrinho.toProdutosApresentacao(produto.nome, produto.descricao, produto.foto, produto.preco)
                            }
                    }.let { flows -> combine(flows) { it.toList() } }
                }
                .catch {ex->
                    Log.e("Produtos view model", "Erro ao observar produtos detalhados: $ex")
                }
                .collect{produtoApresentacao ->
                    _produtosCarrinho.value = produtoApresentacao
                }
        }
    }


//    fun getCarrinho(id: String){
//        viewModelScope.launch {
//            carrinhoUseCase.getDadosCarrinho(id)
//                .catch {
//                    Log.e("Produtos Carrinho view model", "Erro ao observar produtos:")
//                }
//                .collect{carrinhoProdutos ->
//                    // Lista para armazenar os produtos detalhados
//                    val produtosDetalhados = mutableListOf<ProdutosShow>()
//
//                    // Para cada produto no carrinho, busque os detalhes
//                    for (produto in carrinhoProdutos) {
//                        try {
//                            // Chama getProdutobyId diretamente, pois não é um Flow
//                            val produtoDetalhado = productUseCase.getProdutobyId(produto.id)
//
//                            // Se o produto detalhado for encontrado, cria um Produto e adiciona à lista
//                            produtoDetalhado?.let {
//                                val prod = ProdutosShow(
//                                    it.id,
//                                    it.nome,
//                                    it.descricao,
//                                    it.foto,
//                                    produto.quantidade,
//                                    it.preco,
//                                    produto.quantidade * it.preco
//                                )
//                                Log.d("Produtos Carrinho", prod.toString())
//                                produtosDetalhados.add(prod)
//                            }
//                        } catch (e: Exception) {
//                            Log.e("Produtos Carrinho view model", "Erro ao buscar detalhes do produto: ${e.message}")
//                        }
//                    }
//
//                    // Atualiza o StateFlow com a lista de produtos detalhados
//                    Log.d("Produtos Carrinho", "produtosDetalhados")
//                    _produtosCarrinho.value = produtosDetalhados
//
//
//
////                    val produtosDetalhado = mutableListOf<Flow<List<ProdutosShow>>>()
////                    for(produto in carrinhoProdutos){
////                        val produtoDetails = productUseCase.getProdutobyId(produto.id)
////                        produtoDetails?.let {
////                            var prod = ProdutosShow(it.id,it.descricao,it.descricao,it.foto, produto.quantidade, it.preco, (produto.quantidade * it.preco))
////                            produtosDetalhado.add(prod)
////                        }
////                    }
////                    _produtosCarrinho.value = produtosDetalhado
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
            carrinhoUseCase.adicionarProdutoCarrinho(produto, utilizadorId)
        }

//        suspend fun adicionarProdutoCarrinho(produto: Produto, utilizadorId: String) {
//            carrinhoUseCase.adicionarProdutoCarrinho(produto, utilizadorId)
//        }


}
