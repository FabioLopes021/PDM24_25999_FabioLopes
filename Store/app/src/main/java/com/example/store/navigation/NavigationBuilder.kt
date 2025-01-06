package com.example.store.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
    data object ListaCarrinhos: Screen()
//    @Serializable
//    data class Carrinho(
//      val userId: String
//    ): Screen()
}


@SuppressLint("RestrictedApi")
@Composable
fun SetupNavGraph(navController: NavHostController){

    val homeViewModel: HomeViewModel = viewModel()

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
        composable<Screen.ListaCarrinhos>{
            val listaCarrinhosViewModel: ListaCarrinhosViewModel = viewModel()
            ListaCarrinhosScreen(navController = navController, listaCarrinhosViewModel)
        }
//        composable<Screen.Carrinho>{backStackEntry ->
//            //val userId = backStackEntry.toRoute<Screen.CarrinhoUtilizador>()
//            //CarrinhoScreen(navController = navController, homeViewModel, email.email)
//        }
    }
}