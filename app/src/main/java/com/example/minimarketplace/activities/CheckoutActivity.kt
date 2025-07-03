package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.minimarketplace.databinding.ActivityCheckoutBinding
import com.example.minimarketplace.models.Order
import com.example.minimarketplace.utils.CartManager
import java.util.Locale
import java.util.UUID

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        val cartItems = CartManager.getCartItems()
        val total = CartManager.getCartTotal()


        // Auto-fill user data from profile
        autoFillUserData()

        // Display order total
        binding.tvOrderTotal.text = String.format(Locale.getDefault(), "$%.2f", total)
        binding.btnPlaceOrder.text = String.format(Locale.getDefault(), "Place Order - $%.2f", total)

        // Display cart items
        binding.layoutOrderItems.removeAllViews()
        cartItems.forEach { item ->
            val itemView = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, 8, 0, 8)
            }

            val itemText = TextView(this).apply {
                text = "${item.product.name} x${item.quantity}"
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                setTextColor(ContextCompat.getColor(this@CheckoutActivity, android.R.color.darker_gray))
            }

            val priceText = TextView(this).apply {
                text = String.format(Locale.getDefault(), "$%.2f", item.product.price * item.quantity)
                setTextColor(ContextCompat.getColor(this@CheckoutActivity, android.R.color.darker_gray))
            }

            itemView.addView(itemText)
            itemView.addView(priceText)
            binding.layoutOrderItems.addView(itemView)
        }
    }

    private fun autoFillUserData() {
        val currentUser = CartManager.currentUser
        currentUser?.let { user ->
            // Auto-fill form fields with user profile data
            binding.etFullName.setText(user.name)
            binding.etPhoneNumber.setText(user.phone)
            binding.etAddress.setText(user.address)

            // Parse address for city if it contains comma-separated values
            val addressParts = user.address.split(",")
            if (addressParts.size >= 2) {
                binding.etAddress.setText(addressParts[0].trim())
                binding.etCity.setText(addressParts[1].trim())

                // If postal code is in the address, extract it
                if (addressParts.size >= 3) {
                    val lastPart = addressParts.last().trim()
                    // Check if last part contains numbers (likely postal code)
                    val postalCode = lastPart.split(" ").find { it.matches(Regex("\\d+")) }
                    postalCode?.let { binding.etPostalCode.setText(it) }
                }
            }

            // Show a toast to inform user that data was auto-filled
            Toast.makeText(this, "Shipping details auto-filled from your profile ✓", Toast.LENGTH_SHORT).show()
        } ?: run {
            // No user data available, show message
            Toast.makeText(this, "Please complete your profile for faster checkout", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnPlaceOrder.setOnClickListener {
            processOrder()
        }
    }

    private fun processOrder() {
        val name = binding.etFullName.text.toString().trim()
        val phone = binding.etPhoneNumber.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()
        val city = binding.etCity.text.toString().trim()
        val postalCode = binding.etPostalCode.text.toString().trim()

        when {
            name.isEmpty() -> {
                Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show()
                return
            }
            phone.isEmpty() -> {
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
                return
            }
            address.isEmpty() -> {
                Toast.makeText(this, "Please enter your address", Toast.LENGTH_SHORT).show()
                return
            }
            city.isEmpty() -> {
                Toast.makeText(this, "Please enter your city", Toast.LENGTH_SHORT).show()
                return
            }
            postalCode.isEmpty() -> {
                Toast.makeText(this, "Please enter your postal code", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Show processing state
        binding.btnPlaceOrder.apply {
            text = "Processing Order..."
            isEnabled = false
        }

        // Create shipping address and checkout
        val shippingAddress = "$address, $city, $postalCode"
        CartManager.checkout(shippingAddress)

        // Navigate to order history
        val intent = Intent(this, OrderHistoryActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}
