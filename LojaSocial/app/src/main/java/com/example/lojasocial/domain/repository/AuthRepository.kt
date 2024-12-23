package com.example.lojasocial.domain.repository

import com.example.lojasocial.domain.model.User
import com.example.lojasocial.navigation.Route
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase


interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun login(email: String, password: String): User?

    //Implementar depois
    //suspend fun sendEmailVerification()

    suspend fun register(email: String, password: String): User?

    //Implementar depois
    //suspend fun deleteUser()

    fun logout()
}