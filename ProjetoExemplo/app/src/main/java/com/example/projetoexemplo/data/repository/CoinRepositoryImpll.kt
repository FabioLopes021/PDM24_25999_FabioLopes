package com.example.projetoexemplo.data.repository

import CoinRepository
import com.example.projetoexemplo.data.remote.api.CoinPaprikaApi
import com.example.projetoexemplo.domain.model.Coin
import com.example.projetoexemplo.domain.model.CoinDetail


class CoinRepositoryImpl(private val api: CoinPaprikaApi) : CoinRepository {
    override suspend fun getCoins(): List<Coin> {
        return api.getCoins().map { it.toCoin() }
    }

    override suspend fun getCoinDetail(coinId: String): CoinDetail {
        return api.getCoinDetail(coinId).toCoinDetail()
    }

}

