package com.example.store.domain.repository

import com.example.store.domain.model.Produto
import kotlinx.coroutines.flow.Flow

interface ProdutoRepository {
    fun observeProdutos(): Flow<List<Produto>>
    fun getProducts(): Flow<List<Produto>>
}