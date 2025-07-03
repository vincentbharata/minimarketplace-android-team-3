package com.example.minimarketplace.data

data class Order(
    val id: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val orderDate: String,
    val status: OrderStatus,
    val shippingAddress: String
) {
    fun getFormattedTotal(): String {
        return "Rp ${String.format("%,.0f", totalAmount)}"
    }
}

enum class OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
