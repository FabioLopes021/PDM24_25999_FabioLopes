package com.example.store.data.remote.model

import com.example.store.domain.model.User
import com.example.store.domain.model.Utilizador

data class UserEntity(
    val email: String?
){
    fun toUtilizador(): User {
        return User(this.email)
    }
}