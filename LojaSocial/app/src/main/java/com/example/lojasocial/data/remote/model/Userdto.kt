package com.example.lojasocial.data.remote.model

import com.example.lojasocial.domain.model.User

data class Userdto(
    var email: String
){
    fun toUser(): User{
        return User(userId = "", email = email, name = "", surename = "", picture = "", roleId = 0)
    }
}

