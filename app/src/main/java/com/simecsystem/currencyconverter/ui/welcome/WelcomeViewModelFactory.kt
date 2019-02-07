package com.simecsystem.currencyconverter.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simecsystem.currencyconverter.data.repository.CurrencyRepository

class WelcomeViewModelFactory(
    private val currencyRepository: CurrencyRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WelcomeViewModel(currencyRepository) as T
    }
}