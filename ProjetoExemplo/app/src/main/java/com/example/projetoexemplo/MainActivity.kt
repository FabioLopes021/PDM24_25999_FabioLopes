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
import androidx.navigation.compose.rememberNavController
import com.example.projetoexemplo.domain.model.Coin
import com.example.projetoexemplo.presentation.coin_list.CoinDetailViewModel
import com.example.projetoexemplo.presentation.coin_list.CoinListViewModel
import com.example.projetoexemplo.presentation.sreens.CoinDetailScreen
import com.example.projetoexemplo.presentation.sreens.CoinListScreen
import com.example.projetoexemplo.ui.theme.ProjetoExemploTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetoExemploTheme {
                MainScreenTeste()
            }
        }
    }
}

@Composable
fun MainScreenTeste( ) {
    val navController = rememberNavController();
    SetupNavGraph(navController)


}


//@Composable
//fun MainScreen( ) {
//    var selectedCoinId by remember { mutableStateOf<String?>(null) }
//
//    if (selectedCoinId == null) {
//        val coinListViewModel: CoinListViewModel = viewModel()
//        CoinListScreen(coinListViewModel) { coinId ->
//            selectedCoinId = coinId
//        }
//    } else {
//        val coinDetailViewModel: CoinDetailViewModel = viewModel()
//        CoinDetailScreen(coinDetailViewModel, selectedCoinId!!) {
//            selectedCoinId = null
//        }
//    }
//}









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