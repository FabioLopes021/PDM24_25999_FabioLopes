package com.example.store.presentation.screens

import android.widget.Toast
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.store.presentation.navigation.Screen
import com.example.store.presentation.viewModels.AuthState
import com.example.store.presentation.viewModels.AuthViewModel

@Composable
fun Login(navController: NavHostController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
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
        Text(text = "Welcome", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(24.dp))
        TextField(value = email, label = { Text("Email") }, onValueChange = {email = it})
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = password, label = { Text("Password") }, onValueChange = {password = it})
        Spacer(modifier = Modifier.height(24.dp))
        Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Black),onClick = {
            authViewModel.login(email,password)
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
