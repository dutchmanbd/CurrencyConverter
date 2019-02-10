package com.simecsystem.currencyconverter.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simecsystem.currencyconverter.data.repository.CurrencyRepository

class SettingsViewModelFactory(
    private val currencyRepository: CurrencyRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel(currencyRepository) as T
    }

}