package com.example.store.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.store.domain.model.Produto
import com.example.store.domain.repository.ProdutoRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class ProdutoRepositoryImpl: ProdutoRepository {
    private val db: FirebaseFirestore = Firebase.firestore
    private val produtos = MutableLiveData<Produto>()

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


    override fun observeProdutos(): Flow<List<Produto>> = callbackFlow {
        val listenerRegistration = db.collection("Produtos")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val produtos = querySnapshot.documents.mapNotNull { it.toObject(Produto::class.java) }
                    trySend(produtos)
                }
            }
        awaitClose { listenerRegistration.remove() }
    }

    override fun getProducts() = db.collection("Produtos").snapshots().map {Snapshot ->
        //Snapshot.toObjects(Produto::class.java)
        Snapshot.documents.map {document ->
            val produto = document.toObject(Produto::class.java)
            Log.d("ProdutosCarrinho", "Produtos encontrados no repositorio produtos: $produtos")
            produto?.copy(id = document.id)
        }.filterNotNull()
    }

    fun getProdutoByIdFlow(produtoId: String): Flow<Produto> = callbackFlow {
        val documentRef = db.collection("Produtos").document(produtoId)
        val listener = documentRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            snapshot?.toObject(Produto::class.java)?.let { produto ->
                trySend(produto).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    suspend fun getProdutoById(produtoId: String): Produto? {
        return try {
            val document = db.collection("Produtos")
                .document(produtoId)
                .get()
                .await()

            document.toObject(Produto::class.java)?.copy(id = document.id)
        } catch (e: Exception) {
            null
        }
    }

}