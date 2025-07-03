package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minimarketplace.adapters.CartAdapter
import com.example.minimarketplace.databinding.ActivityCartBinding
import com.example.minimarketplace.utils.CartManager
import java.util.Locale

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupRecyclerView()
        setupClickListeners()
        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            cartItems = CartManager.getCartItems().toMutableList(),
            onQuantityChanged = { item, newQuantity ->
                CartManager.updateQuantity(item.product.id, newQuantity)
                updateUI()
            },
            onRemoveItem = { item ->
                CartManager.removeFromCart(item.product.id)
                updateUI()
            }
        )

        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnCheckout.setOnClickListener {
            if (CartManager.getCartItems().isNotEmpty()) {
                startActivity(Intent(this, CheckoutActivity::class.java))
            }
        }

        // Handle bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.example.minimarketplace.R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                com.example.minimarketplace.R.id.nav_products -> {
                    startActivity(Intent(this, ProductListActivity::class.java))
                    true
                }
                com.example.minimarketplace.R.id.nav_cart -> {
                    // Already on cart
                    true
                }
                com.example.minimarketplace.R.id.nav_orders -> {
                    startActivity(Intent(this, OrderHistoryActivity::class.java))
                    true
                }
                com.example.minimarketplace.R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun updateUI() {
        val cartItems = CartManager.getCartItems()
        val total = CartManager.getCartTotal()

        if (cartItems.isEmpty()) {
            binding.layoutEmptyCart.visibility = View.VISIBLE
            binding.rvCartItems.visibility = View.GONE
            binding.layoutCheckout.visibility = View.GONE
            binding.tvItemCount.text = "0 items"
        } else {
            binding.layoutEmptyCart.visibility = View.GONE
            binding.rvCartItems.visibility = View.VISIBLE
            binding.layoutCheckout.visibility = View.VISIBLE

            val itemText = if (cartItems.size == 1) "1 item" else "${cartItems.size} items"
            binding.tvItemCount.text = itemText
            binding.tvTotal.text = "$${String.format(Locale.getDefault(), "%.2f", total)}"

            // Update adapter with new data
            cartAdapter.updateItems(cartItems.toMutableList())
        }
    }
}
