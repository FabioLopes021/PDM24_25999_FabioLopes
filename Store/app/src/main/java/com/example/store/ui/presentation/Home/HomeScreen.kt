package com.example.store.ui.presentation.Home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.alignBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.store.domain.model.Utilizador
import com.example.store.navigation.Screen
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel, email:String?) {
    val produtos by homeViewModel.produtos.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var utilizador = remember { mutableStateOf<Utilizador?>(null) }


    LaunchedEffect(Unit) {
        if(email != null)
            utilizador.value = homeViewModel.fetchUtilizador(email)
    }

    Scaffold { innerpadding ->
        Column (modifier = Modifier
            //.padding(innerpadding)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TopAppBar(
                modifier =  Modifier.padding(innerpadding),
                    title = { Text("Bem-Vindo ${utilizador.value?.nome}")},
                    actions = {
                        IconButton(onClick = {
                            if(utilizador.value != null)
                                navController.navigate(Screen.ListaCarrinhos(utilizador.value?.email))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Buscar",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = {
                                try {
                                    coroutineScope.launch {
                                        if (utilizador.value != null) {
                                            homeViewModel.alterarVisibilidadeCarrinho(
                                                utilizador!!.value!!.email,
                                                context
                                            )
                                        }
                                    }
                                }catch (e: Exception){
                                    Log.d("Teste", "teste")
                                }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Partilhar Carrinho",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = {
                            coroutineScope.launch {
                                try {
                                    homeViewModel.logout()
                                    navController.navigate(Screen.Login)
                                }catch (e: Exception){
                                    Log.d("Teste", "teste")
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "SignOut",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = {
                            if(utilizador.value != null)
                                navController.navigate(Screen.CarrinhoUtilizador(utilizador.value?.email))
                        }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Buscar",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Cyan,
                        titleContentColor = Color.White
                    )
            )
            LazyColumn {
                items(produtos) { produto ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Foto do produto
                        Image(
                            painter = rememberAsyncImagePainter(model = produto.foto),
                            contentDescription = "Foto do produto",
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        // Nome e descrição do produto
                        Column(
                            modifier = Modifier
                                .weight(1f) // Ocupa o espaço restante
                        ) {
                            Text(
                                text = "Nome: ${produto.nome}",
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 1
                            )
                            Text(
                                text = produto.descricao ?: "Sem descrição",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                maxLines = 2
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        IconButton(onClick = {
                            try {
                                if(utilizador?.value?.id != null)
                                    coroutineScope.launch {
                                        homeViewModel.adicionarProdutoCarrinho(produto, utilizador!!.value!!.id)
                                    }
                            }catch (e: Exception){
                                Log.d("Teste", "teste")
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Partilhar Carrinho",
                                tint = Color.White
                            )
                        }

                        // Botão para adicionar ao carrinho
//                        Button(
//                            onClick = {
//                                if(utilizador?.value?.id != null)
//                                    coroutineScope.launch {
//                                        homeViewModel.adicionarProdutoCarrinho(produto, utilizador!!.value!!.id)
//                                    }
//                            },
//                            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
//                        ) {
//                            Text(text = "Add")
//                        }
                    }

                    Divider(color = Color.LightGray, thickness = 1.dp)
                }
            }
        }
    }
}