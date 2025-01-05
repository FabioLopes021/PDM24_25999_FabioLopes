package com.example.store.domain.use_case

import com.example.store.data.repository.ProdutoRepositoryImpl
import com.example.store.domain.model.Produto
import com.example.store.domain.repository.ProdutoRepository
import kotlinx.coroutines.flow.Flow


class ProdutoUseCase(private val repository: ProdutoRepositoryImpl) {
    operator fun invoke(): Flow<List<Produto>> {
        return repository.observeProdutos()
    }

    fun getProdutos(): Flow<List<Produto>>{
        return repository.getProducts()
    }
}