package com.example.minimarketplace.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minimarketplace.R
import com.example.minimarketplace.data.Order
import com.example.minimarketplace.data.OrderStatus

class OrderAdapter(
    private val orders: List<Order>
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvOrderId: TextView = view.findViewById(R.id.tvOrderId)
        val tvOrderDate: TextView = view.findViewById(R.id.tvOrderDate)
        val tvOrderTotal: TextView = view.findViewById(R.id.tvOrderTotal)
        val tvOrderStatus: TextView = view.findViewById(R.id.tvOrderStatus)
        val tvOrderItems: TextView = view.findViewById(R.id.tvOrderItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        holder.tvOrderId.text = "Order ID: ${order.id}"
        holder.tvOrderDate.text = order.orderDate
        holder.tvOrderTotal.text = order.getFormattedTotal()

        // Set status text and color
        val statusText = when (order.status) {
            OrderStatus.PENDING -> "Menunggu"
            OrderStatus.PROCESSING -> "Diproses"
            OrderStatus.SHIPPED -> "Dikirim"
            OrderStatus.DELIVERED -> "Terkirim"
            OrderStatus.CANCELLED -> "Dibatalkan"
        }
        holder.tvOrderStatus.text = statusText

        // Set status color
        val statusColor = when (order.status) {
            OrderStatus.PENDING -> android.graphics.Color.parseColor("#FF9800")
            OrderStatus.PROCESSING -> android.graphics.Color.parseColor("#2196F3")
            OrderStatus.SHIPPED -> android.graphics.Color.parseColor("#9C27B0")
            OrderStatus.DELIVERED -> android.graphics.Color.parseColor("#4CAF50")
            OrderStatus.CANCELLED -> android.graphics.Color.parseColor("#F44336")
        }
        holder.tvOrderStatus.setTextColor(statusColor)

        // Show order items
        val itemsText = order.items.joinToString("\n") {
            "${it.product.name} x${it.quantity}"
        }
        holder.tvOrderItems.text = itemsText
    }

    override fun getItemCount() = orders.size
}
