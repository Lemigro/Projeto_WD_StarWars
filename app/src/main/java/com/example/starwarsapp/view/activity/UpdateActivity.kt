package com.example.starwarsapp.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarsapp.databinding.ActivityUpdateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private val database = FirebaseDatabase.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

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
                updateUserData(updatedName, updatedAge, updatedEmail)
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

    private fun updateUserData(name: String, age: String, email: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userRef = database.getReference("users").child(userId)
            val updatedUser = mapOf(
                "name" to name,
                "age" to age,
                "email" to email
            )

            userRef.setValue(updatedUser)
                .addOnSuccessListener {
                    showToast("Dados atualizados com sucesso.")
                    finish()
                }
                .addOnFailureListener { error ->
                    showToast("Erro ao atualizar os dados: ${error.message}")
                }
        } else {
            showToast("Erro: usuário não autenticado.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
