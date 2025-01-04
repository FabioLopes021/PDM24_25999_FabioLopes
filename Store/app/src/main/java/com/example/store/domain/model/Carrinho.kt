package com.example.store.domain.model

data class Carrinho(
    val carrinhoId: String,
    val utilizadorId: String,
    val utilizadorNome: String,
    val visibilidade: Boolean,
    val produtos: List<ProdutoCarrinho>
)