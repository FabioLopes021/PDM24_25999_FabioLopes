package com.example.store.domain.use_case

import android.text.BoringLayout
import android.util.Log
import com.example.store.data.remote.model.ProdutoCarrinhoDto
import com.example.store.data.repository.CarrinhoRepositoryImpl
import com.example.store.domain.model.Produto
import com.example.store.domain.model.ProdutoCarrinho
import com.example.store.domain.model.Utilizador
import kotlinx.coroutines.flow.Flow


class CarrinhoUseCase(private val repository: CarrinhoRepositoryImpl){


    suspend fun adicionarProdutoCarrinho(produto: Produto, id: String): Boolean {
        var produtoAdd =  ProdutoCarrinhoDto(produto.id, 1, produto.preco)
        var verificacao = produtoExisteCarrinho(produtoAdd, id)
        if(verificacao != null){
            Log.d("Carrinho", "Adicionar  -> Verificaçao != NULL")
            return repository.addQuantideProduto(verificacao, id)
        }else{
            Log.d("Carrinho", "Adicionar  -> Verificaçao == NULL")
            return repository.addProductToCart(produtoAdd, id)
        }
    }

    suspend fun removerProdutoCarrinho(produto: Produto, idUtilizador: String): Boolean {
        var produtoRmv =  repository.getProdutoCarrinho(produto, idUtilizador)

        if(produtoRmv != null){
            if (produtoRmv.quantidade > 1){
                Log.d("Carrinho", "Remover  -> quantiadade > 1")
                repository.subQuantideProduto(produtoRmv, idUtilizador)
            }
            else{
                Log.d("Carrinho", "Remover  -> quantidade < 1")
                repository.removerProdutoCarrinho(produtoRmv, idUtilizador)
            }

            return true
        }else{
            Log.d("Carrinho", "Remover  -> Verificaçao == NULL")
            return false
        }
    }

    suspend fun produtoExisteCarrinho(produto: ProdutoCarrinhoDto, id: String): ProdutoCarrinho?{
        return repository.ProdutoExisteCarrinho(produto ,id)
    }

    fun getDadosCarrinho(id: String): Flow<List<ProdutoCarrinho>> {
        return repository.getProductsCarrinho(id)
    }

    fun observeDadosCarrinho(id: String): Flow<List<ProdutoCarrinhoDto>> {
        Log.d("ProdutosCarrinho", "Produtos encontrados funcao: Passou pelo use case")
        return repository.observeProdutosCarrinho(id)
    }



}