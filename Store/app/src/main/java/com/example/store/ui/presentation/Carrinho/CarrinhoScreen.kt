package com.example.store.ui.presentation.Carrinho

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
import androidx.compose.material.icons.filled.ArrowBack
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
fun CarrinhoScreen(navController: NavHostController, carrinhoViewModel: CarrinhoViewModel, email:String?) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val carrinho by carrinhoViewModel.produtosCarrinho.collectAsState()

    var utilizador = remember { mutableStateOf<Utilizador?>(null) }

    LaunchedEffect(email) {
        email?.let{
            if(email != null)
                utilizador.value = carrinhoViewModel.fetchUtilizador(email)
            if(utilizador.value?.id != null)
                carrinhoViewModel.observeCarrinho(utilizador.value?.id!!)
        }
    }

    Scaffold { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                modifier = Modifier.padding(innerpadding),
                title = { "Carrinho de Compras" },
                actions = {
                    IconButton(onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.ListaCarrinhos(utilizador.value?.email))
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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

            //Lista

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

                        // Nome e descrição do produto
                        Column(
                            modifier = Modifier
                                .weight(1f) // Ocupa o espaço restante
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
                    }
                    Divider(color = Color.LightGray, thickness = 1.dp)
                }
            }
        }
    }

}