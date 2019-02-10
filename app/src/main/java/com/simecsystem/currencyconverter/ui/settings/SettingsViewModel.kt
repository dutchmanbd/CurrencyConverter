package com.simecsystem.currencyconverter.ui.settings

import androidx.lifecycle.ViewModel;
import com.simecsystem.currencyconverter.data.repository.CurrencyRepository
import com.simecsystem.currencyconverter.internal.lazyDeferred

class SettingsViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    fun getRates(isFromOnline: Boolean) = lazyDeferred {
        currencyRepository.getRates(isFromOnline)
    }
}
