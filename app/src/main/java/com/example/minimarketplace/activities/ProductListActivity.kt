package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minimarketplace.adapters.ProductAdapter
import com.example.minimarketplace.databinding.ActivityProductListBinding
import com.example.minimarketplace.utils.CartManager

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupRecyclerView()
        setupClickListeners()
        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }

    private fun setupRecyclerView() {
        val category = intent.getStringExtra("category")
        val productsToShow = if (category != null) {
            CartManager.products.filter { it.category == category }
        } else {
            CartManager.products
        }

        productAdapter = ProductAdapter(
            productsToShow
        ) { product ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }

        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(this@ProductListActivity)
            adapter = productAdapter
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun updateUI() {
        val productCount = CartManager.products.size
        binding.tvProductCount.text = when (productCount) {
            0 -> "No products available"
            1 -> "1 product available"
            else -> "$productCount products available"
        }
        updateCartBadge()
    }

    private fun updateCartBadge() {
        val cartCount = CartManager.getCartItemCount()
        if (cartCount > 0) {
            binding.tvCartBadge.visibility = android.view.View.VISIBLE
            binding.tvCartBadge.text = if (cartCount > 99) "99+" else cartCount.toString()
        } else {
            binding.tvCartBadge.visibility = android.view.View.GONE
        }
    }
}
