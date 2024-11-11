package com.example.projetoexemplo.domain.use_case

import CoinRepository
import com.example.projetoexemplo.domain.model.CoinDetail

class GetCoinDetailUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(coinId: String): CoinDetail {
        return repository.getCoinDetail(coinId)
    }
}