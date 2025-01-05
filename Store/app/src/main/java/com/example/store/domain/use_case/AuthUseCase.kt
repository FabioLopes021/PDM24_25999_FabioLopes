package com.example.store.domain.use_case

import com.example.store.domain.model.User
import com.example.store.domain.repository.AuthRepository

class AuthUseCase(private  val repository: AuthRepository){
    suspend fun login(email: String, password: String): User{
        return repository.login(email, password)
    }

    suspend fun register(email: String, password: String): User {
        return repository.register(email, password)
    }

    suspend fun logout(){
        repository.logout()
    }

    suspend fun getCurrentUser(): String?{
        val user: User? = repository.getCurrentUser()
        if(user == null)
            return ""
        else
            return user.email
    }
}