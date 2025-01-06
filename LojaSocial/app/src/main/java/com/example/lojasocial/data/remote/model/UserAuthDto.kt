package com.example.lojasocial.data.remote.model

import com.example.lojasocial.domain.model.User

class UserAuthDto (
    var email: String
){
    fun toUser(): User {
        return User(userId = "", email = email, name = "", surename = "", picture = "", roleId = 0)
    }
}