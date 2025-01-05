package com.example.store.domain.model

data class ProdutoCarrinho(
    val id: String,
    val produtoId: String,
    val quantidade: Int,
    val preco: Double
){
    constructor() :  this("","",0,0.0)
}