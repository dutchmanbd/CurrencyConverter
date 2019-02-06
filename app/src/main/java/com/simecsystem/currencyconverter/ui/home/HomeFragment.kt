package com.simecsystem.currencyconverter.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mynameismidori.currencypicker.CurrencyPicker

import com.simecsystem.currencyconverter.R
import com.simecsystem.currencyconverter.internal.setAllClickListener
import kotlinx.android.synthetic.main.home_fragment.*
import com.mynameismidori.currencypicker.ExtendedCurrency
import com.simecsystem.currencyconverter.internal.Constant


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
        val TAG = "HomeFragment"
    }

//    private val currencyResponse: CurrencyResponse
//        get() = Constant.convertToList()

    private val currencies: List<ExtendedCurrency>
        get() = ExtendedCurrency.getAllCurrencies()

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val fromCurrency = ExtendedCurrency.getCurrencyByISO("BDT")
        val toCurrency = ExtendedCurrency.getCurrencyByISO("USD")

        fromCurrency?.let {
            tvFromCurrencyName.text = it.code
            ivFromFlag.setImageResource(it.flag)
        }

        toCurrency?.let {
            tvToCurrencyName.text = it.code
            ivToFlag.setImageResource(it.flag)
        }


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
            val text = tvFromCurrency.text.toString()
            if(!text.contains(".")){
                tvFromCurrency.text = text.plus(".")
                calculateCurrency()
            }
        }

        btn_del.setOnClickListener {
            tvFromCurrency.apply {
                if(text.length == 1)
                    text = "0"
                else
                    text = text.removeSuffix(text.last().toString())
            }
            calculateCurrency()
        }

        btn_clear.setOnClickListener {
            tvFromCurrency.apply {
                text = "0"
            }
            calculateCurrency()
        }

        btn_equal.setOnClickListener {
            calculateCurrency()
        }

    }


    private fun calculateCurrency(){
        val sourceAmount = tvFromCurrency.text.toString().toDouble()

        val fromRate = Constant.currencyObject.get(tvFromCurrencyName.text.toString()).asDouble
        val toRate = Constant.currencyObject.get(tvToCurrencyName.text.toString()).asDouble

        val targetAmount = (sourceAmount / fromRate) * toRate


        tvToCurrency.text = String.format("%.04f", targetAmount)
    }

    private fun setToFromCurrency(value: Int) {
        val prevText = tvFromCurrency.text.toString()
        val text = if(prevText == "0")
            "$value"
        else
            "${tvFromCurrency.text}$value"

        tvFromCurrency.text = text

        calculateCurrency()
    }

    private fun showFromCurrencyPickerDialog() {
        val picker = CurrencyPicker.newInstance("Select Currency")
        picker.setListener { name, code, symbol, flagDrawableResId ->
            picker.dismiss()
            tvFromCurrencyName.text = code
            ivFromFlag.setImageResource(flagDrawableResId)
        }
        picker.show(activity!!.supportFragmentManager, "FROM_CURRENCY_PICKER")

    }

    private fun showToCurrencyPickerDialog() {
        val picker = CurrencyPicker.newInstance("Select Currency")
        picker.setListener { name, code, symbol, flagDrawableResId ->
            picker.dismiss()
            tvToCurrencyName.text = code
            ivToFlag.setImageResource(flagDrawableResId)
        }
        picker.show(activity!!.supportFragmentManager, "TO_CURRENCY_PICKER")

    }

}
