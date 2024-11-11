package com.example.projetoexemplo.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetoexemplo.data.remote.api.RetrofitInstance
import com.example.projetoexemplo.data.repository.CoinRepositoryImpl
import com.example.projetoexemplo.domain.model.Coin
import com.example.projetoexemplo.domain.use_case.GetCoinsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class CoinListViewModel : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = CoinRepositoryImpl(api)
    private val getCoinsUseCase = GetCoinsUseCase(repository)

    val coins = MutableStateFlow<List<Coin>>(emptyList())


    fun fetchCoins() {
        viewModelScope.launch {
            try {
                coins.value = getCoinsUseCase()
            } catch (e: Exception) {
                coins.value = emptyList()
            }
        }
    }
}