package com.simecsystem.currencyconverter.ui.settings

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mynameismidori.currencypicker.CurrencyPicker
import com.mynameismidori.currencypicker.ExtendedCurrency

import com.simecsystem.currencyconverter.R
import com.simecsystem.currencyconverter.internal.Constant
import com.simecsystem.currencyconverter.internal.SharedPref
import com.simecsystem.currencyconverter.internal.setAllClickListener
import com.simecsystem.currencyconverter.ui.scope.ScopeFragment
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class SettingsFragment : ScopeFragment(), KodeinAware {


    override val kodein by closestKodein()
    private val settingsViewModelFactory: SettingsViewModelFactory by instance()
    private lateinit var viewModel: SettingsViewModel

    private val sharedPref: SharedPref
        get() = SharedPref(context!!)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this@SettingsFragment, settingsViewModelFactory).get(SettingsViewModel::class.java)



        updateFromAndToCurrency()
        updateLastDate()

        gpFromCurrency.setAllClickListener(View.OnClickListener {
            showFromCurrencyPicker()
        })

        gpToCurrency.setAllClickListener(View.OnClickListener {
            showToCurrencyPicker()
        })

        btnUpdate.setOnClickListener {
            updateCurrencies()
        }

        val floatingPoint = sharedPref.read(Constant.DEFAULT_FLOATING_POINT, 4)

        if (floatingPoint == 2)
            rgPoint.check(R.id.rbTwoDigit)
        else if(floatingPoint == 4)
            rgPoint.check(R.id.rbFourDigit)
        else
            rgPoint.check(R.id.rbSixDigit)

        rgPoint.setOnCheckedChangeListener { _, checkedId ->
            val point = when (checkedId) {
                R.id.rbTwoDigit -> 2
                R.id.rbFourDigit -> 4
                else -> 6
            }
//            viewModel.updateFloatingPoint(point)
            sharedPref.write(Constant.DEFAULT_FLOATING_POINT, point)
        }

    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun updateLastDate() {
        val currentDate = SimpleDateFormat("dd-MM-yyyy").format(Date())
        val date = sharedPref.read(Constant.LAST_UPDATE_DATE, currentDate)
        tvLastUpdateDate.text = "Last update: $date"
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun updateCurrencies() = launch {
        btnUpdate.visibility = View.GONE
        gpLoading.visibility = View.VISIBLE

        try {
            val ratesValue = viewModel.getRates(true).value.await()

            ratesValue.observe(this@SettingsFragment, Observer { rates ->
                if (rates == null) return@Observer

                gpLoading.visibility = View.GONE
                btnUpdate.visibility = View.VISIBLE
                val date = SimpleDateFormat("dd-MM-yyyy").format(Date())
                sharedPref.write(Constant.LAST_UPDATE_DATE, date)
                tvLastUpdateDate.text = "Last update: $date"
            })
        } catch (e: Exception){
            gpLoading.visibility = View.GONE
            btnUpdate.visibility = View.VISIBLE
        }
    }


    private fun showFromCurrencyPicker() {
        val picker = CurrencyPicker.newInstance("Select Currency")
        picker.setListener { name, code, symbol, flagDrawableResId ->
            picker.dismiss()
            tvFromCurrencyCode.text = code
            ivFromCurrency.setImageResource(flagDrawableResId)
//            viewModel.updateFromCurrencyCode(code)
            sharedPref.write(Constant.DEFAULT_CURRENCY_NAME_1, name)
        }
        picker.show(activity!!.supportFragmentManager, "FROM_CURRENCY_PICKER")
    }

    private fun showToCurrencyPicker() {
        val picker = CurrencyPicker.newInstance("Select Currency")
        picker.setListener { name, code, symbol, flagDrawableResId ->
            picker.dismiss()
            tvToCurrencyCode.text = code
            ivToCurrency.setImageResource(flagDrawableResId)
//            viewModel.updateToCurrencyCode(code)
            sharedPref.write(Constant.DEFAULT_CURRENCY_NAME_2, name)
        }
        picker.show(activity!!.supportFragmentManager, "FROM_CURRENCY_PICKER")
    }

    private fun updateFromAndToCurrency() {
//        val fromCurrencyCode = sharedPref.read(Constant.DEFAULT_CURRENCY_NAME_1, "BDT")
//        val toCurrencyCode = sharedPref.read(Constant.DEFAULT_CURRENCY_NAME_2, "USD")

        val fromCurrencyName = sharedPref.read(Constant.DEFAULT_CURRENCY_NAME_1, "Bangladeshi Taka")
        val toCurrencyName = sharedPref.read(Constant.DEFAULT_CURRENCY_NAME_2, "United States Dollar")


//        val fromCurrency = ExtendedCurrency.getCurrencyByName(fromCurrencyName)
//        val toCurrency = ExtendedCurrency.getCurrencyByName(toCurrencyName)

        val fromCurrency = ExtendedCurrency.getAllCurrencies().single{ currency ->
            currency.name == fromCurrencyName
        }
        val toCurrency = ExtendedCurrency.getAllCurrencies().single{ currency ->
            currency.name == toCurrencyName
        }


        fromCurrency?.let { currency ->
            tvFromCurrencyCode.text = currency.code
            ivFromCurrency.setImageResource(currency.flag)
        }

        toCurrency?.let { currency ->
            tvToCurrencyCode.text = currency.code
            ivToCurrency.setImageResource(currency.flag)
        }
    }

}
