package com.simecsystem.currencyconverter.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.simecsystem.currencyconverter.data.network.response.CurrencyResponse
import com.simecsystem.currencyconverter.data.retrofit.CurrencyApiService
import com.simecsystem.currencyconverter.internal.NoConnectivityException

class CurrencyNetworkDataSourceImpl(
    private val currencyApiService: CurrencyApiService
) : CurrencyNetworkDataSource {
    private val _downloadedCurrencyResponse = MutableLiveData<CurrencyResponse>()
    override val downloadedCurrencyResponse: LiveData<CurrencyResponse>
        get() = _downloadedCurrencyResponse

    override suspend fun fetchCurrencyResponse() {
        try {
            val fetchedCurrencyResponse = currencyApiService.getLatestCurrencyResponse().await()
            _downloadedCurrencyResponse.postValue(fetchedCurrencyResponse)
        } catch (e: NoConnectivityException){
            Log.d("Connectivity", "No internet connection")
        }
    }
}