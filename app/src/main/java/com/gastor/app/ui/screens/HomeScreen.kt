package com.gastor.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gastor.app.viewmodel.TransactionViewModel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.gastor.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val viewModel: TransactionViewModel = viewModel()
    val income = viewModel.totalIncome.collectAsState()
    val expenses = viewModel.totalExpenses.collectAsState()
    val transactions = viewModel.transactions.collectAsState()

    val balance = (income.value ?: 0.0) - (expenses.value ?: 0.0)

    val expensesList = transactions.value.filter { it.type == "expense" }
    val totalExpenses = expensesList.sumOf { it.amount }

    val categoryMap = expensesList.groupBy { it.category }
        .mapValues { it.value.sumOf { t -> t.amount } }

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text("GASTOR", 
                        style = MaterialTheme.typography.titleMedium,
                        letterSpacing = 4.sp,
                        color = Color.White
                    ) 
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = CoreBackground.copy(alpha = 0.9f)
                ),
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Text("⚙️", color = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                if (expanded) {
                    SmallFloatingActionButton(
                        onClick = {
                            expanded = false
                            navController.navigate("add_income")
                        },
                        containerColor = NeonGreen
                    ) {
                        Text("+ Ingreso", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    SmallFloatingActionButton(
                        onClick = {
                            expanded = false
                            navController.navigate("add_expense")
                        },
                        containerColor = NeonRed
                    ) {
                        Text("- Gasto", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                FloatingActionButton(
                    onClick = { expanded = !expanded },
                    containerColor = AccentCyan,
                    contentColor = Color.Black
                ) {
                    Text(if (expanded) "x" else "+", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = CoreBackground,
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Text("❖") },
                    label = { Text("Inicio") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AccentCyan,
                        indicatorColor = SurfaceCard
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("statistics") },
                    icon = { Text("↗") },
                    label = { Text("Estadísticas") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("history") },
                    icon = { Text("≡") },
                    label = { Text("Historial") }
                )
            }
        },
        containerColor = CoreBackground
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Text("Centro de Comando", fontSize = 16.sp, color = TextGray)
            Spacer(modifier = Modifier.height(20.dp))

            BalanceCard(
                income = income.value ?: 0.0,
                expenses = expenses.value ?: 0.0,
                balance = balance
            )

            Spacer(modifier = Modifier.height(30.dp))

            CategorySection(categoryMap, totalExpenses)
        }
    }
}

@Composable
fun BalanceCard(income: Double, expenses: Double, balance: Double) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(SurfaceCard, Color(0xFF222631))
                )
            )
            .border(1.dp, SurfaceCardBorder, RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {
        Column {
            Text("BALANCE NETO", color = TextGray, fontSize = 12.sp, letterSpacing = 2.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$${String.format(java.util.Locale.US, "%.2f", balance)}",
                fontSize = 42.sp,
                fontWeight = FontWeight.Black,
                color = TextWhite
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("INGRESOS", color = TextGray, fontSize = 10.sp, letterSpacing = 1.sp)
                    Text("$${String.format(java.util.Locale.US, "%.2f", income)}", color = NeonGreen, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text("GASTOS", color = TextGray, fontSize = 10.sp, letterSpacing = 1.sp)
                    Text("$${String.format(java.util.Locale.US, "%.2f", expenses)}", color = NeonRed, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun CategorySection(categoryMap: Map<String, Double>, totalExpenses: Double) {
    Column {
        Text("Desglose de Operaciones", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        categoryMap.forEach { (category, amount) ->
            val percent = if (totalExpenses > 0) (amount / totalExpenses) * 100 else 0.0
            CategoryItem(name = category, amount = amount, percent = percent.toFloat())
        }
    }
}

@Composable
fun CategoryItem(name: String, amount: Double, percent: Float) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(name, color = TextWhite, fontWeight = FontWeight.Medium)
            Text("$${String.format(java.util.Locale.US, "%.2f", amount)}", color = TextGray)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = percent / 100f,
            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
            color = AccentCyan,
            trackColor = SurfaceCard,
        )
    }
}