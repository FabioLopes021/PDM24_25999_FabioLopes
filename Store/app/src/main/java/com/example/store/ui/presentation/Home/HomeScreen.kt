package com.example.store.ui.presentation.Home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.store.domain.model.Produto
import com.example.store.domain.model.Utilizador
import com.example.store.navigation.Screen
import com.example.store.presentation.viewModels.AuthState
import com.example.store.presentation.viewModels.AuthViewModel
import kotlinx.coroutines.launch



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
            .padding(innerpadding)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(utilizador.value != null)
                Text(text = "testes ${utilizador!!.value!!.nome}")
            Text(text = "Home Page")
            LazyColumn {
                items(produtos) { produto ->
                    Text(text = "Nome: ${produto.nome}")
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


@Composable
fun UserProductsScreen(
    userName: String, // Nome do usuário
    produtos: List<Produto>, // Lista de produtos
    onProductClick: (Produto) -> Unit // Ação ao clicar em um produto
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Cabeçalho com o nome do usuário
        Text(
            text = "Bem-vindo, $userName!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        // Lista de produtos
        LazyColumn(
            modifier = Modifier.run {
                fillMaxSize()
                        .padding(8.dp)
            }
        ) {
            items(produtos) { produto ->
                ProductItem(produto = produto, onClick = { onProductClick(produto) })
            }
        }
    }
}

@Composable
fun ProductItem(produto: Produto, onClick: () -> Unit) {
    Card(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick), elevation = 4.dp, backgroundColor = Color.LightGray) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagem do produto (se houver)
            produto.imageUrl?.let { imageUrl ->
                Image(
                    painter = rememberImagePainter(data = imageUrl),
                    contentDescription = produto.nome,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Informações do produto
            Column {
                Text(text = produto.nome, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "Preço: R$${produto.preco}", fontSize = 14.sp, color = Color.DarkGray)
                Text(
                    text = produto.descricao ?: "Sem descrição disponível",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


