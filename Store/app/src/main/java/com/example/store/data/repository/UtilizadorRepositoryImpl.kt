package com.example.store.data.repository

import android.util.Log
import com.example.store.domain.model.Produto
import com.example.store.domain.model.Utilizador
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class UtilizadorRepositoryImpl {
    private val db: FirebaseFirestore = Firebase.firestore

    suspend fun getUserById(email: String): Utilizador?{
        return try{
            val document = db.collection("Utilizadores")
                .whereEqualTo("email", email)
                .get()
                .await()

            val utilizador = document.documents.firstOrNull()?.toObject(Utilizador::class.java)

            utilizador?.copy(id = document.documents.firstOrNull()?.id.toString())
        }catch (ex: Exception){
            Log.d("Teste_UserFetch","Erro ao dar fetch ao utilizador ${ex.message}")
            null
        }
    }

}