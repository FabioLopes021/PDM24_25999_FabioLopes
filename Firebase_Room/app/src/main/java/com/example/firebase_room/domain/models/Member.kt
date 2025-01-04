package com.example.firebase_room.domain.models

data class Member(
    val name: String,
    val surename: String,
    val cc: String,
    val phone: Int,
    val locality: String,
    val notes: String,
    val warnings: String,
)