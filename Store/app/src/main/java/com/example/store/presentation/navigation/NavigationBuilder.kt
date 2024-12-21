package com.example.store.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.store.presentation.screens.HomeScreen
import kotlinx.serialization.Serializable
import com.example.store.presentation.screens.Login
import com.example.store.presentation.screens.Register
import com.example.store.presentation.viewModels.AuthViewModel


@Serializable
sealed class Screen{
    @Serializable
    data object Home: Screen()
    @Serializable
    data object Login: Screen()
    @Serializable
    data object Signup: Screen()
}


@SuppressLint("RestrictedApi")
@Composable
fun SetupNavGraph(authViewModel: AuthViewModel){
    val navController = rememberNavController();

    NavHost(
        navController = navController,
        startDestination = Screen.Login
    ){
        composable<Screen.Login>{
            Login(navController = navController, authViewModel)
        }
        composable<Screen.Signup>{
            Register(navController = navController, authViewModel)
        }
        composable<Screen.Home>{
            HomeScreen(navController = navController, authViewModel)
        }
    }
}