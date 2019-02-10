package com.simecsystem.currencyconverter.internal

import android.content.Context
import android.net.ConnectivityManager

object ConnectivityChecker {

    fun isChecked(context: Context): Boolean{
        val appContext = context.applicationContext
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}