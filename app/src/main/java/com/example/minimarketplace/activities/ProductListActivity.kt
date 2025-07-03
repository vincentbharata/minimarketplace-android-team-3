package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minimarketplace.R
import com.example.minimarketplace.adapters.ProductAdapter
import com.example.minimarketplace.data.DataManager

class ProductListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        supportActionBar?.title = "Daftar Produk"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        setupRecyclerView()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerViewProducts)
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(DataManager.sampleProducts) { product ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product_id", product.id)
            startActivity(intent)
        }

        recyclerView.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(this@ProductListActivity, 2)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
