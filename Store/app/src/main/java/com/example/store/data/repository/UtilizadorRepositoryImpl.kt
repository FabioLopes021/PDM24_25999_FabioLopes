package com.example.store.data.repository

import android.util.Log
import com.example.store.data.remote.model.UtilizadorDto
import com.example.store.domain.model.Produto
import com.example.store.domain.model.Utilizador
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class UtilizadorRepositoryImpl {
    private val db: FirebaseFirestore = Firebase.firestore

    fun GetUtilizadoresCarrinhoPartilhado(): Flow<List<Utilizador>>{
        return db.collection("Utilizadores")
            .whereEqualTo("visibilidadeCarrinho", true)
            .snapshots()
            .map { snapshot ->
                snapshot.documents.map {document ->
                    document.toObject(Utilizador::class.java)
                }.filterNotNull()
            }
    }



    suspend fun getUserByEmail(email: String): Utilizador?{
        return try{
            val document = db.collection("Utilizadores")
                .whereEqualTo("email", email)
                .get()
                .await()

            val documentSnapshot = document.documents.firstOrNull()
            val user = documentSnapshot?.toObject(UtilizadorDto::class.java)

            user?.toUtilizador(documentSnapshot.id)
        }catch (ex: Exception){
            Log.d("Teste_UserFetch","Erro ao dar fetch ao utilizador ${ex.message}")
            null
        }
    }


    suspend fun addUtilizador(utilizador: Utilizador): Boolean{
        val utilizadoresRef = db.collection("Utilizadores")
        try {
            utilizadoresRef.add(utilizador)
                .await()

            return true
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao adicionar utilizador", e)
            return false
        }
    }


    suspend fun alterarVisibilidadeCarrinho(utilizador: Utilizador, utilizadorId: String): Boolean{
        try{
            val documentId = db.collection("Utilizadores")
                .document(utilizadorId)

            documentId.update("visibilidadeCarrinho", !utilizador.visibilidadeCarrinho).await()

            return true
        }catch (e: Exception){
            e.printStackTrace()
            return false
        }
    }

}