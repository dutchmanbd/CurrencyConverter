package com.simecsystem.currencyconverter.ui.welcome

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.simecsystem.currencyconverter.R
import com.simecsystem.currencyconverter.internal.ConnectivityChecker
import com.simecsystem.currencyconverter.internal.Constant
import com.simecsystem.currencyconverter.internal.SharedPref
import com.simecsystem.currencyconverter.ui.MainActivity
import com.simecsystem.currencyconverter.ui.scope.ScopeActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import com.synnapps.carouselview.ImageListener
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class WelcomeActivity : ScopeActivity(), KodeinAware {

    private val images = arrayOf(R.drawable.ic_slide_1, R.drawable.ic_slide_2, R.drawable.ic_slide_3)

    private val imageListener = ImageListener { position, imageView -> imageView.setImageResource(images[position]) }

    override val kodein by closestKodein()
    private val welcomeViewModelFactory: WelcomeViewModelFactory by instance()
    private lateinit var viewModel: WelcomeViewModel

    private val sharedPref : SharedPref by lazy {
        SharedPref(this@WelcomeActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(sharedPref.read(Constant.IS_ALREADY_DOWNLOAD_CURRENCY, false))
            navigateToMainActivity()

        setContentView(R.layout.activity_welcome)

        viewModel = ViewModelProviders.of(this@WelcomeActivity, welcomeViewModelFactory).get(WelcomeViewModel::class.java)

        carouselView?.apply {
            pageCount = images.size
            setImageListener(imageListener)
        }

        btnFetch.setOnClickListener {
            if(ConnectivityChecker.isChecked(applicationContext)){
                btnFetch.visibility = View.GONE
                fetchCurrency()
            } else{
                Toast.makeText(applicationContext, "Please connect internet", Toast.LENGTH_SHORT).show()
            }
        }

        btnNext.setOnClickListener {
            navigateToMainActivity()
        }

    }

    private fun navigateToMainActivity(){
        val mainIntent = Intent(this@WelcomeActivity, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    @SuppressLint("SimpleDateFormat")
    private fun fetchCurrency() = launch {
        try{
            gpLoading.visibility = View.VISIBLE
            val rateData = viewModel.getRates(true).value.await()
            rateData.observe(this@WelcomeActivity, Observer {rates ->
                if(rates == null) return@Observer

                gpLoading.visibility = View.GONE
                btnFetch.visibility = View.GONE
                btnNext.visibility = View.VISIBLE
                val date = SimpleDateFormat("dd-MM-yyyy").format(Date())
                sharedPref.write(Constant.LAST_UPDATE_DATE, date)

                sharedPref.write(Constant.IS_ALREADY_DOWNLOAD_CURRENCY, true)
            })
        } catch (e: Exception){
            btnFetch.visibility = View.VISIBLE
            gpLoading.visibility = View.GONE
        }
    }

//    private fun isOnline(): Boolean{
//        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
//                as ConnectivityManager
//        val networkInfo = connectivityManager.activeNetworkInfo
//
//        return networkInfo != null && networkInfo.isConnected
//    }



}
