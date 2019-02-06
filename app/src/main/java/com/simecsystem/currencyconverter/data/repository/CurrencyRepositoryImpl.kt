package com.simecsystem.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import com.simecsystem.currencyconverter.data.db.CurrencyDao
import com.simecsystem.currencyconverter.data.db.entity.Rate
import com.simecsystem.currencyconverter.data.network.CurrencyNetworkDataSource
import com.simecsystem.currencyconverter.data.network.response.CurrencyResponse
import kotlinx.coroutines.Dispatchers
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



    override suspend fun getRates(): LiveData<List<Rate>> {
        return withContext(Dispatchers.IO){
            initCurrencyData()
            return@withContext currencyDao.getRates()
        }
    }

    override suspend fun getRate(code: String): LiveData<Rate> {
        return withContext(Dispatchers.IO){
            initCurrencyData()
            return@withContext currencyDao.getRate(code)
        }
    }

    private fun initCurrencyData(){

    }

    private fun persistFetchedCurrencyResponse(currencyResponse: CurrencyResponse) {

    }


}