package com.gastor.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gastor.app.viewmodel.TransactionViewModel

@Composable
fun StatisticsScreen() {

    val viewModel: TransactionViewModel = viewModel()

    val transactions = viewModel.transactions.collectAsState()

    val expenses = transactions.value.filter { it.type == "expense" }
    val income = transactions.value.filter { it.type == "income" }

    val totalExpenses = expenses.sumOf { it.amount }
    val totalIncome = income.sumOf { it.amount }

    // Agrupar por categoría
    val categoryMap = expenses.groupBy { it.category }
        .mapValues { entry ->
            entry.value.sumOf { it.amount }
        }
    val topCategory = categoryMap.maxByOrNull { it.value }

    val topCategoryText = if (topCategory != null && totalExpenses > 0) {
        val percent = (topCategory.value / totalExpenses) * 100
        "Tu mayor gasto es ${topCategory.key} (${String.format("%.1f", percent)}%)"
    } else {
        "Aún no hay suficientes datos"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text("Estadísticas", fontSize = 26.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = topCategoryText,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Gastos por categoría", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(categoryMap.toList()) { (category, amount) ->

                val percent = if (totalExpenses > 0)
                    (amount / totalExpenses) * 100
                else 0.0

                StatsCategoryItem(
                    name = category,
                    percent = percent
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
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