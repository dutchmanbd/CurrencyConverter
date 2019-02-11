package com.simecsystem.currencyconverter.ui.welcome

import androidx.lifecycle.ViewModel
import com.simecsystem.currencyconverter.data.repository.CurrencyRepository
import com.simecsystem.currencyconverter.internal.Constant
import com.simecsystem.currencyconverter.internal.SharedPref
import com.simecsystem.currencyconverter.internal.lazyDeferred

class WelcomeViewModel(
//    private val sharedPref: SharedPref,
    private val currencyRepository: CurrencyRepository
): ViewModel() {
    fun getRates(isDownloadedFromOnline: Boolean) = lazyDeferred {
        currencyRepository.getRates(isDownloadedFromOnline)
    }

//    val isCurrencyDownloaded = sharedPref.read(Constant.IS_ALREADY_DOWNLOAD_CURRENCY, false)
//
//    fun updateCurrencyDownloaded(hasDownloaded: Boolean){
//        sharedPref.write(Constant.IS_ALREADY_DOWNLOAD_CURRENCY, hasDownloaded)
//    }
//
//    fun updateDate(date: String){
//        sharedPref.write(Constant.LAST_UPDATE_DATE, date)
//    }

}