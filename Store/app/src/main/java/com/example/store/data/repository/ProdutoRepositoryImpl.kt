package com.example.store.data.repository

import com.example.store.domain.model.Produto
import com.example.store.domain.repository.ProdutoRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class ProdutoRepositoryImpl: ProdutoRepository {
    val db: FirebaseFirestore = Firebase.firestore

    suspend fun addProducts(produto: Produto): DocumentReference? {
        try{
            val documentId = db.collection("produtos")
                .add(produto)
                .await()
            return documentId
        }catch (e: Exception){
            e.printStackTrace()
            return null
        }
    }

    suspend fun readProducts(produto: Produto) {
        try{
            val lista: List<Produto>

        }
    }


}