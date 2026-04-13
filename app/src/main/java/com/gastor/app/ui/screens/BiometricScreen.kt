package com.gastor.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BiometricScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Activar biometría",
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "¿Activar acceso biométrico?"
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Accede más rápido con tu huella digital o reconocimiento facial"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Acceso más rápido y conveniente")
            Text("Mayor seguridad biométrica")
            Text("Siempre puedes usar tu PIN")
        }

        Column {

            Button(
                onClick = {
                    navController.navigate("home")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Activar biometría")
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate("home")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Ahora no")
            }
        }
    }
}