package com.example.minimarketplace.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minimarketplace.adapters.OrderAdapter
import com.example.minimarketplace.databinding.ActivityOrderHistoryBinding
import com.example.minimarketplace.utils.CartManager

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupRecyclerView()
        setupClickListeners()
        updateUI()
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter(CartManager.getOrders())

        binding.rvOrders.apply {
            layoutManager = LinearLayoutManager(this@OrderHistoryActivity)
            adapter = orderAdapter
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun updateUI() {
        val orders = CartManager.getOrders()

        if (orders.isEmpty()) {
            binding.layoutEmptyOrders.visibility = View.VISIBLE
            binding.rvOrders.visibility = View.GONE
            binding.tvOrderCount.text = "0 orders"
        } else {
            binding.layoutEmptyOrders.visibility = View.GONE
            binding.rvOrders.visibility = View.VISIBLE
            binding.tvOrderCount.text = "${orders.size} orders"
        }
    }
}
