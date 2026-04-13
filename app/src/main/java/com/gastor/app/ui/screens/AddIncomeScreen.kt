package com.gastor.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gastor.app.viewmodel.TransactionViewModel
import com.gastor.app.data.model.Transaction

@Composable
fun AddIncomeScreen(
    navController: NavController,
    viewModel: TransactionViewModel = viewModel()
) {

    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Registrar Ingreso",
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Monto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Nota (opcional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {

                if (amount.isNotEmpty()) {

                    viewModel.addTransaction(
                        Transaction(
                            amount = amount.toDouble(),
                            category = "Ingreso",
                            date = System.currentTimeMillis(),
                            type = "income",
                            note = note
                        )
                    )

                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text("Guardar Ingreso")
        }
    }
}