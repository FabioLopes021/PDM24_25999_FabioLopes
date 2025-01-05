package com.example.store.data.repository

import com.example.store.data.remote.api.AuthService
import com.example.store.domain.model.User
import com.example.store.domain.repository.AuthRepository

class AuthRepositoryImpl: AuthRepository {

    override suspend fun login(email: String, password: String): User {
        val result = AuthService.login(email, password)
        return User(email = result.user?.email ?: "")
    }

    override suspend fun register(email: String, password: String): User {
        val result = AuthService.register(email, password)
        return User(email = result.user?.email ?: "")
    }

    override fun logout() {
        AuthService.logout()
    }

    override fun getCurrentUser(): User? {
        val email = AuthService.getCurrentUser()
        return if (email != null) User(email) else null
    }
}