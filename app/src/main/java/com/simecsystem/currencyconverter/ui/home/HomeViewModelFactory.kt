package com.simecsystem.currencyconverter.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simecsystem.currencyconverter.data.repository.CurrencyRepository

class HomeViewModelFactory(
    private val currencyRepository: CurrencyRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(currencyRepository) as T
    }

}