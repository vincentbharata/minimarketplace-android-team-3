package com.example.minimarketplace.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minimarketplace.databinding.ActivityProfileBinding
import com.example.minimarketplace.models.User
import com.example.minimarketplace.utils.CartManager
import java.util.Calendar

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        currentUser = CartManager.currentUser
        setupUI()
        setupClickListeners()
        loadUserData()
    }

    private fun setupUI() {
        // Update profile display
        currentUser?.let { user ->
            if (user.name.isNotEmpty()) {
                binding.tvProfileName.text = if (user.name.contains("💜")) user.name else "${user.name} 💜"
            }
            binding.tvProfileEmail.text = user.email.ifEmpty { "Update your email 📧" }
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSettings.setOnClickListener {
            Toast.makeText(this, "Settings coming soon! ⚙️", Toast.LENGTH_SHORT).show()
        }

        binding.btnChangePhoto.setOnClickListener {
            Toast.makeText(this, "Photo upload coming soon! 📸", Toast.LENGTH_SHORT).show()
        }

        binding.etBirthDate.setOnClickListener {
            showDatePicker()
        }

        binding.btnSaveProfile.setOnClickListener {
            saveProfile()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun loadUserData() {
        val currentUser = CartManager.currentUser
        currentUser?.let { user ->
            binding.etFullName.setText(user.name)
            binding.etEmail.setText(user.email)
            binding.etPhone.setText(user.phone)
            binding.etAddress.setText(user.address)
            // Set default values for fields not in our simplified User model
            binding.etBirthDate.setText("01/01/1990")
            binding.etCity.setText("Sample City")
            binding.etPostalCode.setText("12345")
            binding.etProvince.setText("Sample Province")
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
                binding.etBirthDate.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun saveProfile() {
        val name = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()

        // Validation
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in required fields! 📝", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email! 📧", Toast.LENGTH_SHORT).show()
            return
        }

        // Create updated user data
        val updatedUser = User(
            id = CartManager.currentUser?.id ?: "user_${System.currentTimeMillis()}",
            name = name,
            email = email,
            phone = phone,
            address = address
        )

        CartManager.currentUser = updatedUser

        Toast.makeText(this, "Profile updated successfully! ✨", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Your profile is complete! Now you can checkout faster! 🚀", Toast.LENGTH_LONG).show()
    }
}
