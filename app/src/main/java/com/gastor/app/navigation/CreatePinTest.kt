package com.gastor.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun CreatePinScreen(navController: NavController) {

    var pin by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Crear PIN de seguridad",
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Crea un PIN de 6 dígitos",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        PinDots(pin.length)

        Spacer(modifier = Modifier.height(40.dp))

        NumberPad { number ->

            if (pin.length < 6) {
                pin += number
            }

            if (pin.length == 6) {
                navController.navigate("biometric")
            }
        }
    }
}
@Composable
fun PinDots(count: Int) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        repeat(6) { index ->

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        color = if (index < count) Color.Black else Color.LightGray,
                        shape = CircleShape
                    )
            )
        }
    }
}
@Composable
fun NumberPad(onNumberClick: (String) -> Unit) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        listOf(
            listOf("1","2","3"),
            listOf("4","5","6"),
            listOf("7","8","9"),
            listOf("0")
        ).forEach { row ->

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {

                row.forEach { number ->

                    NumberButton(number) {
                        onNumberClick(number)
                    }
                }
            }
        }
    }
}
@Composable
fun NumberButton(number: String, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .size(80.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = number,
            fontSize = 28.sp
        )
    }
}