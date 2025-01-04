package com.example.firebase_room.domain.use_case

import com.example.firebase_room.domain.models.Member
import com.example.firebase_room.domain.repository.MemberRepository

class GetMemberUseCase(private val repository: MemberRepository){
    suspend operator fun invoke(): List<Member> = repository.getAllMembers()
}