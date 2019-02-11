package com.simecsystem.currencyconverter.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.simecsystem.currencyconverter.internal.Constant
import com.simecsystem.currencyconverter.internal.SharedPref
import com.simecsystem.currencyconverter.ui.MainActivity
import com.simecsystem.currencyconverter.ui.welcome.WelcomeActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class SplashScreenActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private val splashScreenViewModelFactory: SplashScreenViewModelFactory by instance()
    private lateinit var viewModel: SplashScreenViewModel

    private val sharedPref : SharedPref by lazy {
        SharedPref(this@SplashScreenActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(sharedPref.read(Constant.IS_ALREADY_DOWNLOAD_CURRENCY, false))
            navigateToMainActivity()
        else
            navigateToWelcomeActivity()

//        viewModel = ViewModelProviders.of(this@SplashScreenActivity, splashScreenViewModelFactory)
//            .get(SplashScreenViewModel::class.java)

//        if(viewModel.isCurrencyDownloaded){
//            navigateToMainActivity()
//        } else{
//            navigateToWelcomeActivity()
//        }
    }

    private fun navigateToMainActivity() {
        val mainIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun navigateToWelcomeActivity() {
        val welcomeIntent = Intent(this@SplashScreenActivity, WelcomeActivity::class.java)
        startActivity(welcomeIntent)
        finish()
    }


}