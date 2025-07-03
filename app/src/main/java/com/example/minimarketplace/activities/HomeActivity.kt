package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.minimarketplace.R
import com.example.minimarketplace.data.DataManager
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.card.MaterialCardView

class HomeActivity : AppCompatActivity() {

    private lateinit var tvWelcome: TextView
    private lateinit var cardProducts: MaterialCardView
    private lateinit var cardCart: MaterialCardView
    private lateinit var cardProfile: MaterialCardView
    private lateinit var cardOrderHistory: MaterialCardView
    private lateinit var tvCartCount: TextView
    private lateinit var cardCartBadge: MaterialCardView
    private lateinit var cardCartHeader: MaterialCardView
    private lateinit var tvCartBadge: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // No ActionBar setup needed - using NoActionBar theme
        initViews()
        setupClickListeners()
        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateCartCount()
    }

    private fun initViews() {
        tvWelcome = findViewById(R.id.tvWelcome)
        cardProducts = findViewById(R.id.cardProducts)
        cardCart = findViewById(R.id.cardCart)
        cardProfile = findViewById(R.id.cardProfile)
        cardOrderHistory = findViewById(R.id.cardOrderHistory)
        tvCartCount = findViewById(R.id.tvCartCount)
        cardCartBadge = findViewById(R.id.cardCartBadge)
        cardCartHeader = findViewById(R.id.cardCartHeader)
        tvCartBadge = findViewById(R.id.tvCartBadge)
    }

    private fun setupClickListeners() {
        cardProducts.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java))
        }

        cardCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        // Header cart button also navigates to cart
        cardCartHeader.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        cardProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        cardOrderHistory.setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
        }
    }

    private fun updateUI() {
        val username = DataManager.getUserSession(this)
        tvWelcome.text = username ?: "User"
        updateCartCount()
    }

    private fun updateCartCount() {
        val cartCount = DataManager.getCartItemCount()
        if (cartCount > 0) {
            // Update main cart card badge
            tvCartCount.text = cartCount.toString()
            cardCartBadge.visibility = android.view.View.VISIBLE

            // Update header cart badge
            tvCartBadge.text = cartCount.toString()
            tvCartBadge.visibility = android.view.View.VISIBLE
        } else {
            // Hide both cart badges when empty
            cardCartBadge.visibility = android.view.View.GONE
            tvCartBadge.visibility = android.view.View.GONE
        }
    }
}
