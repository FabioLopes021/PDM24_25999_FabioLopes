package com.example.cars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cars.ui.theme.CarsTheme
import androidx.constraintlayout.compose.ConstraintLayout



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarsTheme {
                var listacarros = remember { mutableStateListOf<Car>() } ;
                NavegacaoApp();
            }
        }
    }
}


@Composable
fun CreateButton(text: String, onClick: () -> Unit, color: Color){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(5/4f)
            .padding(5.dp),
        colors = ButtonDefaults.buttonColors( containerColor  = color)) // Apply custom color
    {
        Text(text = text, fontSize = 16.sp)
    }
}


@Composable
fun NavegacaoApp( ) {
    val navController = rememberNavController()
    NavHost (navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(navController)
        }
        composable("details") {
            DetailsScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
    }
}


@Composable
fun EcraInicio(navController: NavController) {
    Button(onClick = { navController.navigate("destino") }) {
        Text("Ir para o Destino")
    }
}

@Composable
fun EcraDestino() {
    Text("Bem-vindo ao Ecra de Destino")
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun HomeScreen(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()){
        Button(onClick = { navController.navigate("details") }) {
            Text("Go to Details")
        }
        val text = createRef();

        Text(
            text = "Texto no centro",
            modifier = Modifier.constrainAs(text){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
fun DetailsScreen(navController: NavController) {
    Button(onClick = { navController.navigate("settings") }) {
        Text("Go to Settings")
    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    ConstraintLayout (modifier = Modifier. fillMaxSize() ) {
        val (text1, text2) = createRefs()

        Button(onClick = { navController.popBackStack() }) {
            Text("Go Back")

        }

        Text(
            text = "Texto no centro",
            modifier = Modifier.constrainAs(text1) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = "Texto por baixo",
            modifier = Modifier.constrainAs(text2) {
                top.linkTo(text1.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarsTheme {
        Greeting("Android")
    }
}