package com.example.store.domain.model

data class Utilizador(
    val id: String,
    val nome: String,
    val email: String,
    val morada: String,
    val telemovel: Int,
    val carrinhoId: String
){
    constructor() : this("","", "", "", 0,"")
}