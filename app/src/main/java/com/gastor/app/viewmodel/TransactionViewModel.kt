package com.gastor.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gastor.app.data.database.AppDatabase
import com.gastor.app.data.model.Transaction
import com.gastor.app.data.repository.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).transactionDao()
    private val repository = TransactionRepository(dao)

    val transactions = repository.getAllTransactions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val totalIncome = repository.getTotalIncome()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)

    val totalExpenses = repository.getTotalExpenses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.insert(transaction)
        }
    }
}