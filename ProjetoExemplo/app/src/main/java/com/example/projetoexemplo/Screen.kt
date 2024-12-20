package com.example.projetoexemplo

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object CoinList: Screen()
    @Serializable
    data class CoinDetails(
        val url: String
    ): Screen()
}