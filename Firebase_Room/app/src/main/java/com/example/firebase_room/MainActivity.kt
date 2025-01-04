package com.example.firebase_room

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.firebase_room.models.Member
import com.example.firebase_room.navigation.AppNavigation
import com.example.firebase_room.ui.theme.Firebase_RoomTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Firebase_RoomTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNavigation(navController)
                }
            }
        }
    }
}

//@Composable
//fun UserScreen() {
//    // Estado para armazenar os dados do usuário
//    var user by remember { mutableStateOf<Member?>(null) }
//
//    // Carregar os dados do Firestore de forma assíncrona
//    LaunchedEffect(Unit) {
//        val db = Firebase.firestore
//        db.collection("Membro")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val name = document.getString("name") ?: ""
//                    val surename = document.getString("surename") ?: ""
//                    val cc = document.getString("cc") ?: ""
//                    val phone = document.getLong("phone")?.toInt() ?: 0
//                    val locality = document.getString("locality") ?: ""
//                    val notes = document.getString("notes") ?: ""
//                    val warnings = document.getString("warnings") ?: ""
//
//                    // Atualiza o estado com os dados do usuário
//                    user = Member(
//                        name = name,
//                        surename = surename,
//                        cc = cc,
//                        phone = phone,
//                        locality = locality,
//                        notes = notes,
//                        warnings = warnings
//                    )
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("TAG", "Error getting documents.", exception)
//            }
//    }
//
//    // Exibe os dados do usuário ou uma mensagem de carregamento
//    user?.let {
//        MemberDetails(member = it)
//    } ?: Text("Carregando dados...")
//}
//
//
//
//@Composable
//fun MemberDetails(member: Member) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp), // Espaçamento
//        horizontalAlignment = Alignment.CenterHorizontally, // Alinha tudo ao centro
//        verticalArrangement = Arrangement.spacedBy(8.dp) // Espaçamento entre os itens
//    ) {
//        Text(text = "Nome: ${member.name} ${member.surename}", style = MaterialTheme.typography.bodyLarge)
//        Text(text = "Cartão de Cidadão: ${member.cc}", style = MaterialTheme.typography.bodyMedium)
//        Text(text = "Telefone: ${member.phone}", style = MaterialTheme.typography.bodyMedium)
//        Text(text = "Localidade: ${member.locality}", style = MaterialTheme.typography.bodyMedium)
//        Text(text = "Notas: ${member.notes}", style = MaterialTheme.typography.bodyMedium)
//        Text(text = "Avisos: ${member.warnings}", style = MaterialTheme.typography.bodyMedium)
//    }
//}
//
//@Preview
//@Composable
//fun PreviewMemberDetails() {
//    val member = Member(
//        name = "João",
//        surename = "Silva",
//        cc = "12345678",
//        phone = 912345678,
//        locality = "Lisboa",
//        notes = "Cliente VIP",
//        warnings = "Nenhum"
//    )
//    MemberDetails(member = member)
//}

