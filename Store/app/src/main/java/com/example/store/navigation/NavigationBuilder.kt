package com.example.store.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.store.ui.presentation.Home.HomeScreen
import com.example.store.ui.presentation.Home.HomeViewModel
import kotlinx.serialization.Serializable
import com.example.store.ui.presentation.Login.Login
import com.example.store.ui.presentation.Login.LoginViewModel
import com.example.store.ui.presentation.SingUp.Register
import com.example.store.ui.presentation.SingUp.SignUpViewModel
import com.example.store.ui.presentation.SplashScreen.SplashScreen
import com.example.store.ui.presentation.SplashScreen.SplashViewModel


@Serializable
sealed class Screen{
//    @Serializable
////    data object Home: Screen()
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
}


@SuppressLint("RestrictedApi")
@Composable
fun SetupNavGraph(navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = Screen.Login
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
            val homeViewModel: HomeViewModel = viewModel()
            HomeScreen(navController = navController, homeViewModel)
        }
    }
}