package com.example.store.domain.use_case

import android.text.BoringLayout
import com.example.store.data.remote.model.ProdutoCarrinhoDto
import com.example.store.data.repository.CarrinhoRepositoryImpl
import com.example.store.domain.model.Produto
import com.example.store.domain.model.ProdutoCarrinho
import kotlinx.coroutines.flow.Flow


class CarrinhoUseCase(private val repository: CarrinhoRepositoryImpl){


    suspend fun adicionarProdutoCarrinho(produto: Produto, id: String): Boolean {
        var produtoAdd =  ProdutoCarrinhoDto(produto.id, 1, produto.preco)
        var verificacao = produtoExisteCarrinho(produtoAdd, id)
        if(verificacao != null){
            return repository.addQuantideProduto(verificacao, id)
        }else{
            return repository.addProductToCart(produtoAdd, id)
        }
    }

    suspend fun produtoExisteCarrinho(produto: ProdutoCarrinhoDto, id: String): ProdutoCarrinho?{
        return repository.ProdutoExisteCarrinho(produto ,id)
    }

    fun getDadosCarrinho(id: String): Flow<List<ProdutoCarrinho>> {
        return repository.getProductsCarrinho(id)
    }

    fun observeDadosCarrinho(id: String): Flow<List<ProdutoCarrinhoDto>> {
        return repository.observeProdutosCarrinho(id)
    }



}