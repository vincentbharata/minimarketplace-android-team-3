package com.example.minimarketplace.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minimarketplace.models.Order
import com.example.minimarketplace.databinding.ItemOrderBinding

class OrderAdapter(
    private val orders: List<Order>
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        with(holder.binding) {
            tvOrderId.text = "Order #${order.id.takeLast(8)}"
            tvOrderDate.text = order.orderDate
            tvOrderStatus.text = order.status
            tvOrderTotal.text = "$${String.format("%.2f", order.totalAmount)}"
            tvShippingAddress.text = order.shippingAddress

            // Set status color
            when (order.status) {
                "Processing" -> tvOrderStatus.setBackgroundColor(
                    holder.itemView.context.getColor(android.R.color.holo_blue_light)
                )
                "Shipped" -> tvOrderStatus.setBackgroundColor(
                    holder.itemView.context.getColor(android.R.color.holo_orange_light)
                )
                "Delivered" -> tvOrderStatus.setBackgroundColor(
                    holder.itemView.context.getColor(android.R.color.holo_green_light)
                )
                else -> tvOrderStatus.setBackgroundColor(
                    holder.itemView.context.getColor(android.R.color.darker_gray)
                )
            }

            // Display order items
            layoutOrderItems.removeAllViews()
            for (item in order.items) {
                val itemView = LinearLayout(holder.itemView.context).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 0, 0, 8)
                    }
                }

                val itemText = TextView(holder.itemView.context).apply {
                    text = "${item.product.name} x${item.quantity}"
                    textSize = 14f
                    setTextColor(holder.itemView.context.getColor(android.R.color.darker_gray))
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                }

                val priceText = TextView(holder.itemView.context).apply {
                    text = "$${String.format("%.2f", item.product.price * item.quantity)}"
                    textSize = 14f
                    setTextColor(holder.itemView.context.getColor(android.R.color.darker_gray))
                }

                itemView.addView(itemText)
                itemView.addView(priceText)
                layoutOrderItems.addView(itemView)
            }
        }
    }

    override fun getItemCount() = orders.size
}
