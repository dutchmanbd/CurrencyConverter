package com.simecsystem.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import com.simecsystem.currencyconverter.data.db.entity.Rate

interface CurrencyRepository {
    suspend fun getRates(): LiveData<List<Rate>>
    suspend fun getRate(code: String): LiveData<Rate>
}