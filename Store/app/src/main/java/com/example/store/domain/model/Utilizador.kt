package com.example.store.domain.model

data class Utilizador(
    val nome: String,
    val email: String,
    val morada: String,
    val telemovel: Int,
    val carrinhoId: String
)