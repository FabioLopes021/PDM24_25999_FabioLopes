package com.example.projetoexemplo.data.remote.model

import com.example.projetoexemplo.domain.model.Coin

data class CoinDto (
    val id: String,
    val name: String,
    val symbol: String,
){
    fun toCoin(): Coin {
        return Coin(id = id, name = name, symbol = symbol)
    }
}