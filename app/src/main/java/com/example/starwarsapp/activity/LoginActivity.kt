package com.example.starwarsapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarsapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o Firebase Auth
        auth = FirebaseAuth.getInstance()

        setupListeners()
    }

    private fun setupListeners() {
        binding.btLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (validateLoginFields(email, password)) {
                performLogin(email, password)
            } else {
                showToast("Preencha todos os campos corretamente.")
            }
        }

        binding.btAdd.setOnClickListener {
            navigateToRegisterScreen()
        }
    }

    private fun validateLoginFields(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun performLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showToast("Login realizado com sucesso!")
                    navigateToMainActivity()
                } else {
                    showToast("Erro ao fazer login: ${task.exception?.message}")
                }
            }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegisterScreen() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}