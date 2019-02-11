package com.simecsystem.currencyconverter.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simecsystem.currencyconverter.data.repository.CurrencyRepository
import com.simecsystem.currencyconverter.internal.SharedPref

class WelcomeViewModelFactory(
//    private val sharedPref: SharedPref,
    private val currencyRepository: CurrencyRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WelcomeViewModel(currencyRepository) as T
    }
}