package com.example.projetoexemplo

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.projetoexemplo.presentation.coin_list.CoinDetailViewModel
import com.example.projetoexemplo.presentation.coin_list.CoinListViewModel
import com.example.projetoexemplo.presentation.sreens.CoinDetailScreen
import com.example.projetoexemplo.presentation.sreens.CoinListScreen
import kotlinx.serialization.Serializable


@SuppressLint("RestrictedApi")
@Composable
fun SetupNavGraph(
    navController: NavHostController,
){
    val listViewModel: CoinListViewModel = viewModel()
    val detailsViewModel: CoinDetailViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.CoinList
    ){
        composable<Screen.CoinList>{
            CoinListScreen(listViewModel, navController)
        }
        composable<Screen.CoinDetails>{backStackEntry ->
            val coinDetails = backStackEntry.toRoute<Screen.CoinDetails>()
            CoinDetailScreen(detailsViewModel, coinDetails.url, navController)
        }
    }
}
