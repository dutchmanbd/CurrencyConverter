package com.simecsystem.currencyconverter.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simecsystem.currencyconverter.internal.SharedPref

class SplashScreenViewModelFactory(
//    private val sharedPref: SharedPref
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashScreenViewModel() as T
    }
}