package com.example.starwarsapp.view.activity

import android.content.Intent
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

        loadUserData()

        binding.btnUpdate.setOnClickListener {
            val updatedName = binding.edtName.text.toString()
            val updatedAge = binding.edtAge.text.toString()
            val updatedEmail = binding.edtEmail.text.toString()
            val newPassword = binding.edtNewPassword.text.toString()

            if (validateFields(updatedName, updatedAge, updatedEmail)) {
                updateUserData(updatedName, updatedAge, updatedEmail, newPassword)
            } else {
                showToast("Preencha todos os campos corretamente.")
            }
        }

        binding.btnBack.setOnClickListener {
            navigateToMenu()
        }
    }

    private fun navigateToMenu() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userRef = database.getReference("users").child(userId)
            userRef.get().addOnSuccessListener { snapshot ->
                val userName = snapshot.child("name").getValue(String::class.java) ?: ""
                val userAge = snapshot.child("age").getValue(String::class.java) ?: ""
                val userEmail = snapshot.child("email").getValue(String::class.java) ?: ""

                binding.edtName.setText(userName)
                binding.edtAge.setText(userAge)
                binding.edtEmail.setText(userEmail)
            }.addOnFailureListener { error ->
                showToast("Erro ao carregar os dados: ${error.message}")
            }
        } else {
            showToast("Erro: usuário não autenticado.")
        }
    }

    private fun validateFields(name: String, age: String, email: String): Boolean {
        return name.isNotEmpty() && age.isNotEmpty() && email.isNotEmpty()
    }

    private fun updateUserData(name: String, age: String, email: String, newPassword: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userRef = database.getReference("users").child(userId)
            val updatedUser = mutableMapOf<String, Any>(
                "name" to name,
                "age" to age,
                "email" to email
            )
            if (newPassword.isNotEmpty()) {
                auth.currentUser?.updatePassword(newPassword)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showToast("Senha atualizada com sucesso.")
                    } else {
                        showToast("Erro ao atualizar a senha: ${task.exception?.message}")
                    }
                }
                updatedUser["password"] = newPassword
            }
            userRef.updateChildren(updatedUser)
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
