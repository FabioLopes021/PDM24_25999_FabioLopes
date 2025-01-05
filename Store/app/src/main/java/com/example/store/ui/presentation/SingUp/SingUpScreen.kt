package com.example.store.ui.presentation.SingUp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.store.navigation.Screen
import com.example.store.presentation.viewModels.AuthState
import com.example.store.presentation.viewModels.AuthViewModel
import kotlinx.coroutines.launch


@Composable
fun Register(navController: NavHostController, signUpViewModel: SignUpViewModel) {
    var password by signUpViewModel.password
    var email by signUpViewModel.email
    var nome by signUpViewModel.nome
    var morada by signUpViewModel.morada
    var telemovel by signUpViewModel.telemovel




    val coroutineScope = rememberCoroutineScope()
    val authState = signUpViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate(Screen.Home)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT).show()
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
        Text(text = "Register", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(24.dp))
        TextField(value = password, label = { Text("nome") }, onValueChange = {password = it})
        Spacer(modifier = Modifier.height(24.dp))
        TextField(value = email, label = { Text("Email") }, onValueChange = {email = it})
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = nome, label = { Text("Nome") }, onValueChange = {nome = it})
        Spacer(modifier = Modifier.height(24.dp))
        TextField(value = morada, label = { Text("Morada") }, onValueChange = {morada = it})
        Spacer(modifier = Modifier.height(24.dp))
        TextField(value = telemovel, label = { Text("telemovel") }, onValueChange = {telemovel = it}, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))
        Spacer(modifier = Modifier.height(24.dp))
        Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Black),onClick = {
            coroutineScope.launch {
                signUpViewModel.register(email,password,context)
            }

        }){
            Text("Register")
        }
        Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),onClick = {
            navController.navigate(Screen.Login)
        }){
            Text("Back")
        }
    }
}