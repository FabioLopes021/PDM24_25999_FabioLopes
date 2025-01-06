package com.example.lojasocial.domain.model


data class User(
    val userId: String,
    val email: String,
    val name: String,
    val surename: String,
    val picture: String,
    val roleId: Int
)