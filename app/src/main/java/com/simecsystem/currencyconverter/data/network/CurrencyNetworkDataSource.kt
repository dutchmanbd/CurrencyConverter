package com.simecsystem.currencyconverter.data.network

import androidx.lifecycle.LiveData
import com.simecsystem.currencyconverter.data.network.response.CurrencyResponse

interface CurrencyNetworkDataSource {
    val downloadedCurrencyResponse: LiveData<CurrencyResponse>

    suspend fun fetchCurrencyResponse()
}