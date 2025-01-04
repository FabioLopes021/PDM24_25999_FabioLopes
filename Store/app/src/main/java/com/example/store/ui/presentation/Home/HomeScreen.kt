package com.example.store.ui.presentation.Home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.store.navigation.Screen
import com.example.store.presentation.viewModels.AuthState
import com.example.store.presentation.viewModels.AuthViewModel
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
//    LaunchedEffect(authState.value) {
//        when(authState.value){
//            is AuthState.Unauthenticated -> navController.navigate(Screen.Login)
//            else -> Unit
//        }
//    }

    Scaffold { innerpadding ->
        Column (modifier = Modifier
            .padding(innerpadding)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Home Page")
            //Text(text = email)
            Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Black),onClick = {
                coroutineScope.launch {
                    try {
                        homeViewModel.logout()
                        navController.navigate(Screen.Login)
                    }catch (e: Exception){
                        Log.d("Teste", "teste")
                    }
                }
            }){
                Text("Signout")
            }
        }
    }
}
