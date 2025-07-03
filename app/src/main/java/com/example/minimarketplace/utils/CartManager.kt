package com.example.minimarketplace.utils

import com.example.minimarketplace.R
import com.example.minimarketplace.models.CartItem
import com.example.minimarketplace.models.Order
import com.example.minimarketplace.models.Product
import com.example.minimarketplace.models.User

object CartManager {
    private val cartItems = mutableListOf<CartItem>()
    private val orders = mutableListOf<Order>()
    var currentUser: User? = null

    // Sample products
    val products = listOf(
        Product("1", "Wireless Headphones", "High-quality wireless headphones with noise cancellation", 299.99, R.drawable.product_headphones, "Electronics", 4.5f, 128),
        Product("2", "Smart Watch", "Fitness tracker with heart rate monitor and GPS", 199.99, R.drawable.product_smartwatch, "Electronics", 4.2f, 89),
        Product("3", "Coffee Maker", "Programmable coffee maker with thermal carafe", 129.99, R.drawable.product_coffee_maker, "Home", 4.0f, 234),
        Product("4", "Yoga Mat", "Non-slip yoga mat for exercise and meditation", 39.99, R.drawable.product_yoga_mat, "Sports", 4.7f, 156),
        Product("5", "Backpack", "Durable travel backpack with multiple compartments", 89.99, R.drawable.product_backpack, "Travel", 4.3f, 67)
    )

    fun addToCart(product: Product, quantity: Int = 1) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            cartItems.add(CartItem(product, quantity))
        }
    }

    fun removeFromCart(productId: String) {
        cartItems.removeAll { it.product.id == productId }
    }

    fun updateQuantity(productId: String, quantity: Int) {
        cartItems.find { it.product.id == productId }?.quantity = quantity
    }

    fun getCartItems(): List<CartItem> = cartItems.toList()

    fun getCartTotal(): Double = cartItems.sumOf { it.product.price * it.quantity }

    fun getCartItemCount(): Int = cartItems.sumOf { it.quantity }

    fun clearCart() = cartItems.clear()

    fun checkout(shippingAddress: String) {
        val order = Order(
            id = "order_${System.currentTimeMillis()}",
            userId = "user_${System.currentTimeMillis()}", // Adding required userId parameter
            items = cartItems.toList(),
            totalAmount = getCartTotal(),
            orderDate = "2024-01-${(1..30).random()}",
            status = "Processing",
            shippingAddress = shippingAddress
        )
        orders.add(order)
        clearCart()
    }

    fun getOrders(): List<Order> = orders.toList()
}
