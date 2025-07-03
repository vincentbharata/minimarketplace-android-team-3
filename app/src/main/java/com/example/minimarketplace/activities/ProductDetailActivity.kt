package com.example.minimarketplace.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.minimarketplace.R
import com.example.minimarketplace.data.DataManager
import com.example.minimarketplace.data.Product
import com.google.android.material.button.MaterialButton

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var ivProductImage: ImageView
    private lateinit var tvProductName: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var tvProductDescription: TextView
    private lateinit var tvProductCategory: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var tvReviewCount: TextView
    private lateinit var btnAddToCart: MaterialButton
    private lateinit var fabClose: com.google.android.material.floatingactionbutton.FloatingActionButton

    private var currentProduct: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // No ActionBar setup needed - using NoActionBar theme
        initViews()
        loadProductDetails()
        setupClickListeners()
    }

    private fun initViews() {
        ivProductImage = findViewById(R.id.ivProductImage)
        tvProductName = findViewById(R.id.tvProductName)
        tvProductPrice = findViewById(R.id.tvProductPrice)
        tvProductDescription = findViewById(R.id.tvProductDescription)
        tvProductCategory = findViewById(R.id.tvProductCategory)
        ratingBar = findViewById(R.id.ratingBar)
        tvReviewCount = findViewById(R.id.tvReviewCount)
        btnAddToCart = findViewById(R.id.btnAddToCart)
        fabClose = findViewById(R.id.fabClose)
    }

    private fun loadProductDetails() {
        val productId = intent.getStringExtra("product_id")
        currentProduct = DataManager.sampleProducts.find { it.id == productId }

        currentProduct?.let { product ->
            tvProductName.text = product.name
            tvProductPrice.text = product.getFormattedPrice()
            tvProductDescription.text = product.description
            tvProductCategory.text = "Kategori: ${product.category}"
            ratingBar.rating = product.rating
            tvReviewCount.text = "(${product.reviewCount} ulasan)"

            Glide.with(this)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(ivProductImage)
        }
    }

    private fun setupClickListeners() {
        btnAddToCart.setOnClickListener {
            currentProduct?.let { product ->
                DataManager.addToCart(product)
                Toast.makeText(this, "${product.name} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            }
        }

        fabClose.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
