package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minimarketplace.R
import com.example.minimarketplace.databinding.ActivityProductDetailBinding
import com.example.minimarketplace.models.Product
import com.example.minimarketplace.utils.CartManager
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var product: Product
    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Get product from intent
        product = intent.getParcelableExtra("product") ?: run {
            finish()
            return
        }

        setupUI()
        setupClickListeners()
        updateCartBadge()
    }

    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }

    private fun setupUI() {
        binding.apply {
            ivProduct.setImageResource(product.imageResId)
            tvProductName.text = product.name
            tvCategory.text = product.category
            tvRating.text = "${product.rating} (${product.reviewCount} reviews)"
            tvPrice.text = "$${product.price}"
            tvDescription.text = product.description
            tvQuantity.text = quantity.toString()
        }
        updateAddToCartButton()
    }

    private fun updateCartBadge() {
        val cartCount = CartManager.getCartItems().sumOf { it.quantity }
        if (cartCount > 0) {
            binding.tvCartBadge.visibility = View.VISIBLE
            binding.tvCartBadge.text = if (cartCount > 99) "99+" else cartCount.toString()
        } else {
            binding.tvCartBadge.visibility = View.GONE
        }
    }

    private fun updateAddToCartButton() {
        val totalPrice = product.price * quantity
        binding.btnAddToCart.text = "Add to Cart - $${String.format(Locale.getDefault(), "%.2f", totalPrice)}"
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        binding.btnDecrease.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvQuantity.text = quantity.toString()
                updateAddToCartButton()
            }
        }

        binding.btnIncrease.setOnClickListener {
            quantity++
            binding.tvQuantity.text = quantity.toString()
            updateAddToCartButton()
        }

        binding.btnAddToCart.setOnClickListener {
            CartManager.addToCart(product, quantity)

            // Show feedback
            binding.btnAddToCart.apply {
                text = "Added to Cart! ✓"
                setBackgroundColor(resources.getColor(android.R.color.holo_green_dark, theme))
                postDelayed({
                    updateAddToCartButton()
                    setBackgroundColor(resources.getColor(R.color.purple_500, theme))
                }, 1000)
            }

            Toast.makeText(this@ProductDetailActivity, "${product.name} added to cart!", Toast.LENGTH_SHORT).show()
            updateCartBadge()
        }
    }
}
