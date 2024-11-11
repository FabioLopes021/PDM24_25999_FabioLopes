package com.example.students.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.students.viewmodel.HomeViewModel


@Composable
fun HomePage(modifier: Modifier = Modifier, viewModel: HomeViewModel){

    val userData = viewModel.userData.observeAsState()

    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = {
                viewModel.getUserData()
            }
        ) {
            Text(text = "Get Data")
            userData.value?.name?.let {
                Box (Modifier.fillMaxWidth(),
                    Alignment.TopStart
                    ){
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                        ){
                        Text(text = "Name: $it")
                    }
                }
            }
        }
    }
}