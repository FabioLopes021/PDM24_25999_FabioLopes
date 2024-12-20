package com.example.projetoexemplo.presentation.sreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projetoexemplo.Screen
import com.example.projetoexemplo.domain.model.Coin
import com.example.projetoexemplo.presentation.coin_list.CoinListViewModel

//@Composable
//fun CoinListScreen(
//    viewModel: CoinListViewModel = viewModel(),
//    onCoinSelected: (String) -> Unit
//) {
//    // Chama fetchCoins uma vez quando a CoinListScreen é composta
//    LaunchedEffect(Unit) {
//        viewModel.fetchCoins()
//    }
//
//    // Observa a lista de moedas do ViewModel
//    val coinList = viewModel.coins.collectAsState()
//
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        items(coinList.value) { coin ->
//            CoinItem(coin = coin, onCoinSelected = onCoinSelected)
//        }
//    }
//}

@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel,
    navController: NavHostController,
) {
    // Chama fetchCoins uma vez quando a CoinListScreen é composta
    LaunchedEffect(Unit) {
        viewModel.fetchCoins()
    }

    // Observa a lista de moedas do ViewModel
    val coinList = viewModel.coins.collectAsState()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(coinList.value) { coin ->
            CoinItem(coin = coin, navController)
        }
    }
}


@Composable
fun CoinItem(coin: Coin, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.CoinDetails(coin.id)) }
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Cyan
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = coin.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Symbol: ${coin.symbol}",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }
    }
}