package com.example.store.ui.presentation.CarrinhoUtilizador

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import com.example.store.domain.model.Produto
import com.example.store.domain.model.Utilizador
import com.example.store.navigation.Screen
import com.example.store.ui.presentation.Home.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrinhoUtilizadorScreen(navController: NavHostController, homeViewModel: HomeViewModel, email: String?) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val carrinho by homeViewModel.produtosCarrinho.collectAsState()

    var utilizador = remember { mutableStateOf<Utilizador?>(null) }

    LaunchedEffect(email) {
        email?.let{
            Log.d("Produtos Carrinho","Passou aqui")
            utilizador.value = homeViewModel.fetchUtilizador(email)
            homeViewModel.clearCarrinho()
            Log.d("Produtos Carrinho", "${utilizador.value!!.nome}")
            if(utilizador.value != null){
                homeViewModel.observeCarrinho(utilizador.value!!.id)
                Log.d("Produtos Carrinho","Passou aqui, Atualizou o utilizador!")
            }
        }
    }

    Scaffold { innerpadding ->
        Column (modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TopAppBar(
                modifier =  Modifier.padding(innerpadding),
                title = {"Carrinho de Compras"},
                actions = {
                    IconButton(onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.Home(utilizador.value?.email))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Home,
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
                items(carrinho) { produto ->
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
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = produto.nome,
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 1
                            )
                            Text(
                                text = produto.descricao ?: "Sem descrição",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                maxLines = 2
                            )
                            Text(
                                text = "Quantidade: ${produto.quantidade}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                maxLines = 2
                            )
                        }
                        IconButton(onClick = {
                            try {
                                coroutineScope.launch {
                                    homeViewModel.removerProdutoCarrinho(Produto(produto.produtoId,produto.nome,produto.descricao,produto.foto,produto.preco), utilizador!!.value!!.id)
                                }
                            }catch (e: Exception){
                                Log.d("Teste", "teste")
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Partilhar Carrinho",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = {
                            try {
                                if(utilizador?.value?.id != null)
                                    coroutineScope.launch {
                                        homeViewModel.adicionarProdutoCarrinho(Produto(produto.produtoId,produto.nome,produto.descricao,produto.foto,produto.preco), utilizador!!.value!!.id)
                                    }
                            }catch (e: Exception){
                                Log.d("Teste", "teste")
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "Partilhar Carrinho",
                                tint = Color.White
                            )
                        }
                    }
                    Divider(color = Color.LightGray, thickness = 1.dp)
                }
            }
        }
    }
}

