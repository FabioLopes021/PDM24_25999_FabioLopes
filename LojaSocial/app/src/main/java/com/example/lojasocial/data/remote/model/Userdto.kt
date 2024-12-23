package com.example.lojasocial.data.remote.model

import com.example.lojasocial.domain.model.User

data class Userdto(
    var email: String
){
    fun toUser(): User{
        return User(name = "", apelido = "", email = this.email, picture = "", role = 0)
    }
}

