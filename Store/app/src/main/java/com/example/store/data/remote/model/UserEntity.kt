package com.example.store.data.remote.model

import com.example.store.domain.model.User


data class UserEntity(
    val email: String?
){
    fun toUtilizador(): User {
        return User(this.email)
    }
}