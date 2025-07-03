package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.minimarketplace.databinding.ActivityLoginBinding
import com.example.minimarketplace.models.User
import com.example.minimarketplace.utils.CartManager

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide action bar
        supportActionBar?.hide()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInput(email, password)) {
                // Save user data
                CartManager.currentUser = User(
                    id = "user_${System.currentTimeMillis()}",
                    name = "John Doe",
                    email = email,
                    phone = "+1234567890",
                    address = "123 Main Street, City, State 12345"
                )

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Remove bottom navigation code since we removed it from the layout
    }

    private fun validateInput(email: String, password: String): Boolean {
        hideError()

        when {
            email.isEmpty() -> {
                showError("Email is required")
                return false
            }
            password.isEmpty() -> {
                showError("Password is required")
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showError("Please enter a valid email")
                return false
            }
            else -> return true
        }
    }

    private fun hideError() {
        binding.tvError.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }
}
