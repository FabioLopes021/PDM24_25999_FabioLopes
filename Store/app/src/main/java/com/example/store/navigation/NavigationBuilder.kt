package com.example.store.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.store.ui.presentation.Carrinho.CarrinhoScreen
import com.example.store.ui.presentation.Carrinho.CarrinhoViewModel
import com.example.store.ui.presentation.CarrinhoUtilizador.CarrinhoUtilizadorScreen
import com.example.store.ui.presentation.Home.HomeScreen
import com.example.store.ui.presentation.Home.HomeViewModel
import com.example.store.ui.presentation.ListaCarrinhos.ListaCarrinhosScreen
import com.example.store.ui.presentation.ListaCarrinhos.ListaCarrinhosViewModel
import kotlinx.serialization.Serializable
import com.example.store.ui.presentation.Login.Login
import com.example.store.ui.presentation.Login.LoginViewModel
import com.example.store.ui.presentation.SignUp.Register
import com.example.store.ui.presentation.SignUp.SignUpViewModel
import com.example.store.ui.presentation.SplashScreen.SplashScreen
import com.example.store.ui.presentation.SplashScreen.SplashViewModel


@Serializable
sealed class Screen{
    @Serializable
    data class Home(
        val email: String?
    ): Screen()
    @Serializable
    data object Login: Screen()
    @Serializable
    data object Signup: Screen()
    @Serializable
    data object SplashScreen: Screen()
    @Serializable
    data class CarrinhoUtilizador(
        val email: String?
    ): Screen()
    @Serializable
    data class ListaCarrinhos(
        val email: String?
    ): Screen()
    @Serializable
    data class Carrinho(
        val email: String
    ): Screen()
}


@SuppressLint("RestrictedApi")
@Composable
fun SetupNavGraph(navController: NavHostController){

    val homeViewModel: HomeViewModel = viewModel()
    val listaCarrinhosViewModel: ListaCarrinhosViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen
    ){
        composable<Screen.SplashScreen>{
            val splashViewModel: SplashViewModel = viewModel()
            SplashScreen(splashViewModel, navController)
        }
        composable<Screen.Login>{
            val loginViewModel: LoginViewModel = viewModel()
            Login(navController = navController, loginViewModel)
        }
        composable<Screen.Signup>{
            val singUpViewModel: SignUpViewModel = viewModel()
            Register(navController = navController, singUpViewModel)
        }
        composable<Screen.Home>{backStackEntry ->
            val email = backStackEntry.toRoute<Screen.Home>()
            Log.d("Email", "Email no navGraph${email.email}")
            HomeScreen(navController = navController, homeViewModel, email.email)
        }
        composable<Screen.CarrinhoUtilizador>{ backStackEntry ->
            val email = backStackEntry.toRoute<Screen.CarrinhoUtilizador>()
            CarrinhoUtilizadorScreen(navController = navController, homeViewModel, email.email)
        }
        composable<Screen.ListaCarrinhos>{backStackEntry ->
            val email = backStackEntry.toRoute<Screen.ListaCarrinhos>()
            ListaCarrinhosScreen(navController = navController, listaCarrinhosViewModel, email.email)
        }
        composable<Screen.Carrinho>{backStackEntry ->
            val email = backStackEntry.toRoute<Screen.Carrinho>()
            val carrinhoViewModel: CarrinhoViewModel = viewModel()
            CarrinhoScreen(navController = navController, carrinhoViewModel, email.email)
        }
    }
}