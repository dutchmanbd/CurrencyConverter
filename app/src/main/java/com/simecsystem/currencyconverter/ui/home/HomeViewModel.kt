package com.simecsystem.currencyconverter.ui.home

import androidx.lifecycle.ViewModel;
import com.simecsystem.currencyconverter.data.repository.CurrencyRepository
import com.simecsystem.currencyconverter.internal.lazyDeferred

class HomeViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    fun getRates(isFromOnline: Boolean) = lazyDeferred {
        currencyRepository.getRates(isFromOnline)
    }

    fun getRate(code: String) = lazyDeferred {
        currencyRepository.getRate(code)
    }
}
