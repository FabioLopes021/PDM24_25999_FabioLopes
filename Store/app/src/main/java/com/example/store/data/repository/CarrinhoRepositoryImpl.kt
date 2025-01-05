package com.example.store.data.repository

import android.util.Log
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
    private val produtos = MutableLiveData<Produto>()

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

    suspend fun addQuantideProduto(produto: ProdutoCarrinho, utilizadorId: String): Boolean {
        try{
            val documentId = db.collection("Utilizadores")
                .document(utilizadorId)
                .collection("Carrinho")
                .document(produto.id)

            documentId.update("quantidade", produto.quantidade + 1).await()

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
                    Log.d("Firestore", "Produtos encontrados: $produtos")
                    trySend(produtos)
                }
            }
        awaitClose { listenerRegistration.remove() }
    }


}