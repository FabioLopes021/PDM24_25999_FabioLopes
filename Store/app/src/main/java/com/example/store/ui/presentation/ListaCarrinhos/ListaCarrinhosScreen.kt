package com.example.store.ui.presentation.ListaCarrinhos

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
import com.example.store.domain.model.Produto
import com.example.store.domain.model.Utilizador
import com.example.store.navigation.Screen
import com.example.store.ui.presentation.Home.HomeViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaCarrinhosScreen(navController: NavHostController, listaCarrinhosViewModel: ListaCarrinhosViewModel){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val utilizadores by listaCarrinhosViewModel.CarrinhosPartilhados.collectAsState()

    var email = remember { mutableStateOf("") }


    Scaffold { innerpadding ->
        Column (modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TopAppBar(
                modifier =  Modifier.padding(innerpadding),
                title = {"Carrinho Publicos"},
                actions = {
                    IconButton(onClick = {
//                        navController.popBackStack()
//                        navController.navigate(Screen.Home(utilizador.value?.email))
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
                items(utilizadores) { utilizador ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Nome e descrição do produto
                        Column(
                            modifier = Modifier
                                .weight(1f) // Ocupa o espaço restante
                        ) {
                            Text(
                                text = utilizador.nome,
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 1
                            )
                        }
                        IconButton(onClick = {
                            try {
                                //Navegação para o outro

                            }catch (e: Exception){
                                Log.d("Teste", "teste")
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Partilhar Carrinho",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    Divider(color = Color.LightGray, thickness = 1.dp) // Linha separadora entre itens
                }
            }



        }
    }
}

