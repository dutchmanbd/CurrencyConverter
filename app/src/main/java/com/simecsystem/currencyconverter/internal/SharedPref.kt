package com.simecsystem.currencyconverter.internal

import android.app.Activity
import android.content.Context

class SharedPref(context: Context) {
    private val mSharedPref = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)

    fun read(key: String, defValue: String) = mSharedPref.getString(key, defValue)!!

    fun write(key: String, value: String){
        mSharedPref.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun read(key: String, defValue: Boolean) =
            mSharedPref.getBoolean(key, defValue)

    fun write(key: String, value: Boolean){
        mSharedPref.edit().apply {
            putBoolean(key, value)
            apply()
        }
    }

    fun read(key: String, defValue: Int) =
        mSharedPref.getInt(key, defValue)

    fun write(key: String, value: Int){
        mSharedPref.edit().apply {
            putInt(key, value)
            apply()
        }
    }

}