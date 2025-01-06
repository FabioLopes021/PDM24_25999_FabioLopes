package com.example.store.ui.presentation.SplashScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.store.navigation.Screen


@Composable
fun SplashScreen(viewModel: SplashViewModel, navController: NavHostController) {
    val isLoggedIn = viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn.value) {
        if (isLoggedIn.value != "") {
            navController.navigate(Screen.Home(isLoggedIn.value))
        } else {
            navController.navigate(Screen.Login)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
