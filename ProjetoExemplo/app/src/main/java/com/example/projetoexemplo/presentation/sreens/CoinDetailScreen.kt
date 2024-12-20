package com.example.projetoexemplo.presentation.sreens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projetoexemplo.Screen
import com.example.projetoexemplo.presentation.coin_list.CoinDetailViewModel


//@Composable
//fun CoinDetailScreen(
//    viewModel: CoinDetailViewModel,
//    coinId: String,
//    onBackPressed: () -> Unit
//) {
//    // Chama fetchCoinDetail uma vez quando a CoinDetailScreen é composta
//    LaunchedEffect(coinId) {
//        Log.d("CoinDetailScreen", "Fetching details for coin: $coinId")
//        viewModel.fetchCoinDetail(coinId)  // Chama o método para buscar os detalhes da moeda
//    }
//
//    // Observa o estado do CoinDetail
//    val coinDetail = viewModel.coinDetail.collectAsState().value
//
//    // Verifica se os dados estão carregados
//    if (coinDetail == null) {
//        // Mostra um loading enquanto os dados não são carregados
//        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
//    } else {
//        // Exibe os detalhes da moeda
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Text(
//                text = coinDetail.name,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black
//            )
//            Text(
//                text = "Description:",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.SemiBold,
//                color = Color.Gray
//            )
//            Text(
//                text = coinDetail.description,
//                fontSize = 16.sp,
//                color = Color.Black
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Botão de voltar
//            Button(
//                onClick = onBackPressed,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            ) {
//                Text("Back")
//            }
//        }
//    }
//}

@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel,
    coinId: String,
    navController: NavHostController,
) {
    // Chama fetchCoinDetail uma vez quando a CoinDetailScreen é composta
    LaunchedEffect(coinId) {
        Log.d("CoinDetailScreen", "Fetching details for coin: $coinId")
        viewModel.fetchCoinDetail(coinId)  // Chama o método para buscar os detalhes da moeda
    }

    // Observa o estado do CoinDetail
    val coinDetail = viewModel.coinDetail.collectAsState().value

    // Verifica se os dados estão carregados
    if (coinDetail == null) {
        // Mostra um loading enquanto os dados não são carregados
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        // Exibe os detalhes da moeda
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = coinDetail.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Description:",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Text(
                text = coinDetail.description,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Botão de voltar
            Button(
                onClick = { navController.navigate(Screen.CoinList) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Back")
            }
        }
    }
}