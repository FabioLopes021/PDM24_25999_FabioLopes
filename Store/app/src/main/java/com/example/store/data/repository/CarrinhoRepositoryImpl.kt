package com.example.store.data.repository

import android.util.Log
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.lifecycle.MutableLiveData
import com.example.store.data.remote.model.ProdutoCarrinhoDto
import com.example.store.domain.model.ProdutoCarrinho
import com.example.store.domain.model.Produto
import com.example.store.domain.repository.ProdutoRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await


class CarrinhoRepositoryImpl {
    private val db: FirebaseFirestore = Firebase.firestore

    suspend fun getProdutoCarrinho(produto: Produto, idUtilizador: String): ProdutoCarrinho?{
        try {
            val query = db.collection("Utilizadores")
                .document(idUtilizador)
                .collection("Carrinho")
                .whereEqualTo("produtoId", produto.id)
                .get()
                .await()

            val document = query.documents.firstOrNull()

            document?.let {
                val produtoCarrinhoDto = it.toObject(ProdutoCarrinhoDto::class.java)
                return produtoCarrinhoDto?.toCarrinhoProduto(it.id)
            }
            return null
        }catch (ex: Exception){
            return null
        }
    }

    suspend fun quantidadeProdutoCarrinho(produto: ProdutoCarrinhoDto, utilizadorId: String): Int?{
        try {
            val document = db.collection("Utilizadores")
                .document(utilizadorId)
                .collection("Carrinho")
                .document(produto.produtoId)
                .get()
                .await()

            return document.getLong("quantidade")?.toInt()
        }catch (ex: Exception){
            return null
        }
    }

    suspend fun addProductToCart(produto: ProdutoCarrinhoDto, utilizadorId: String): Boolean {
        try{
            val documentId = db.collection("Utilizadores")
                .document(utilizadorId)
                .collection("Carrinho")
                .add(produto)
                .await()

            return true
        }catch (e: Exception){
            e.printStackTrace()
            return false
        }
    }

    suspend fun removerProdutoCarrinho(produto: ProdutoCarrinho, utilizadorId: String): Boolean {
        try{
            val documentId = db.collection("Utilizadores")
                .document(utilizadorId)
                .collection("Carrinho")
                .document(produto.id)
                .delete()
                .await()

            return true
        }catch (e: Exception){
            e.printStackTrace()
            return false
        }
    }

    suspend fun addQuantideProduto(produto: ProdutoCarrinho, utilizadorId: String): Boolean {
        try{
            val documentId = db.collection("Utilizadores")
                .document(utilizadorId)
                .collection("Carrinho")
                .document(produto.id)

            documentId.update("quantidade", produto.quantidade + 1).await()
            Log.d("ProdutosCarrinho", "Produtos Adicionados: ${produto.quantidade}")
            return true
        }catch (e: Exception){
            e.printStackTrace()
            return false
        }
    }

    suspend fun subQuantideProduto(produto: ProdutoCarrinho, utilizadorId: String): Boolean {
        try{
            val documentId = db.collection("Utilizadores")
                .document(utilizadorId)
                .collection("Carrinho")
                .document(produto.id)

            documentId.update("quantidade", produto.quantidade - 1).await()

            return true
        }catch (e: Exception){
            e.printStackTrace()
            return false
        }
    }

    suspend fun ProdutoExisteCarrinho(produto: ProdutoCarrinhoDto, utilizadorId: String): ProdutoCarrinho? {
        try{
            val documentId = db.collection("Utilizadores")
                .document(utilizadorId)
                .collection("Carrinho")
                .whereEqualTo("produtoId", produto.produtoId)
                .get()
                .await()

            val prod = documentId.documents.firstOrNull()?.toObject(ProdutoCarrinhoDto::class.java)
            if(prod == null)
                return null

            return ProdutoCarrinho(documentId.documents.firstOrNull()!!.id, prod.produtoId, prod.quantidade, prod.preco)
        }catch (e: Exception){
            e.printStackTrace()
            return null
        }
    }

    fun getProductsCarrinho(userId: String) =
        db.collection("Utilizadores")
            .document(userId)
            .collection("Carrinho")
            .snapshots()
            .map {Snapshot ->
        Snapshot.documents.map {document ->
            //val produto = document.toObject(Produto::class.java)
            val prod = document.toObject(ProdutoCarrinhoDto::class.java)

            prod?.let {
                it.toCarrinhoProduto(document.id)
            }
        }.filterNotNull()
    }


    fun observeProdutosCarrinho(userId: String): Flow<List<ProdutoCarrinhoDto>> = callbackFlow {
        val listenerRegistration = db.collection("Utilizadores").document(userId).collection("Carrinho")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    close(error)
                    Log.e("Firestore", "Erro ao obter os dados do carrinho: ${error.message}")
                    return@addSnapshotListener

                }
                if (querySnapshot != null) {
                    val produtos = querySnapshot.documents.mapNotNull { it.toObject(ProdutoCarrinhoDto::class.java) }
                    Log.d("ProdutosCarrinho", "Produtos encontrados funcao: observeProdutosCarrinho: $produtos")
                    trySend(produtos)
                }
            }
        awaitClose { listenerRegistration.remove() }
    }

}