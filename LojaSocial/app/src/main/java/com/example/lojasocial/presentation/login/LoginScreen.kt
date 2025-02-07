package com.example.lojasocial.presentation.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lojasocial.R
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.navigation.Route
import com.example.lojasocial.utils.AuthState
import com.example.lojasocial.utils.showToastError
import com.example.lojasocial.utils.showToastMessage
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavHostController, loginViewModel: LoginViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val authState = loginViewModel.authState.observeAsState()
    val context = LocalContext.current

    var user: User? = null

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated ->{
                if (user == null){
                    Log.d("Teste", "Antes da rota home, user = null")
                }else{
                    Log.d("Teste", "Antes da rota home, user != null")
                }

                //navController.navigate(Route.Home(user!!.email))
                navController.navigate(Route.Home)
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
        TextField(value = email, label = { Text("Email") }, onValueChange = {email = it})
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = password, label = { Text("Password") }, onValueChange = {password = it})
        Spacer(modifier = Modifier.height(24.dp))
        Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Black),onClick = {
            coroutineScope.launch {
                try {
                    user = loginViewModel.login(email, password, context)
                }catch (e: Exception){
                    showToastError(context, e)
                }
            }
        }){
            Text("Login")
        }
        Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),onClick = { navController.navigate(
            Route.Signup
        )}
        ){
            Text("Register")
        }
    }
}

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val authState = loginViewModel.authState.observeAsState()
    val context = LocalContext.current

    var user: User? = null

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated ->{
                if (user == null){
                    Log.d("Teste", "Antes da rota home, user = null")
                }else{
                    Log.d("Teste", "Antes da rota home, user != null")
                }

                //navController.navigate(Route.Home(user!!.email))
                navController.navigate(Route.Home)
            }
            is AuthState.Error -> showToastMessage(context,(authState.value as AuthState.Error).message)
            else -> Unit
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment =  Alignment.CenterHorizontally) {

        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logotipo",
            modifier = Modifier
                .size(width = 600.dp, height = 350.dp)
                .padding(top = 60.dp, bottom = 80.dp))

        Text(text = "Iniciar Sessão",
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight.W400
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.padding(top = 34.dp,bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            modifier = Modifier.padding(bottom = 50.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        user = loginViewModel.login(email, password, context)
                    }catch (e: Exception){
                        showToastError(context, e)
                    }
                    }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD8E4))
        ) {
            Text("Iniciar Sessão", color = Color(0xFF633B48))
        }

        Button(
            onClick = {
                navController.navigate(Route.Signup)
            },
            modifier = Modifier
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD8E4))
        ) {
            Text("Ainda não tem conta? Registe-se", color = Color(0xFF633B48))
        }
    }
}