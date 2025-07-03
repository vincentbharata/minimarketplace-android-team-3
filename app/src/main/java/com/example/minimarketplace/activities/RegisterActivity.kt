package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.minimarketplace.databinding.ActivityRegisterBinding
import com.example.minimarketplace.models.User
import com.example.minimarketplace.utils.CartManager

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Back button functionality
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (validateInput(name, email, password, confirmPassword)) {
                CartManager.currentUser = User(
                    id = "user_${System.currentTimeMillis()}",
                    name = name,
                    email = email,
                    phone = "+1234567890",
                    address = "123 Main Street, City, State 12345"
                )
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateInput(name: String, email: String, password: String, confirmPassword: String): Boolean {
        hideError()

        when {
            name.isEmpty() -> showError("Name is required")
            email.isEmpty() -> showError("Email is required")
            password.isEmpty() -> showError("Password is required")
            password != confirmPassword -> showError("Passwords do not match")
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> showError("Please enter a valid email")
            else -> return true
        }
        return false
    }

    private fun hideError() {
        binding.tvError.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }
}
