package com.gastor.app.data.repository

import com.gastor.app.data.dao.TransactionDao
import com.gastor.app.data.model.Transaction

class TransactionRepository(private val dao: TransactionDao) {

    suspend fun insert(transaction: Transaction) {
        dao.insertTransaction(transaction)
    }

    fun getAllTransactions() = dao.getAllTransactions()

    fun getTotalIncome() = dao.getTotalIncome()

    fun getTotalExpenses() = dao.getTotalExpenses()
}