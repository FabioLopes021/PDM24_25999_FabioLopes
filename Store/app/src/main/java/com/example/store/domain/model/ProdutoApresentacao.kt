package com.example.store.domain.model

data class ProdutoApresentacao(
    var produtoId: String,
    val nome: String,
    val descricao: String,
    val foto: String,
    val quantidade: Int,
    val preco: Double,
    val precoTotal: Double
){
    constructor() :  this("","","","",0,0.0, 0.0)
}
