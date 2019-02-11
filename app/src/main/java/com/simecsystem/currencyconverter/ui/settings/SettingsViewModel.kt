package com.simecsystem.currencyconverter.ui.settings

import androidx.lifecycle.ViewModel;
import com.simecsystem.currencyconverter.data.repository.CurrencyRepository
import com.simecsystem.currencyconverter.internal.Constant
import com.simecsystem.currencyconverter.internal.SharedPref
import com.simecsystem.currencyconverter.internal.lazyDeferred
import java.text.SimpleDateFormat
import java.util.*

class SettingsViewModel(
//    private val sharedPref: SharedPref,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    fun getRates(isFromOnline: Boolean) = lazyDeferred {
        currencyRepository.getRates(isFromOnline)
    }

//    val floatingPoint = sharedPref.read(Constant.DEFAULT_FLOATING_POINT, 4)
//    fun updateFloatingPoint(point: Int){
//        sharedPref.write(Constant.DEFAULT_FLOATING_POINT, point)
//    }
//    private val currentDate = SimpleDateFormat("dd-MM-yyyy").format(Date())
//    val date = sharedPref.read(Constant.LAST_UPDATE_DATE, currentDate)
//    fun updateDate(date: String){
//        sharedPref.write(Constant.LAST_UPDATE_DATE, date)
//    }
//
//    val fromCurrencyCode = sharedPref.read(Constant.DEFAULT_CURRENCY_NAME_1, "BDT")
//    fun updateFromCurrencyCode(code: String){
//        sharedPref.write(Constant.DEFAULT_CURRENCY_NAME_1, code)
//    }
//
//    val toCurrencyCode = sharedPref.read(Constant.DEFAULT_CURRENCY_NAME_2, "USD")
//    fun updateToCurrencyCode(code: String){
//        sharedPref.write(Constant.DEFAULT_CURRENCY_NAME_2, code)
//    }

}
