package com.simecsystem.currencyconverter.root

import android.app.Activity
import android.app.Application
import androidx.preference.PreferenceManager
import com.simecsystem.currencyconverter.data.db.CurrencyDatabase
import com.simecsystem.currencyconverter.data.network.ConnectivityInterceptor
import com.simecsystem.currencyconverter.data.network.ConnectivityInterceptorImpl
import com.simecsystem.currencyconverter.data.network.CurrencyNetworkDataSource
import com.simecsystem.currencyconverter.data.network.CurrencyNetworkDataSourceImpl
import com.simecsystem.currencyconverter.data.repository.CurrencyRepository
import com.simecsystem.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.simecsystem.currencyconverter.data.retrofit.CurrencyApiService
import com.simecsystem.currencyconverter.internal.SharedPref
import com.simecsystem.currencyconverter.ui.home.HomeViewModelFactory
import com.simecsystem.currencyconverter.ui.settings.SettingsViewModelFactory
import com.simecsystem.currencyconverter.ui.splash.SplashScreenViewModelFactory
import com.simecsystem.currencyconverter.ui.welcome.WelcomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CurrencyApp: Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@CurrencyApp))

        bind() from singleton { SharedPref(instance()) }
        bind() from singleton { CurrencyDatabase(instance()) }
        bind() from singleton { instance<CurrencyDatabase>().currencyDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { CurrencyApiService(instance()) }
        bind<CurrencyNetworkDataSource>() with singleton { CurrencyNetworkDataSourceImpl(instance()) }
        bind<CurrencyRepository>() with singleton { CurrencyRepositoryImpl(instance(), instance()) }
//        bind() from provider { SplashScreenViewModelFactory(instance()) }
//        bind() from provider { HomeViewModelFactory(instance(),instance()) }
//        bind() from provider { SettingsViewModelFactory(instance(), instance()) }
//        bind() from provider { WelcomeViewModelFactory(instance(), instance()) }
        bind() from provider { SplashScreenViewModelFactory() }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { SettingsViewModelFactory(instance()) }
        bind() from provider { WelcomeViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()

    }

}