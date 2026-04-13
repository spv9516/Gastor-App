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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    navController: NavController,
    viewModel: TransactionViewModel = viewModel()
) {

    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    val categories = listOf(
        "Comida",
        "Transporte",
        "Entretenimiento",
        "Servicios",
        "Salud",
        "Educación",
        "Otros"
    )

    var selectedCategory by remember { mutableStateOf("Seleccionar categoría") }
    var expandedCategory by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Registrar Gasto",
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // MONTO
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Monto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // CATEGORÍA 🔥
        ExposedDropdownMenuBox(
            expanded = expandedCategory,
            onExpandedChange = { expandedCategory = !expandedCategory }
        ) {

            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoría") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandedCategory,
                onDismissRequest = { expandedCategory = false }
            ) {

                categories.forEach { category ->

                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            expandedCategory = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // NOTA
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Nota (opcional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        // BOTÓN
        Button(
            onClick = {

                if (amount.isNotEmpty() && selectedCategory != "Seleccionar categoría") {

                    viewModel.addTransaction(
                        Transaction(
                            amount = amount.toDouble(),
                            category = selectedCategory, // 🔥 YA ES DINÁMICO
                            date = System.currentTimeMillis(),
                            type = "expense",
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

            Text("Guardar Gasto")
        }
    }
}