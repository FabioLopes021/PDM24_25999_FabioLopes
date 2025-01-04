package com.example.firebase_room.domain.repository

import com.example.firebase_room.domain.models.Member

interface MemberRepository {
    suspend fun getAllMembers(): List<Member>
}