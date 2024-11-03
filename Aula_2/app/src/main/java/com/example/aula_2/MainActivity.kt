package com.example.aula_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aula_2.ui.theme.Aula_2Theme
import java.text.Normalizer.Form

class FormLine(initName: String, initType: String, inithint: String){
    var Name by mutableStateOf(initName)
    var Type by mutableStateOf(initType)
    var hint by mutableStateOf(inithint)
}

class MainActivity : ComponentActivity() {
    val tst = FormLine("Teste", "Aux","ladfjslajksf")
    val tst1 = FormLine("Teste1", "Aux1","ladfjslajksf1")
    val tst2 = FormLine("Teste2", "Aux2","ladfjslajksf2")

    val aux: MutableList<FormLine> = mutableListOf(tst, tst1, tst2)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Aula_2Theme {
                Column {
                    ExemploListaStringsRow(aux)
                }
            }
        }
    }
}


//@Composable
//fun ExemploListaStrings(itemsList: List<FormLine>){
//    LazyColumn {
//        items(itemsList) {
//            item -> Text(text = item)
//        }
//    }
//}

@Composable
fun ExemploListaStringsRow(itemsList: List<FormLine>){
    LazyColumn {
        items(itemsList) {
                item ->
            Row {
                Text(text = item.Name)

            }
        }
    }
}

@Composable
fun DrawFormLine(line: FormLine){
    Row {
        Text(text = line.Name)
        Spacer(modifier = Modifier.height(20,dp))

    }

}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Aula_2Theme {
        Greeting("Android")
    }
}