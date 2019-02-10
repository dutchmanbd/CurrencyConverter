package com.simecsystem.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import com.mynameismidori.currencypicker.ExtendedCurrency
import com.simecsystem.currencyconverter.data.db.CurrencyDao
import com.simecsystem.currencyconverter.data.db.entity.Rate
import com.simecsystem.currencyconverter.data.network.CurrencyNetworkDataSource
import com.simecsystem.currencyconverter.data.network.response.CurrencyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyRepositoryImpl(
    private val currencyDao: CurrencyDao,
    private val currencyNetworkDataSource: CurrencyNetworkDataSource
) : CurrencyRepository {

    init {
        currencyNetworkDataSource.apply {
            downloadedCurrencyResponse.observeForever {currencyResponse->
                persistFetchedCurrencyResponse(currencyResponse)
            }
        }
    }



    override suspend fun getRates(isFetchedFromOnline : Boolean): LiveData<List<Rate>> {
        return withContext(Dispatchers.IO){
            initCurrencyData(isFetchedFromOnline)
            return@withContext currencyDao.getRates()
        }
    }

    override suspend fun getRate(code1: String, code2: String): LiveData<List<Rate>> {
        return withContext(Dispatchers.IO){
            //initCurrencyData()
            return@withContext currencyDao.getRate(code1, code2)
        }
    }

    private suspend fun initCurrencyData(isFetchedFromOnline : Boolean){
        if(isFetchedFromOnline){
            fetchCurrencyResponse()
        }
    }

    private suspend fun fetchCurrencyResponse(){
        currencyNetworkDataSource.fetchCurrencyResponse()
    }

    private fun persistFetchedCurrencyResponse(currencyResponse: CurrencyResponse) {
        val rates = ArrayList<Rate>()
        val currencies = ExtendedCurrency.getAllCurrencies()
        for(currency in currencies){
            val rate = Rate(code = currency.code, currency = currencyResponse.rates.get(currency.code).asDouble)
            rates.add(rate)
        }
        GlobalScope.launch(Dispatchers.IO){
            currencyDao.upsert(rates)
        }
    }


}