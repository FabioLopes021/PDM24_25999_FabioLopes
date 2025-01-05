package com.example.store.domain.model

data class Produto(
    val id: String,
    val nome: String,
    val descricao: String,
    val foto: String,
    val preco: Double
)  {
    constructor() : this("","", "", "", 0.0)
}