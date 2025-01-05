package com.example.store.domain.model

data class Utilizador(
    var id: String,
    var nome: String,
    var email: String,
    var morada: String,
    var telemovel: Int,
    var carrinhoId: String
){
    constructor() : this("","", "", "", 0,"")
}