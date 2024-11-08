package com.example.cars

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class Car {
    var nome by mutableStateOf(" ");
    var description by mutableStateOf(" ");
}