package com.example.projetoexemplo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projetoexemplo.domain.model.Coin
import com.example.projetoexemplo.presentation.coin_list.CoinDetailViewModel
import com.example.projetoexemplo.presentation.coin_list.CoinListViewModel
import com.example.projetoexemplo.ui.theme.ProjetoExemploTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetoExemploTheme {
                MainScreen()
            }
        }
    }
}



@Composable
fun MainScreen( ) {
    var selectedCoinId by remember { mutableStateOf<String?>(null) }

    if (selectedCoinId == null) {
        val coinListViewModel: CoinListViewModel = viewModel()
        CoinListScreen(coinListViewModel) { coinId ->
            selectedCoinId = coinId
        }
    } else {
        val coinDetailViewModel: CoinDetailViewModel = viewModel()
        CoinDetailScreen(coinDetailViewModel, selectedCoinId!!) {
            selectedCoinId = null
        }
    }
}


@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel,
    coinId: String,
    onBackPressed: () -> Unit
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
                onClick = onBackPressed,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Back")
            }
        }
    }
}

@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = viewModel(),
    onCoinSelected: (String) -> Unit
) {
    // Chama fetchCoins uma vez quando a CoinListScreen é composta
    LaunchedEffect(Unit) {
        Log.d("CoinListScreen", "Calling fetchCoins from CoinListScreen")
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
            CoinItem(coin = coin, onCoinSelected = onCoinSelected)
        }
    }
}


@Composable
fun CoinItem(coin: Coin, onCoinSelected: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCoinSelected(coin.id) }
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




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjetoExemploTheme {
        Greeting("Android")
    }
}