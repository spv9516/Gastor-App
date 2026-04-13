package com.gastor.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {

    var biometricsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text("Configuración", fontSize = 26.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // BIOMETRÍA
        Card(modifier = Modifier.fillMaxWidth()) {

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text("Usar biometría")

                Switch(
                    checked = biometricsEnabled,
                    onCheckedChange = { biometricsEnabled = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // BOTÓN BORRAR DATOS
        Button(
            onClick = {
                // luego conectamos a Room
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Borrar todos los datos")
        }
    }
}