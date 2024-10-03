package com.example.starwarsapp.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarsapp.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val userName = intent.getStringExtra("USER_NAME") ?: ""
        val userAge = intent.getStringExtra("USER_AGE") ?: ""
        val userEmail = intent.getStringExtra("USER_EMAIL") ?: ""

        binding.edtName.setText(userName)
        binding.edtAge.setText(userAge)
        binding.edtEmail.setText(userEmail)

        binding.btnUpdate.setOnClickListener {
            val updatedName = binding.edtName.text.toString()
            val updatedAge = binding.edtAge.text.toString()
            val updatedEmail = binding.edtEmail.text.toString()
            val updatedPassword = binding.edtPassword.text.toString()

            if (validateFields(updatedName, updatedAge, updatedEmail, updatedPassword)) {
                updateUserData(updatedName, updatedAge, updatedEmail, updatedPassword)
            } else {
                showToast("Preencha todos os campos corretamente.")
            }
        }
    }

    private fun validateFields(
        name: String,
        age: String,
        email: String,
        password: String
    ): Boolean {
        return name.isNotEmpty() && age.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }

    private fun updateUserData(name: String, age: String, email: String, password: String) {
        showToast("Cadastro atualizado com sucesso!")
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}