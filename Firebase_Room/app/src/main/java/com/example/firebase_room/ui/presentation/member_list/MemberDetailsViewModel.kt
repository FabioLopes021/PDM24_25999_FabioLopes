package com.example.firebase_room.ui.presentation.member_list

import androidx.lifecycle.ViewModel
import com.example.firebase_room.domain.models.Member

class MemberDetailsViewModel : ViewModel() {
    val selectedMember = Member(
        name = "João",
        surename = "Silva",
        cc = "12345678",
        phone = 912345678,
        locality = "Lisboa",
        notes = "Cliente VIP",
        warnings = "Nenhum"
    )
}