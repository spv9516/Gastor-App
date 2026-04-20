package com.gastor.app.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.background
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
    var date by remember { mutableStateOf("28/02/2026") }
    
    val categories = listOf("Comida", "Transporte", "Entretenimiento", "Servicios", "Salud", "Educación", "Otros")
    var selectedCategory by remember { mutableStateOf("Selecciona una categoría") }
    var expandedCategory by remember { mutableStateOf(false) }

    val bgColor = androidx.compose.ui.graphics.Color(0xFFEFEFEF)
    val topBarColor = com.gastor.app.ui.theme.AccentCyan
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(topBarColor)
                .padding(top = 40.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    androidx.compose.material.icons.Icons.Filled.ArrowBack, 
                    contentDescription = "Back", 
                    tint = androidx.compose.ui.graphics.Color.White, 
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Registrar Gasto",
                fontSize = 22.sp,
                color = androidx.compose.ui.graphics.Color.White,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            GastorInputCard {
                GastorInputHeader("💲", "Monto *")
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    placeholder = { Text("0.00", color = androidx.compose.ui.graphics.Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                        unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
                        focusedBorderColor = topBarColor,
                        unfocusedBorderColor = androidx.compose.ui.graphics.Color.LightGray
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(color = androidx.compose.ui.graphics.Color.Black)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            GastorInputCard {
                GastorInputHeader("🏷️", "Categoría *")
                ExposedDropdownMenuBox(
                    expanded = expandedCategory,
                    onExpandedChange = { expandedCategory = !expandedCategory }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                            unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
                            focusedBorderColor = topBarColor,
                            unfocusedBorderColor = androidx.compose.ui.graphics.Color.LightGray
                        ),
                        textStyle = androidx.compose.ui.text.TextStyle(color = androidx.compose.ui.graphics.Color.Black)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCategory,
                        onDismissRequest = { expandedCategory = false },
                        modifier = Modifier.background(androidx.compose.ui.graphics.Color.White)
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category, color = androidx.compose.ui.graphics.Color.Black) },
                                onClick = {
                                    selectedCategory = category
                                    expandedCategory = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            GastorInputCard {
                GastorInputHeader("📅", "Fecha *")
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                        unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
                        focusedBorderColor = topBarColor,
                        unfocusedBorderColor = androidx.compose.ui.graphics.Color.LightGray
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(color = androidx.compose.ui.graphics.Color.Black)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            GastorInputCard {
                GastorInputHeader("📝", "Nota (opcional)")
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    placeholder = { Text("Agrega una descripción...", color = androidx.compose.ui.graphics.Color.Gray) },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                        unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
                        focusedBorderColor = topBarColor,
                        unfocusedBorderColor = androidx.compose.ui.graphics.Color.LightGray
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(color = androidx.compose.ui.graphics.Color.Black)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (amount.isNotEmpty() && selectedCategory != "Selecciona una categoría") {
                        viewModel.addTransaction(
                            Transaction(
                                amount = amount.toDouble(),
                                category = selectedCategory,
                                date = System.currentTimeMillis(),
                                type = "expense",
                                note = note
                            )
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = topBarColor)
            ) {
                Text("Guardar Gasto", fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = androidx.compose.ui.graphics.Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun GastorInputCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun GastorInputHeader(icon: String, text: String) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
        Text(icon, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 14.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold, color = androidx.compose.ui.graphics.Color.DarkGray)
    }
}