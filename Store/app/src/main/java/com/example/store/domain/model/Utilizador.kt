package com.example.store.domain.model

data class Utilizador(
    val id: String,
    val email: String,
    val morada: String,
    val nome: String,
    val visibilidadeCarrinho: Boolean,
){
    constructor() :  this("","","","",false)
}