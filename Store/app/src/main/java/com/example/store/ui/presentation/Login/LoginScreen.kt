package com.example.store.ui.presentation.Login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.store.domain.model.User
import com.example.store.navigation.Screen
//import com.example.store.presentation.viewModels.AuthState
import com.example.store.utils.showToastMessage
import  com.example.store.utils.AuthState
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavHostController, loginViewModel: LoginViewModel) {

    val coroutineScope = rememberCoroutineScope()

    val authState = loginViewModel.authState.observeAsState()
    val context = LocalContext.current
    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated ->{
                if (user == null){
                    Log.d("Teste", "Antes da rota home, user = null")
                }else{
                    Log.d("Teste", "Antes da rota home, user != null")
                }
                navController.navigate(Screen.Home(user!!.email))
            }
            is AuthState.Error -> showToastMessage(context,(authState.value as AuthState.Error).message)
            else -> Unit
        }
    }

    Column(
        Modifier
        .fillMaxSize()
        .background(color = Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(24.dp))
        TextField(value = loginViewModel.email.value, label = { Text("Email") }, onValueChange = {loginViewModel.email.value = it})
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = loginViewModel.password.value, label = { Text("Password") }, onValueChange = {loginViewModel.password.value = it})
        Spacer(modifier = Modifier.height(24.dp))
        Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Black),onClick = {
            coroutineScope.launch {
                user = loginViewModel.login(loginViewModel.email.value,loginViewModel.password.value,context)
            }
        }){
            Text("Login")
        }
        Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),onClick = { navController.navigate(
            Screen.Signup
        )}
        ){
            Text("Register")
        }
    }
}


