package com.example.store.domain.use_case

import com.example.store.data.repository.ProdutoRepositoryImpl
import com.example.store.domain.model.Produto
import kotlinx.coroutines.flow.Flow


class ProdutoUseCase(private val repository: ProdutoRepositoryImpl) {
    operator fun invoke(): Flow<List<Produto>> {
        return repository.observeProdutos()
    }

    fun getProdutos(): Flow<List<Produto>>{
        return repository.getProducts()
    }

    suspend fun getProdutobyId(id: String): Produto? {
        return repository.getProdutoById(id)
    }

    fun getProdutoByIdFlow(produtoId: String): Flow<Produto>{
        return repository.getProdutoByIdFlow(produtoId)
    }

}