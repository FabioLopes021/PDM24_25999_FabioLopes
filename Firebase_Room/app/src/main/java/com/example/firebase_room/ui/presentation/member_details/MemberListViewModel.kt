package com.example.firebase_room.ui.presentation.member_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase_room.data.remote.api.FirestoreMemberRepository
import com.example.firebase_room.domain.models.Member
import com.example.firebase_room.domain.use_case.GetMemberUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch




class MemberListViewModel : ViewModel() {
    private val repository = FirestoreMemberRepository()

    private val _members = MutableStateFlow<List<Member>>(emptyList())
    val members: StateFlow<List<Member>> get() = _members

    init {
        fetchMembers()
    }

    private fun fetchMembers() {
        viewModelScope.launch {
            repository.getMembersRealtime().collect { fetchedMembers ->
                _members.value = fetchedMembers
            }
        }
    }
}

//class MemberListViewModel : ViewModel() {
//    private val repository = FirestoreMemberRepository()
//    private val getMembersUseCase = GetMemberUseCase(repository)
//
//    private val _members = MutableLiveData<List<Member>>()
//    val members: LiveData<List<Member>> get() = _members
//
//    init {
//        fetchMembers()
//    }
//
//    private fun fetchMembers() {
//        viewModelScope.launch {
//            _members.value = getMembersUseCase()
//        }
//    }
//}