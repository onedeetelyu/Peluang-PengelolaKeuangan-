package org.d3ifcool.peluang.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CashDAO {
    @Insert
    fun insertData(cash: Cash)

    @Query("SELECT * FROM cash")
    fun getAllData(): LiveData<List<Cash>>

    @Query("SELECT * FROM cash WHERE nominal > 0.0")
    fun getAllCashIn(): LiveData<List<Cash>>

    @Query("SELECT * FROM cash WHERE nominal < 0.0")
    fun getAllCashOut(): LiveData<List<Cash>>

    @Query("SELECT nominal FROM cash")
    fun getNominal(): LiveData<List<Double>>

    @Update
    fun updateData(cash: Cash)

    @Query("DELETE FROM cash WHERE id IN (:ids)")
    fun deleteData(ids: List<Int>)

    @Query("DELETE FROM cash")
    fun resetData()
}