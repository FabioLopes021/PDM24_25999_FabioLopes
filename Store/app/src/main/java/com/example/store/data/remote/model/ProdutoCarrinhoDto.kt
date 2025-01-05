package com.example.store.data.remote.model

import com.example.store.domain.model.ProdutoApresentacao
import com.example.store.domain.model.ProdutoCarrinho

data class ProdutoCarrinhoDto(
    val produtoId: String,
    val quantidade: Int,
    val preco: Double
){
    constructor() :  this("",0,0.0)
    fun toCarrinhoProduto(id: String): ProdutoCarrinho{
        return ProdutoCarrinho(id,produtoId, quantidade, preco)
    }
    fun toProdutosApresentacao(nome: String, descricao: String, foto: String, preco1: Double ): ProdutoApresentacao{
        return ProdutoApresentacao(produtoId, nome, descricao, foto, quantidade, preco1, preco1 * quantidade)
    }
}
