package com.example.lojasocial.data.repository

import android.util.Log
import com.example.lojasocial.data.remote.model.Userdto
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl(private val auth: FirebaseAuth): AuthRepository {
    override val currentUser = auth.currentUser

    override suspend fun register(email: String, password: String): User? {
        return try {
            val userDto = suspendCoroutine<Userdto?> { continuation ->
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUser = auth.currentUser
                            if (currentUser != null) {
                                continuation.resume(Userdto(currentUser.email.toString()))
                            } else {
                                continuation.resumeWithException(IllegalStateException("User not found after registration"))
                            }
                        } else {
                            continuation.resumeWithException(task.exception ?: Exception("Registration failed"))
                        }
                    }
            }
            userDto?.toUser()
        } catch (e: Exception) {
            throw IllegalArgumentException(e.message ?: "Erro no Registro")
        }
    }


//    override suspend fun register(email: String, password: String):User?{
//        try {
//            auth.createUserWithEmailAndPassword(email, password)
//        }catch (e: Exception){
//            throw IllegalArgumentException(e.message?: "Erro no Login")
//        }
//    }

    //Implementar depois
    //suspend fun sendEmailVerification()

//    override suspend fun login(email: String, password: String): User? {
//        var user: Userdto? = null
//        try {
//            auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener{task->
//                    if(task.isSuccessful){
//                        user = Userdto(currentUser!!.email.toString())
//                    }else{
//                        Log.d("Teste", task.exception?.message?:"Something went wrong")
//                    }
//                }
//        }catch (e: Exception){
//            throw IllegalArgumentException(e.message?: "Erro no Login")
//        }
//        if(user != null)
//            return user!!.toUser()
//        else
//            return null
//    }
    override suspend fun login(email: String, password: String): User? {
        return try {
            val userDto = suspendCoroutine<Userdto?> { continuation ->
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUser = auth.currentUser
                            if (currentUser != null) {
                                continuation.resume(Userdto(currentUser.email.toString()))
                            } else {
                                continuation.resumeWithException(IllegalStateException("User not found after login"))
                            }
                        } else {
                            continuation.resumeWithException(task.exception ?: Exception("Login failed"))
                        }
                    }
            }
            userDto?.toUser()
        } catch (e: Exception) {
            throw IllegalArgumentException(e.message ?: "Erro no Login")
        }
    }

    //Implementar depois
    //suspend fun deleteUser()

    override fun logout(){
        auth.signOut()
    }
}