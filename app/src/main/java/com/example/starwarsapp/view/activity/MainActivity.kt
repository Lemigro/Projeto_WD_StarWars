package com.example.starwarsapp.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.example.starwarsapp.databinding.ActivityMainBinding
import com.example.starwarsapp.view.adapter.MainPagerAdapter
import com.example.starwarsapp.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        checkIfUserIsAuthenticated()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPagerAndTabs()
        binding.btnUpdate.setOnClickListener {
            navigateToUpdateProfile()
        }
        binding.btnBackLogin.setOnClickListener {
            logoutAndNavigateToLoginScreen()
        }
    }

    private fun checkIfUserIsAuthenticated() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            navigateToLoginScreen()
        }
    }

    private fun setupViewPagerAndTabs() {
        val adapter = MainPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_peoples)
                1 -> tab.text = getString(R.string.tab_planets)
                2 -> tab.text = getString(R.string.tab_vehicles)
                3 -> tab.text = getString(R.string.tab_species)
                4 -> tab.text = getString(R.string.tab_starships)
                5 -> tab.text = getString(R.string.tab_films)
                6 -> tab.text = getString(R.string.tab_favorites)
            }
        }.attach()
    }

    private fun navigateToUpdateProfile() {
        val intent = Intent(this, UpdateActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun logoutAndNavigateToLoginScreen() {
        auth.signOut()
        navigateToLoginScreen()
    }
}
