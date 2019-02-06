package com.simecsystem.currencyconverter.ui

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import com.mynameismidori.currencypicker.ExtendedCurrency
import com.simecsystem.currencyconverter.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setupBottomNavMenu(navController)
        setupSideNavMenu(navController)
        setupActionBar(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(drawer_layout,
            Navigation.findNavController(this, R.id.nav_host_fragment))
    }

    private fun setupBottomNavMenu(navController: NavController){
        bottomNav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setupSideNavMenu(navController: NavController){
        navView?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setupActionBar(navController: NavController){
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
    }

}
