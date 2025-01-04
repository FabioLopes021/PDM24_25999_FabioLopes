package com.example.firebase_room.data.repository

import com.example.firebase_room.data.remote.api.FirestoreMemberRepository
import com.example.firebase_room.domain.models.Member
import com.example.firebase_room.domain.repository.MemberRepository

class MemberRepositoryImpl(private val remoteRepository: FirestoreMemberRepository) :
    MemberRepository {
    override suspend fun getAllMembers(): List<Member> = remoteRepository.getAllMembers()
}