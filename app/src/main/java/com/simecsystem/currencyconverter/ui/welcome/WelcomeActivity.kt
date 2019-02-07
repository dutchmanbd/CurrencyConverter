package com.simecsystem.currencyconverter.ui.welcome

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.simecsystem.currencyconverter.R
import com.simecsystem.currencyconverter.ui.MainActivity
import com.simecsystem.currencyconverter.ui.scope.ScopeActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import com.synnapps.carouselview.ImageListener
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.lang.Exception


class WelcomeActivity : ScopeActivity(), KodeinAware {

    private val images = arrayOf(R.drawable.ic_slide_1, R.drawable.ic_slide_2, R.drawable.ic_slide_3)

    private val imageListener = ImageListener { position, imageView -> imageView.setImageResource(images[position]) }

    override val kodein by closestKodein()
    private val welcomeViewModelFactory: WelcomeViewModelFactory by instance()
    private lateinit var viewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        viewModel = ViewModelProviders.of(this@WelcomeActivity, welcomeViewModelFactory).get(WelcomeViewModel::class.java)

        carouselView?.apply {
            pageCount = images.size
            setImageListener(imageListener)
        }

        btnFetch.setOnClickListener {
            if(isOnline()){
                fetchCurrency()
            } else{
                Toast.makeText(applicationContext, "Please connect internet", Toast.LENGTH_SHORT).show()
            }
        }

        btnNext.setOnClickListener {
            val mainIntent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }

    }

    private fun fetchCurrency() = launch {
        try{
            gpLoading.visibility = View.VISIBLE
            val rateData = viewModel.getRates(true).value.await()
            rateData.observe(this@WelcomeActivity, Observer {rates ->
                if(rates == null) return@Observer

                gpLoading.visibility = View.GONE
                btnFetch.visibility = View.GONE
                btnNext.visibility = View.VISIBLE
            })
        } catch (e: Exception){
            gpLoading.visibility = View.GONE
        }
    }

    private fun isOnline(): Boolean{
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }



}
