package com.gastor.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gastor.app.viewmodel.TransactionViewModel

import androidx.navigation.NavController
import com.gastor.app.ui.theme.CoreBackground

@Composable
fun StatisticsScreen(navController: NavController) {
    val viewModel: TransactionViewModel = viewModel()
    val transactions = viewModel.transactions.collectAsState()

    val expenses = transactions.value.filter { it.type == "expense" }
    val income = transactions.value.filter { it.type == "income" }
    
    val totalExpenses = expenses.sumOf { it.amount }
    val totalIncome = income.sumOf { it.amount }
    val balance = totalIncome - totalExpenses
    
    val budget = 2000000.0
    val remaining = budget - totalExpenses

    val categoryMap = expenses.groupBy { it.category }.mapValues { entry -> entry.value.sumOf { it.amount } }

    Scaffold(
        bottomBar = { com.gastor.app.ui.components.GastorBottomNavBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_expense") },
                containerColor = com.gastor.app.ui.theme.AccentCyan,
                contentColor = androidx.compose.ui.graphics.Color.White,
                shape = androidx.compose.foundation.shape.CircleShape
            ) {
                Icon(androidx.compose.material.icons.Icons.Filled.Add, contentDescription = "Add", modifier = Modifier.size(32.dp))
            }
        },
        containerColor = androidx.compose.ui.graphics.Color(0xFFEFEFEF)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Blue Background Top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f)
                    .background(
                        color = com.gastor.app.ui.theme.AccentCyan
                    )
            )
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Column {
                        Text("¡Hola de nuevo!", color = androidx.compose.ui.graphics.Color.White, fontSize = 14.sp)
                        Text("Mi resumen", color = androidx.compose.ui.graphics.Color.White, fontSize = 24.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    }
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(androidx.compose.material.icons.Icons.Filled.Settings, contentDescription = "Settings", tint = androidx.compose.ui.graphics.Color.White, modifier = Modifier.size(28.dp))
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Card 1: Total gastado
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Total gastado este mes", color = androidx.compose.ui.graphics.Color.Gray, fontSize = 14.sp)
                        Text(
                            "$${String.format(java.util.Locale.US, "%,.0f", totalExpenses)}",
                            color = androidx.compose.ui.graphics.Color.Black,
                            fontSize = 32.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        // Progress bar
                        LinearProgressIndicator(
                            progress = if (budget > 0) (totalExpenses / budget).toFloat().coerceAtMost(1f) else 0f,
                            modifier = Modifier.fillMaxWidth().height(6.dp),
                            color = com.gastor.app.ui.theme.NeonGreen,
                            trackColor = androidx.compose.ui.graphics.Color(0xFFE0E0E0)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "$${String.format(java.util.Locale.US, "%,.0f", remaining)} restantes de $${String.format(java.util.Locale.US, "%,.0f", budget)}",
                            color = com.gastor.app.ui.theme.NeonGreen,
                            fontSize = 12.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Card 2: Balance del mes
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Balance del mes", color = androidx.compose.ui.graphics.Color.Black, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            BalancePill(color = androidx.compose.ui.graphics.Color(0xFFD4EED8), textColor = com.gastor.app.ui.theme.NeonGreen, text = "$${String.format(java.util.Locale.US, "%,.0f", totalIncome)}")
                            BalancePill(color = androidx.compose.ui.graphics.Color(0xFFFBDDDD), textColor = com.gastor.app.ui.theme.NeonRed, text = "-$${String.format(java.util.Locale.US, "%,.0f", totalExpenses)}")
                            BalancePill(color = androidx.compose.ui.graphics.Color(0xFFD2E3FC), textColor = com.gastor.app.ui.theme.AccentCyan, text = "$${String.format(java.util.Locale.US, "%,.0f", balance)}")
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Card 3: Gastos por categoría
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Gastos por categoría", color = androidx.compose.ui.graphics.Color.Black, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Box(
                            modifier = Modifier.fillMaxWidth().height(150.dp),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            if (totalExpenses > 0) {
                                DonutChart(categoryMap = categoryMap, total = totalExpenses)
                            } else {
                                Text("No hay gastos", color = androidx.compose.ui.graphics.Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BalancePill(color: androidx.compose.ui.graphics.Color, textColor: androidx.compose.ui.graphics.Color, text: String) {
    Box(
        modifier = Modifier
            .background(color, androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, color = textColor, fontSize = 12.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
    }
}

@Composable
fun DonutChart(categoryMap: Map<String, Double>, total: Double) {
    val colors = listOf(com.gastor.app.ui.theme.AccentCyan, androidx.compose.ui.graphics.Color(0xFFB39DDB), androidx.compose.ui.graphics.Color(0xFFFFCC80), com.gastor.app.ui.theme.NeonGreen, com.gastor.app.ui.theme.NeonRed, androidx.compose.ui.graphics.Color.DarkGray)
    var startAngle = -90f
    
    androidx.compose.foundation.Canvas(modifier = Modifier.size(120.dp)) {
        var index = 0
        categoryMap.forEach { (category, amount) ->
            val sweepAngle = ((amount / total) * 360).toFloat()
            drawArc(
                color = colors[index % colors.size],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 40f, cap = androidx.compose.ui.graphics.StrokeCap.Butt)
            )
            startAngle += sweepAngle
            index++
        }
    }
}

@Composable
fun StatsCategoryItem(name: String, percent: Double) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(name)
            Text("%.1f%%".format(percent))
        }

        Spacer(modifier = Modifier.height(6.dp))

        LinearProgressIndicator(
            progress = percent.toFloat() / 100f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
    }
}