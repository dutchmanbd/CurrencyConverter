package com.simecsystem.currencyconverter.ui.welcome

import androidx.lifecycle.ViewModel
import com.simecsystem.currencyconverter.data.repository.CurrencyRepository
import com.simecsystem.currencyconverter.internal.lazyDeferred

class WelcomeViewModel(
    private val currencyRepository: CurrencyRepository
): ViewModel() {
    fun getRates(isDownloadedFromOnline: Boolean) = lazyDeferred {
        currencyRepository.getRates(isDownloadedFromOnline)
    }
}