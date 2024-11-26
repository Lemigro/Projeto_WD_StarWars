package com.example.starwarsapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarsapp.adapter.MainPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.example.starwarsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar o TabLayout e o ViewPager2
        val adapter = MainPagerAdapter(this)
        binding.viewPager.adapter = adapter

        // Configurar TabLayout com ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "People"
                1 -> tab.text = "Planets"
                2 -> tab.text = "Vehicles"
                3 -> tab.text = "Species"
                4 -> tab.text = "Starships"
                5 -> tab.text = "Films"
                6 -> tab.text = "Favorites"
            }
        }.attach()

        // Botão de Atualizar Cadastro
        binding.btnUpdate.setOnClickListener {
            navigateToUpdateProfile()
        }

        // Botão de Voltar para Login
        binding.btnBackLogin.setOnClickListener {
            navigateToLoginScreen()
        }
    }

    private fun navigateToUpdateProfile() {
        val intent = Intent(this, UpdateActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Finaliza a MainActivity para que o usuário não possa voltar sem login
    }
}