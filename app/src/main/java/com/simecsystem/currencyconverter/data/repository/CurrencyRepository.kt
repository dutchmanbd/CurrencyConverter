package com.simecsystem.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import com.simecsystem.currencyconverter.data.db.entity.Rate

interface CurrencyRepository {
    suspend fun getRates(isFetchedFromOnline: Boolean): LiveData<List<Rate>>
    suspend fun getRate(code1: String, code2: String): LiveData<List<Rate>>
}