package com.example.calculator

//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Label
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme
import androidx.compose.ui.graphics.Color
import kotlin.math.sqrt


object CalculatorMemory{
    private var total by mutableStateOf("0")
    private var total1 by mutableStateOf("0")
    private var counter by mutableStateOf(0)
    private var actualAction by mutableStateOf("")
    private var memory by mutableStateOf("0")


    public fun addNumber(number: String){
        if(counter == 0){
            if ( total  == "0"){
                total = number
            }else{
                total += number.toString()
            }
        }else {
            if ( total1  == "0"){
                total1 = number
            }else{
                total1 += number.toString()
            }
        }
    }

    public fun addDot(){
        if(counter % 2 ==  0){
            if(!total.contains(".")) {
                total += "."
            }
        }else {
            if(!total1.contains(".")) {
                total1 += "."
            }
        }
    }

    public fun finishAction(act: String){
        if(actualAction != "" && act == "") {
            val totalaux = total.toDouble()
            val total1aux = total1.toDouble()
            var result = 0.0
            var result1 = 0
            when (actualAction) {
                "x" -> {
                    result = totalaux * total1aux
                    total = result.toString()
                }

                "/" -> {
                    result = totalaux / total1aux
                    total = result.toString()
                }

                "+" -> {
                    result = totalaux + total1aux
                    total = result.toString()
                }

                "-" -> {
                    result = totalaux - total1aux
                    total = result.toString()
                }
            }
        }
    }

    public fun calcAction(action: String){
        when(action){
            "x" -> {
                if(counter > 0)
                    finishAction("")
                actualAction = "x"
                total1 = "0"
                counter++
            }
            "/" -> {
                if(counter > 0)
                    finishAction("")
                actualAction = "/"
                total1 = "0"
                counter++
            }
            "+" -> {
                if(counter > 0)
                    finishAction("")
                actualAction = "+"
                total1 = "0"
                counter++
            }
            "-" -> {
                if(counter > 0)
                    finishAction("")
                actualAction = "-"
                total1 = "0"
                counter++
            }
            "=" -> {
                if(actualAction != "=" && actualAction != ""){
                    if(counter > 0)
                        finishAction("")
                    actualAction = "="
                    total1 = "0"
                    counter++
//                    if(counter % 2 == 0)
//                        counter += 2
//                    else
//                        counter++
                }
            }
        }
    }

    fun subOperations(action: String){
        var aux = 0.0
        when(action){
            "%" -> {
                if(counter % 2 == 0){
                    aux = total.toDouble() / 100
                    total = aux.toString()
                }else{
                    aux = total1.toDouble() / 100
                    total1 = aux.toString()
                    calcAction("=")
                }
            }
            "√" -> {
                if(counter % 2 == 0){
                    aux = sqrt(total.toDouble())
                    total = aux.toString()
                }else{
                    aux = sqrt(total1.toDouble())
                    total1 = aux.toString()
                    calcAction("=")
                }
            }
            "+/-" -> {

                if(counter % 2 == 0){
                    if(total != "0" && total != "0.0"){
                        aux = -(total.toDouble())
                        total = aux.toString()
                    }
                }else{
                    if(total != "0" && total != "0.0") {
                        aux = -(total1.toDouble())
                        total1 = aux.toString()
                    }
                }
            }
            "CE" -> {
                if(counter % 2 == 0){
                    if(total != "0"){
                        total = total.dropLast(1)
                        if(total.isEmpty())
                            total = "0"
                    }
                }else{
                    if(total != "0") {
                        total1 = total1.dropLast(1)
                        if(total1.isEmpty())
                            total1 = "0"
                    }
                }
            }
        }
    }

    fun memoryFunctions(action: String){
        var aux = 0.0

        when(action){
            "MRC" ->{
                if(counter % 2 == 0){
                    total = memory
                }else{
                    total1 = memory
                }
            }
            "M-" -> {
                if(counter % 2 == 0){
                    memory = (total.toDouble() -  memory.toDouble()).toString()
                }else{
                    memory = (total1.toDouble() -  memory.toDouble()).toString()
                }
            }
            "M+" -> {
                if(counter % 2 == 0){
                    memory = (total.toDouble() +  memory.toDouble()).toString()
                }else{
                    memory = (total1.toDouble() +  memory.toDouble()).toString()
                }
            }
        }
    }

    public fun clearCalculator(){
        total = "0"
        total1 = "0"
        counter = 0
        actualAction = ""
    }

    fun getCounterValue(): Int{
        return counter
    }

    fun getTotalValue(): String{
        return total
    }

    fun getTotal1Value(): String{
        return total1
    }
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .systemBarsPadding()
                        .fillMaxSize()
                        .padding(15.dp)
                ){
                    Display();
                    Keyboard();
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun Display(){
    Column(modifier = Modifier) {
        Row(verticalAlignment = Alignment.Top){
            if(CalculatorMemory.getCounterValue() % 2 == 0 || CalculatorMemory.getTotal1Value() == "0"){
                Text(CalculatorMemory.getTotalValue())
            }else{
                Text(CalculatorMemory.getTotal1Value())
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



@SuppressLint("Range")
@Composable
fun Keyboard(){
    Column ( modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth()){
            Column( modifier = Modifier.weight(1f)){
                CreateButton("MRC", color = Color.Black, onClick =  {
                    CalculatorMemory.memoryFunctions("MRC")
                } )
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("M-", color = Color.Black, onClick =  {
                    CalculatorMemory.memoryFunctions("M-")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("M+", color = Color.Black, onClick =  {
                    CalculatorMemory.memoryFunctions("M+")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("ON/C", color = Color.Red, onClick =  {
                    CalculatorMemory.clearCalculator()
                })
            }
        }
        Row(Modifier.fillMaxWidth()){
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("√", color = Color.Black, onClick =  {
                    CalculatorMemory.subOperations("√")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("%", color = Color.Black, onClick =  {
                    CalculatorMemory.subOperations("%")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("+/-", color = Color.Black, onClick =  {
                    CalculatorMemory.subOperations("+/-")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("CE", color = Color.Red, onClick =  {
                    CalculatorMemory.subOperations("CE")
                })
            }
        }
        Row(Modifier.fillMaxWidth()){
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("7", color = Color.Gray, onClick =  {
                    CalculatorMemory.addNumber("7")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("8", color = Color.Gray, onClick =  {
                    CalculatorMemory.addNumber("8")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("9", color = Color.Gray, onClick =  {
                    CalculatorMemory.addNumber("9")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("/", color = Color.Black, onClick =  {
                    CalculatorMemory.calcAction("/")
                })
            }
        }
        Row(Modifier.fillMaxWidth()){
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("4", color = Color.Gray, onClick =  {
                    CalculatorMemory.addNumber("4")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("5", color = Color.Gray, onClick =  {
                    CalculatorMemory.addNumber("5")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("6", color = Color.Gray, onClick =  {
                    CalculatorMemory.addNumber("6")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("x", color = Color.Black, onClick =  {
                    CalculatorMemory.calcAction("x")
                })
            }
        }
        Row(Modifier.fillMaxWidth()){
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("1", color = Color.Gray, onClick =  {
                    CalculatorMemory.addNumber("1")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("2", color = Color.Gray, onClick =  {
                    CalculatorMemory.addNumber("2")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("3", color = Color.Gray, onClick =  {
                    CalculatorMemory.addNumber("3")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("-", color = Color.Black, onClick =  {
                    CalculatorMemory.calcAction("-")
                })
            }
        }
        Row(Modifier.fillMaxWidth()){
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("0", color = Color.Gray, onClick =  {
                    CalculatorMemory.addNumber("0")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton(".", color = Color.Gray, onClick =  {
                    CalculatorMemory.addDot()
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("=", color = Color.Gray, onClick =  {
                    CalculatorMemory.calcAction("=")
                })
            }
            Column ( modifier = Modifier.weight(1f)){
                CreateButton("+", color = Color.Black, onClick =  {
                    CalculatorMemory.calcAction("+")
                })
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorTheme {
        //Greeting("Android")
        //Display()
        Keyboard()
    }
}