package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme
import kotlin.math.floor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // using composables instead of xml, because xml is boooooooring
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Create the Calculator.
                    Calculator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

fun calcPitch(area: Float): String {
    // taking the FIFA recommended area of the football/soccer pitch
    var calculatedArea = area / 7_140
    return "Das entspricht ca. $calculatedArea Fußballfeldern."
}

fun calcAge(years: Int): String {
    // convert to double so it is easier to not convert it 3 times in a row
    var y = years.toDouble()
    var leapYears = floor(y / 4).toInt()
    var leapHundredYears = floor(y / 100).toInt()
    var leapFourHundredYears = floor(y / 400).toInt()
    // first days times years
    // then add all the leap years minus then 100 leap years plus the 400 leap years
    var days: Int = (years * 365) + (leapYears - leapHundredYears + leapFourHundredYears)
    var minutes: Int = days * 1440
    return "Das entspricht ca. $minutes Minuten."
}

fun calcMoney(cents: Int): String {
    // 86400 = 60 * 60 * 24
    // to make it easier
    var days = floor(cents.toDouble() / 86400)
    return "Das entspricht ca. ${days.toInt()} Tagen."
}

// return a constructed integer out of a string
fun getInteger(str: String): Int {
    return (str.filter { it.isDigit() }).toInt()
}

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    var resolution by remember { mutableStateOf("") }
    var padding = Modifier.padding(horizontal = 5.dp)

    Box(modifier = modifier) {
        Column(modifier = padding) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Amount") },
                modifier = padding
            )
            Button(onClick = {
                resolution = calcPitch(getInteger(text).toFloat())
            }) { Text("Fläche in Fußballfelder") }
            Button(onClick = {
                resolution = calcAge(getInteger(text))
            }) { Text("Alter in Minuten") }
            Button(onClick = {
                resolution = calcMoney(getInteger(text))
            }) { Text("Geld in Zeit") }
            OutlinedTextField(
                value = resolution,
                readOnly = true,
                onValueChange = { resolution = it },
                label = { Text("Ergebniss") })
        }
    }
}