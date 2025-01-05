package com.example.store.data.remote.model

import com.example.store.domain.model.Utilizador

data class UtilizadorDto (
    val email: String,
    val morada: String,
    val nome: String,
    val visibilidadeCarrinho: Boolean,
){
    constructor() :  this("","","",false)
    fun toUtilizador(id: String): Utilizador{
        return Utilizador(id,email,morada,nome,visibilidadeCarrinho)
    }
}