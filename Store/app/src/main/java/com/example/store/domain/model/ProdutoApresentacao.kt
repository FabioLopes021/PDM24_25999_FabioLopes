package com.example.store.domain.model

data class ProdutoApresentacao(
    var produtoId: String,
    var nome: String,
    var descricao: String,
    var foto: String,
    var quantidade: Int,
    var preco: Double,
    var precoTotal: Double
){
    constructor() :  this("","","","",0,0.0, 0.0)
}
