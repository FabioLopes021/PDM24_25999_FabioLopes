package com.example.lojasocial.domain.model


data class User(
    val email: String,
    val name: String,
    val apelido: String,
    val picture: String,
    val role: Int
)