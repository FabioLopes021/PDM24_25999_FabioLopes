package com.example.firebase_room.ui.presentation.member_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MemberDetailsScreen(viewModel: MemberDetailsViewModel = MemberDetailsViewModel()) {
    val member = viewModel.selectedMember

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Nome: ${member.name} ${member.surename}")
        Text("Cartão de Cidadão: ${member.cc}")
        Text("Telefone: ${member.phone}")
        Text("Localidade: ${member.locality}")
        Text("Notas: ${member.notes}")
        Text("Avisos: ${member.warnings}")
    }
}