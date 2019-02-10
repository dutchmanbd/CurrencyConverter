package com.simecsystem.currencyconverter.ui.home


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mynameismidori.currencypicker.CurrencyPicker

import com.simecsystem.currencyconverter.R
import com.simecsystem.currencyconverter.internal.setAllClickListener
import kotlinx.android.synthetic.main.home_fragment.*
import com.mynameismidori.currencypicker.ExtendedCurrency
import com.simecsystem.currencyconverter.data.db.entity.Rate
import com.simecsystem.currencyconverter.internal.ConnectivityChecker
import com.simecsystem.currencyconverter.internal.Constant
import com.simecsystem.currencyconverter.internal.SharedPref
import com.simecsystem.currencyconverter.ui.scope.ScopeFragment
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : ScopeFragment(), KodeinAware {

    companion object {
        val TAG = "HomeFragment"
    }

    override val kodein by closestKodein()
    private val homeViewModelFactory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel

    private val sharedPref: SharedPref
        get() = SharedPref(context!!)

    private val currencies: List<ExtendedCurrency>
        get() = ExtendedCurrency.getAllCurrencies()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this@HomeFragment, homeViewModelFactory).get(HomeViewModel::class.java)

        if(ConnectivityChecker.isChecked(context!!))
            updateCurrencies()

        setupDefaultCurrency()

        gpFromCurrency.setAllClickListener(View.OnClickListener {
            showFromCurrencyPickerDialog()
        })

        gpToCurrency.setAllClickListener(View.OnClickListener {
            showToCurrencyPickerDialog()
        })

        btn_zero.setOnClickListener {
            setToFromCurrency(0)
        }

        btn_one.setOnClickListener {
            setToFromCurrency(1)
        }

        btn_two.setOnClickListener {
            setToFromCurrency(2)
        }

        btn_three.setOnClickListener {
            setToFromCurrency(3)
        }

        btn_four.setOnClickListener {
            setToFromCurrency(4)
        }
        btn_five.setOnClickListener {
            setToFromCurrency(5)
        }

        btn_six.setOnClickListener {
            setToFromCurrency(6)
        }

        btn_seven.setOnClickListener {
            setToFromCurrency(7)
        }

        btn_eight.setOnClickListener {
            setToFromCurrency(8)
        }

        btn_nine.setOnClickListener {
            setToFromCurrency(9)
        }

        btn_point.setOnClickListener {
            val text = tvFromCurrencyTitle.text.toString()
            if(!text.contains(".")){
                tvFromCurrencyTitle.text = text.plus(".")
                calculateCurrency()
            }
        }

        btn_del.setOnClickListener {
            tvFromCurrencyTitle.apply {
                if(text.length == 1)
                    text = "0"
                else
                    text = text.removeSuffix(text.last().toString())
            }
            calculateCurrency()
        }

        btn_clear.setOnClickListener {
            tvFromCurrencyTitle.apply {
                text = "0"
            }
            calculateCurrency()
        }

        btn_equal.setOnClickListener {
            calculateCurrency()
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun updateCurrencies() = launch {
        try {
            val ratesValue = viewModel.getRates(true).value.await()
            ratesValue.observe(this@HomeFragment, Observer {rates->
                if(rates == null) return@Observer

                val date = SimpleDateFormat("dd-MM-yyyy").format(Date())
                sharedPref.write(Constant.LAST_UPDATE_DATE, date)
            })
        } catch (e: Exception){
            Log.d(TAG, e.localizedMessage)
        }
    }

    private fun setupDefaultCurrency() {

        val fromCurrencyCode = sharedPref.read(Constant.DEFAULT_CURRENCY_CODE_1, "BDT")
        val toCurrencyCode = sharedPref.read(Constant.DEFAULT_CURRENCY_CODE_2, "USD")

        val fromCurrency = ExtendedCurrency.getAllCurrencies().single{currency ->
            currency.code == fromCurrencyCode
        }
        val toCurrency = ExtendedCurrency.getAllCurrencies().single{currency ->
            currency.code == toCurrencyCode
        }

        fromCurrency?.let { currency ->
            tvFromCurrencyName.text = currency.code
            ivFromFlag.setImageResource(currency.flag)
        }

        toCurrency?.let { currency ->
            tvToCurrencyName.text = currency.code
            ivToFlag.setImageResource(currency.flag)
        }


    }


    private fun calculateCurrency() = launch{
        val defaultPoint = sharedPref.read(Constant.DEFAULT_FLOATING_POINT, 4)
        val sourceAmount = tvFromCurrencyTitle.text.toString().toDouble()
        val rateValue = viewModel.getRate(tvFromCurrencyName.text.toString(), tvToCurrencyName.text.toString())
        rateValue.value.await().observe(this@HomeFragment, Observer { rates ->
            if(rates == null) return@Observer

            val rate1 = rates.single { rate ->
                rate.code == tvFromCurrencyName.text.toString()
            }

            val rate2 = rates.single { rate ->
                rate.code == tvToCurrencyName.text.toString()
            }

            val fromRate = rate1.currency
            val toRate = rate2.currency

            val targetAmount = (sourceAmount / fromRate) * toRate
            tvToCurrency.text = String.format("%.0${defaultPoint}f", targetAmount)
        })



    }

    private fun setToFromCurrency(value: Int) {
        val prevText = tvFromCurrencyTitle.text.toString()
        val text = if(prevText == "0")
            "$value"
        else
            "${tvFromCurrencyTitle.text}$value"

        tvFromCurrencyTitle.text = text

        calculateCurrency()
    }

    private fun showFromCurrencyPickerDialog() {
        val picker = CurrencyPicker.newInstance("Select Currency")
        picker.setListener { name, code, symbol, flagDrawableResId ->
            picker.dismiss()
            tvFromCurrencyName.text = code
            ivFromFlag.setImageResource(flagDrawableResId)
            sharedPref.write(Constant.DEFAULT_CURRENCY_CODE_1, code)
            Log.d(TAG, "from: $code")
        }
        picker.show(activity!!.supportFragmentManager, "FROM_CURRENCY_PICKER")

    }

    private fun showToCurrencyPickerDialog() {
        val picker = CurrencyPicker.newInstance("Select Currency")
        picker.setListener { name, code, symbol, flagDrawableResId ->
            picker.dismiss()
            tvToCurrencyName.text = code
            ivToFlag.setImageResource(flagDrawableResId)
            sharedPref.write(Constant.DEFAULT_CURRENCY_CODE_2, code)
            Log.d(TAG, "to: $code")
        }
        picker.show(activity!!.supportFragmentManager, "TO_CURRENCY_PICKER")

    }

}
