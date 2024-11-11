package com.example.students.model

import kotlinx.coroutines.delay

class UserRepository {
    suspend fun fetchUserData(): UserData{
        delay(2000)
        return UserData(name = "Teste", 25999, "09-05-2023")
    }
}