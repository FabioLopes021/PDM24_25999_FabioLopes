package com.example.firebase_room.ui.presentation.member_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//@Composable
//fun MemberListScreen(navController: NavController, viewModel: MemberListViewModel = MemberListViewModel()) {
//    val members = viewModel.members.observeAsState(emptyList())
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        if (members.value.isEmpty()) {
//            Text("Carregando membros...")
//        } else {
//            members.value.forEach { member ->
//                Text(
//                    text = "${member.name} ${member.surename}",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                        .clickable {
//                            navController.navigate("member_details")
//                        }
//                )
//            }
//        }
//    }
//}

@Composable
fun MemberListScreen(navController: NavController, viewModel: MemberListViewModel = MemberListViewModel()) {
    val members = viewModel.members.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        if (members.value.isEmpty()) {
            Text("Carregando membros...")
        } else {
            members.value.forEach { member ->
                Text(
                    text = "${member.name} ${member.surename}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("member_details")
                        }
                )
            }
        }
    }
}
