package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.minimarketplace.adapters.ProductAdapter
import com.example.minimarketplace.databinding.ActivityHomeBinding
import com.example.minimarketplace.utils.CartManager

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupClickListeners()
        setupProductRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }

    private fun setupUI() {
        // Set user name
        binding.tvUserName.text = CartManager.currentUser?.name ?: "Guest User"
        updateCartBadge()
    }

    private fun setupProductRecyclerView() {
        productAdapter = ProductAdapter(CartManager.products) { product ->
            // Navigate to product detail
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }

        binding.recyclerViewProducts.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 2)
            adapter = productAdapter
        }
    }

    private fun updateCartBadge() {
        val cartCount = CartManager.getCartItemCount()

        // Update professional cart badge
        if (cartCount > 0) {
            binding.tvCartBadge.visibility = android.view.View.VISIBLE
            binding.tvCartBadge.text = if (cartCount > 99) "99+" else cartCount.toString()
        } else {
            binding.tvCartBadge.visibility = android.view.View.GONE
        }
    }

    // Public method for updating cart badge from adapter
    fun updateCartBadgeFromAdapter() {
        updateCartBadge()
    }

    private fun setupClickListeners() {
        // Cart button click
        binding.btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        // Profile button click
        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Category buttons
        binding.btnElectronics.setOnClickListener {
            navigateToProductList("Electronics")
        }

        binding.btnHome.setOnClickListener {
            navigateToProductList("Home")
        }

        binding.btnSports.setOnClickListener {
            navigateToProductList("Sports")
        }

        binding.btnTravel.setOnClickListener {
            navigateToProductList("Travel")
        }

        // Bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.example.minimarketplace.R.id.nav_home -> {
                    // Already on home
                    true
                }
                com.example.minimarketplace.R.id.nav_products -> {
                    startActivity(Intent(this, ProductListActivity::class.java))
                    true
                }
                com.example.minimarketplace.R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
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

    private fun navigateToProductList(category: String) {
        val intent = Intent(this, ProductListActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
}
