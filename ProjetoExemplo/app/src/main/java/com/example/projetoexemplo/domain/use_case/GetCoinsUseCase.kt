package com.example.projetoexemplo.domain.use_case;

import CoinRepository
import com.example.projetoexemplo.data.repository.CoinRepositoryImpl
import com.example.projetoexemplo.domain.model.Coin


class GetCoinsUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke():List<Coin>
    {
        return repository.getCoins()
    }
}