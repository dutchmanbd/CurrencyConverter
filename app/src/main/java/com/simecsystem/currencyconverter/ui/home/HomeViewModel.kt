package com.simecsystem.currencyconverter.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel;
import com.simecsystem.currencyconverter.data.repository.CurrencyRepository
import com.simecsystem.currencyconverter.internal.Constant
import com.simecsystem.currencyconverter.internal.SharedPref
import com.simecsystem.currencyconverter.internal.lazyDeferred
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(
//    private val sharedPref: SharedPref,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    fun getRates(isFromOnline: Boolean) = lazyDeferred {
        currencyRepository.getRates(isFromOnline)
    }

    fun getRate(code1: String, code2: String) = lazyDeferred {
        currencyRepository.getRate(code1, code2)
    }

//    fun updateDate(date: String){
//        sharedPref.write(Constant.LAST_UPDATE_DATE, date)
//    }
//
//    fun loadDate() = {
//        val currentDate = SimpleDateFormat("dd-MM-yyyy").format(Date())
//        sharedPref.read(Constant.LAST_UPDATE_DATE, currentDate)
//    }
//
//    val fromCurrencyCode = sharedPref.read(Constant.DEFAULT_CURRENCY_NAME_1, "BDT")
//
//    fun saveFromCurrencyCode(code: String){
//        sharedPref.write(Constant.DEFAULT_CURRENCY_NAME_1, code)
//    }
//
//    val toCurrencyCode = sharedPref.read(Constant.DEFAULT_CURRENCY_NAME_2, "USD")
//    fun saveToCurrencyCode(code: String){
//        sharedPref.write(Constant.DEFAULT_CURRENCY_NAME_2, code)
//    }
//
//    val defaultPoint = sharedPref.read(Constant.DEFAULT_FLOATING_POINT, 4)
}
