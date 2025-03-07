package com.example.lojasocial.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.lojasocial.presentation.Loading.LoadingViewModel
import com.example.lojasocial.presentation.Loading.loadingApp
import com.example.lojasocial.presentation.home.Home
import com.example.lojasocial.presentation.home.HomeViewModel
import com.example.lojasocial.presentation.login.Login
import com.example.lojasocial.presentation.login.LoginScreen
import com.example.lojasocial.presentation.login.LoginViewModel
import com.example.lojasocial.presentation.signup.RegisterScreen
import com.example.lojasocial.presentation.signup.SignUp
import com.example.lojasocial.presentation.signup.SignUpViewModel


@SuppressLint("RestrictedApi")
@Composable
fun SetupNavGraph(loadingViewModel: LoadingViewModel){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Loading
    ){
        composable<Route.Loading>{
            loadingApp(navController = navController, loadingViewModel)
        }
        composable<Route.Login>{
            val loginViewModel: LoginViewModel = viewModel()
            //Login(navController = navController, loginViewModel)
            LoginScreen(navController = navController, loginViewModel)
        }
        composable<Route.Signup>{
            val singUpViewModel: SignUpViewModel = viewModel()
            //SignUp(navController = navController, singUpViewModel)
            RegisterScreen(navController = navController, singUpViewModel)
        }
        composable<Route.Home>{//backStackEntry ->
            val homeViewModel: HomeViewModel = viewModel()
            //val user = backStackEntry.toRoute<Route.Home>()
            Home(navController = navController,homeViewModel)
        }
    }
}