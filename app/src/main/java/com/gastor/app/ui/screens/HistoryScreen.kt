package com.gastor.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gastor.app.viewmodel.TransactionViewModel
import com.gastor.app.data.model.Transaction

@Composable
fun HistoryScreen() {

    val viewModel: TransactionViewModel = viewModel()
    val transactions = viewModel.transactions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Historial",
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(transactions.value) { transaction ->

                TransactionItem(transaction)

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Text(
                    text = transaction.category,
                    fontSize = 16.sp
                )

                Text(
                    text = transaction.note,
                    fontSize = 12.sp
                )
            }

            Text(
                text = if (transaction.type == "expense")
                    "-${transaction.amount}"
                else
                    "+${transaction.amount}",
                fontSize = 16.sp
            )
        }
    }
}