package com.example.store.data.repository

import android.util.Log
import com.example.store.data.remote.model.UtilizadorDto
import com.example.store.domain.model.Produto
import com.example.store.domain.model.Utilizador
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class UtilizadorRepositoryImpl {
    private val db: FirebaseFirestore = Firebase.firestore

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

}