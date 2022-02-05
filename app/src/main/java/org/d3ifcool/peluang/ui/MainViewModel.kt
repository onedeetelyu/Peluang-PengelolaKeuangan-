package org.d3ifcool.peluang.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import org.d3ifcool.peluang.database.Cash
import org.d3ifcool.peluang.database.CashDAO

class MainViewModel(val database: CashDAO) : ViewModel() {
    var cashAll = database.getAllData()
//    var cashOut = database.getAllCashOut()
//    var cashIn = database.getAllCashIn()
    var nominal = database.getNominal()

    fun insertData(cash: Cash) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.insertData(cash)
            }
        }
    }

    fun updateData(cash: Cash) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.updateData(cash)
            }
        }
    }

    fun deleteData(ids: List<Int>) {
        val newIds = ids.toList()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.deleteData(newIds)
            }
        }
    }

    fun resetData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.resetData()
            }
        }
    }
}