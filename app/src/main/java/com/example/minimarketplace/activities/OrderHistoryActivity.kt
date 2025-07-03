package com.example.minimarketplace.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minimarketplace.R
import com.example.minimarketplace.adapters.OrderAdapter
import com.example.minimarketplace.data.DataManager

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerViewOrders: RecyclerView
    private lateinit var tvEmptyOrders: TextView
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        supportActionBar?.title = "Riwayat Pesanan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        setupRecyclerView()
        updateOrderDisplay()
    }

    private fun initViews() {
        recyclerViewOrders = findViewById(R.id.recyclerViewOrders)
        tvEmptyOrders = findViewById(R.id.tvEmptyOrders)
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter(DataManager.getOrders())

        recyclerViewOrders.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(this@OrderHistoryActivity)
        }
    }

    private fun updateOrderDisplay() {
        val orders = DataManager.getOrders()

        if (orders.isEmpty()) {
            recyclerViewOrders.visibility = android.view.View.GONE
            tvEmptyOrders.visibility = android.view.View.VISIBLE
        } else {
            recyclerViewOrders.visibility = android.view.View.VISIBLE
            tvEmptyOrders.visibility = android.view.View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
