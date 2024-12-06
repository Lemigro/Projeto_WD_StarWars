package com.example.starwarsapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarsapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val age = binding.edtAge.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (validateFields(name, age, email, password)) {
                performRegistration(name, age, email, password)
            }
        }
    }

    private fun validateFields(name: String, age: String, email: String, password: String): Boolean {
        return when {
            name.isEmpty() -> {
                showToast("O nome não pode estar vazio.")
                false
            }
            age.isEmpty() -> {
                showToast("A idade não pode estar vazia.")
                false
            }
            email.isEmpty() -> {
                showToast("O email não pode estar vazio.")
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showToast("O email é inválido.")
                false
            }
            password.isEmpty() -> {
                showToast("A senha não pode estar vazia.")
                false
            }
            password.length < 6 -> {
                showToast("A senha deve ter pelo menos 6 caracteres.")
                false
            }
            else -> true
        }
    }

    private fun performRegistration(name: String, age: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        saveUserToDatabase(it.uid, name, age, email, password)
                        showToast("Cadastro realizado com sucesso!")
                        navigateToMainActivity()
                    }
                } else {
                    showToast("Erro ao realizar cadastro: ${task.exception?.message}")
                }
            }
    }

    private fun saveUserToDatabase(userId: String, name: String, age: String, email: String, password: String) {
        val userRef = database.getReference("users").child(userId)
        val user = mapOf(
            "name" to name,
            "age" to age,
            "email" to email,
            "password" to password
        )
        userRef.setValue(user)
            .addOnSuccessListener {
                showToast("Usuário salvo no banco de dados.")
            }
            .addOnFailureListener { error ->
                showToast("Erro ao salvar usuário no banco de dados: ${error.message}")
            }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
