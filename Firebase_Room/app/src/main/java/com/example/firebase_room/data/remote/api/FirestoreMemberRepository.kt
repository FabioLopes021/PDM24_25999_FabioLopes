package com.example.firebase_room.data.remote.api

import com.example.firebase_room.domain.models.Member
import com.example.firebase_room.domain.repository.MemberRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreMemberRepository: MemberRepository {
    private val db = Firebase.firestore

    override suspend fun getAllMembers(): List<Member> {
        return try {
            val result = db.collection("Membro").get().await()
            result.map { document ->
                Member(
                    name = document.getString("name") ?: "",
                    surename = document.getString("surename") ?: "",
                    cc = document.getString("cc") ?: "",
                    phone = document.getLong("phone")?.toInt() ?: 0,
                    locality = document.getString("locality") ?: "",
                    notes = document.getString("notes") ?: "",
                    warnings = document.getString("warnings") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Para atualizações em tempo real
    fun getMembersRealtime(): Flow<List<Member>> = callbackFlow {
        val listener = db.collection("Membro")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }

                val members = snapshot?.map { document ->
                    Member(
                        name = document.getString("name") ?: "",
                        surename = document.getString("surename") ?: "",
                        cc = document.getString("cc") ?: "",
                        phone = document.getLong("phone")?.toInt() ?: 0,
                        locality = document.getString("locality") ?: "",
                        notes = document.getString("notes") ?: "",
                        warnings = document.getString("warnings") ?: ""
                    )
                } ?: emptyList()
                trySend(members).isSuccess
            }

        awaitClose { listener.remove() }
    }

}