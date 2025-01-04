package com.example.store.domain.repository

import com.example.store.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): User
    suspend fun register(email: String, password: String): User
    fun logout()
    fun getCurrentUser(): User?
}