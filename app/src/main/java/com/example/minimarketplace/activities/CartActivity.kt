package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minimarketplace.R
import com.example.minimarketplace.adapters.CartAdapter
import com.example.minimarketplace.data.DataManager
import com.google.android.material.button.MaterialButton

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerViewCart: RecyclerView
    private lateinit var tvEmptyCart: LinearLayout
    private lateinit var layoutCheckout: com.google.android.material.card.MaterialCardView
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnCheckout: MaterialButton
    private lateinit var btnStartShopping: MaterialButton
    private lateinit var fabBack: com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // No ActionBar setup needed - using NoActionBar theme
        initViews()
        setupRecyclerView()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        updateCartDisplay()
    }

    private fun initViews() {
        recyclerViewCart = findViewById(R.id.recyclerViewCart)
        tvEmptyCart = findViewById(R.id.tvEmptyCart)
        layoutCheckout = findViewById(R.id.layoutCheckout)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        btnCheckout = findViewById(R.id.btnCheckout)
        btnStartShopping = findViewById(R.id.btnStartShopping)
        fabBack = findViewById(R.id.fabBack)
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            items = DataManager.getCartItems().toMutableList(),
            onQuantityChanged = { productId, quantity ->
                DataManager.updateCartItemQuantity(productId, quantity)
                updateCartDisplay()
            },
            onItemRemoved = { productId ->
                DataManager.removeFromCart(productId)
                updateCartDisplay()
            }
        )

        recyclerViewCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(this@CartActivity)
        }
    }

    private fun setupClickListeners() {
        btnCheckout.setOnClickListener {
            if (DataManager.getCartItems().isNotEmpty()) {
                startActivity(Intent(this, CheckoutActivity::class.java))
            }
        }

        btnStartShopping.setOnClickListener {
            finish() // Go back to previous activity (usually home)
        }

        fabBack.setOnClickListener {
            finish()
        }
    }

    private fun updateCartDisplay() {
        val cartItems = DataManager.getCartItems()

        if (cartItems.isEmpty()) {
            recyclerViewCart.visibility = android.view.View.GONE
            tvEmptyCart.visibility = android.view.View.VISIBLE
            layoutCheckout.visibility = android.view.View.GONE
        } else {
            recyclerViewCart.visibility = android.view.View.VISIBLE
            tvEmptyCart.visibility = android.view.View.GONE
            layoutCheckout.visibility = android.view.View.VISIBLE

            cartAdapter.updateItems(cartItems)
            tvTotalPrice.text = "Rp ${String.format("%,.0f", DataManager.getCartTotal())}"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
