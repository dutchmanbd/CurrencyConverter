package com.simecsystem.currencyconverter.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.simecsystem.currencyconverter.data.db.entity.Rate

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(rates: List<Rate>)

    @Query("select * from Rate")
    fun getRates(): LiveData<List<Rate>>

    @Query("select * from Rate where code = :code1 or code = :code2")
    fun getRate(code1: String, code2: String): LiveData<List<Rate>>
}