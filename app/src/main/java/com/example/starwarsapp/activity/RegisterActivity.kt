package com.example.starwarsapp.activity

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

        // Inicializando Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Listener para o botão de cadastro
        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text.toString()
            val age = binding.edtAge.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (validateFields(name, age, email, password)) {
                performRegistration(name, age, email, password)
            } else {
                showToast("Preencha todos os campos corretamente.")
            }
        }
    }

    // Função para validar os campos
    private fun validateFields(name: String, age: String, email: String, password: String): Boolean {
        return name.isNotEmpty() && age.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Função para registrar o usuário no Firebase Authentication
    private fun performRegistration(name: String, age: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        saveUserToDatabase(it.uid, name, age, email)  // Salva os dados adicionais no Realtime Database
                        showToast("Cadastro realizado com sucesso!")
                        navigateToMainActivity()
                    }
                } else {
                    // Exibe mensagem de erro ao registrar
                    showToast("Erro ao realizar cadastro: ${task.exception?.message}")
                }
            }
    }

    // Função para salvar os dados no Firebase Realtime Database
    private fun saveUserToDatabase(userId: String, name: String, age: String, email: String) {
        val userRef = database.getReference("users").child(userId)
        val user = mapOf(
            "name" to name,
            "age" to age,
            "email" to email
        )
        userRef.setValue(user)
            .addOnSuccessListener {
                showToast("Usuário salvo no banco de dados.")
            }
            .addOnFailureListener { error ->
                showToast("Erro ao salvar usuário no banco de dados: ${error.message}")
            }
    }

    // Navega para a MainActivity após cadastro bem-sucedido
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Função para exibir mensagens de erro ou sucesso
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
