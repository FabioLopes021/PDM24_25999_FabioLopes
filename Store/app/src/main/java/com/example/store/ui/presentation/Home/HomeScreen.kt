package com.example.store.ui.presentation.Home

import android.icu.text.CaseMap.Title
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.alignBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Util
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.store.domain.model.Produto
import com.example.store.domain.model.Utilizador
import com.example.store.navigation.Screen
import com.example.store.presentation.viewModels.AuthState
import com.example.store.presentation.viewModels.AuthViewModel
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
                                navController.navigate(Screen.CarrinhoUser(utilizador.value?.email))
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
            //Text(text = "Home Page")
//            LazyColumn {
//                items(produtos) { produto ->
//                    Row(modifier = Modifier.fillMaxWidth()) {
//                        Box(modifier = Modifier.fillMaxSize()){
//                            Text(modifier = Modifier.alignBy(
//
//                            ), text = "Nome: ${produto.nome}")
//                    }
//                    }
//
//                }
//            }
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

                        // Botão para adicionar ao carrinho
                        Button(
                            onClick = {
                                if(utilizador?.value?.id != null)
                                    coroutineScope.launch {
                                        homeViewModel.adicionarProdutoCarrinho(produto, utilizador!!.value!!.id)
                                    }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
                        ) {
                            Text(text = "Add")
                        }
                    }

                    Divider(color = Color.LightGray, thickness = 1.dp) // Linha separadora entre itens
                }
            }
            //Text(text = email)
            Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Black),onClick = {
                coroutineScope.launch {
                    try {
                        homeViewModel.logout()
                        navController.navigate(Screen.Login)
                    }catch (e: Exception){
                        Log.d("Teste", "teste")
                    }
                }
            }){
                Text("Signout")
            }
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreen(
//    navController: NavHostController,
//    homeViewModel: HomeViewModel,
//    email: String?
//) {
//    val produtos by homeViewModel.produtos.collectAsState(emptyList()) // Coleta a lista de produtos
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//    val utilizador = remember { mutableStateOf<Utilizador?>(null) }
//
//    // Carregar o utilizador quando a tela for exibida
//    LaunchedEffect(Unit) {
//        email?.let {
//            utilizador.value = homeViewModel.fetchUtilizador(it)
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = utilizador.value?.nome?.let { "Bem-vindo, $it!" } ?: "Home Page",
//                        style = MaterialTheme.typography.titleLarge // Substituto para h6 no Material 3
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.Cyan, // Cor de fundo
//                    titleContentColor = Color.White // Cor do texto do título
//                )
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.SpaceBetween,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // Lista de produtos
////            LazyColumn(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .weight(1f) // Atribui peso para ocupar o espaço disponível
////                    .padding(horizontal = 16.dp)
////            ) {
////                items(produtos) { produto ->
////                    ProductItem(produto)
////                }
////            }
//            LazyColumn {
//                items(produtos) { produto ->
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        // Exibe a imagem do produto
//                        Image(
//                            painter = rememberAsyncImagePainter(produto.foto),
//                            contentDescription = "Foto do Produto",
//                            modifier = Modifier
//                                .size(64.dp)
//                                .clip(CircleShape)
//                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)),
//                            contentScale = ContentScale.Crop
//                        )
//
//                        // Exibe o nome do produto
//                        Text(
//                            text = produto.nome,
//                            style = MaterialTheme.typography.bodyLarge,
//                            color = MaterialTheme.colorScheme.onSurface,
//                            modifier = Modifier.weight(1f)
//                        )
//                    }
//                }
//            }
//
//            // Botão de logout
//            Button(
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
//                onClick = {
//                    coroutineScope.launch {
//                        try {
//                            homeViewModel.logout()
//                            navController.navigate(Screen.Login) {
//                                popUpTo(0) // Remove todas as telas anteriores
//                            }
//                        } catch (e: Exception) {
//                            Log.e("HomeScreen", "Erro ao fazer logout: ${e.message}")
//                        }
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text("Signout", color = Color.White)
//            }
//        }
//    }
//}
//
//@Composable
//fun ProductItem(produto: Produto) {
//    ElevatedCard(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//        ) {
//            Text(text = "Nome: ${produto.nome}", style = MaterialTheme.typography.titleMedium)
//            Text(text = "Preço: ${produto.preco}€", style = MaterialTheme.typography.bodyMedium)
//            produto.descricao?.let {
//                Text(
//                    text = it,
//                    style = MaterialTheme.typography.bodySmall,
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
//        }
//    }
//}


