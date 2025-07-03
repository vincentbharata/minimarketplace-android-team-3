package com.example.minimarketplace.data

import android.content.Context
import android.content.SharedPreferences

object DataManager {
    private var cart: MutableList<CartItem> = mutableListOf()
    private var orders: MutableList<Order> = mutableListOf()

    // Data dummy untuk login - username dan password yang valid
    private val dummyUsers = mapOf(
        "admin" to "admin123",
        "user" to "user123",
    )

    // Data dummy untuk informasi pengiriman berdasarkan user
    private val dummyShippingInfo = mapOf(
        "admin" to listOf(
            ShippingInfo(
                fullName = "Administrator",
                phoneNumber = "081234567890",
                address = "Jl. Sudirman No. 123, Senayan",
                city = "Jakarta Pusat",
                postalCode = "10270",
                isDefault = true
            ),
            ShippingInfo(
                fullName = "Admin Kantor",
                phoneNumber = "081234567891",
                address = "Jl. Thamrin No. 456, Menteng",
                city = "Jakarta Pusat",
                postalCode = "10350",
                isDefault = false
            )
        ),
        "user" to listOf(
            ShippingInfo(
                fullName = "User Demo",
                phoneNumber = "082345678901",
                address = "Jl. Gatot Subroto No. 789, Kuningan",
                city = "Jakarta Selatan",
                postalCode = "12950",
                isDefault = true
            ),
            ShippingInfo(
                fullName = "User Rumah",
                phoneNumber = "082345678902",
                address = "Jl. Kemang Raya No. 234, Kemang",
                city = "Jakarta Selatan",
                postalCode = "12560",
                isDefault = false
            )
        )
    )

    // Sample products for the marketplace
    val sampleProducts = listOf(
        Product(
            id = "1",
            name = "Smartphone Samsung Galaxy A54",
            description = "Smartphone dengan kamera 50MP, RAM 8GB, Storage 256GB. Dilengkapi dengan layar Super AMOLED 6.4 inch dan baterai 5000mAh.",
            price = 4500000.0,
            imageUrl = "https://via.placeholder.com/300x300/2196F3/FFFFFF?text=Galaxy+A54",
            category = "Electronics",
            rating = 4.5f,
            reviewCount = 128
        ),
        Product(
            id = "2",
            name = "Sepatu Sneakers Nike Air Max",
            description = "Sepatu olahraga premium dengan teknologi Air Max untuk kenyamanan maksimal. Cocok untuk aktivitas sehari-hari dan olahraga.",
            price = 1200000.0,
            imageUrl = "https://via.placeholder.com/300x300/FF5722/FFFFFF?text=Nike+Air+Max",
            category = "Fashion",
            rating = 4.8f,
            reviewCount = 89
        ),
        Product(
            id = "3",
            name = "Laptop ASUS VivoBook 14",
            description = "Laptop ringan dengan prosesor Intel Core i5, RAM 8GB, SSD 512GB. Ideal untuk kerja dan entertainment.",
            price = 8500000.0,
            imageUrl = "https://via.placeholder.com/300x300/4CAF50/FFFFFF?text=ASUS+VivoBook",
            category = "Electronics",
            rating = 4.3f,
            reviewCount = 67
        ),
        Product(
            id = "4",
            name = "Jaket Hoodie Premium",
            description = "Jaket hoodie berkualitas tinggi dengan bahan cotton fleece. Nyaman dipakai dan cocok untuk cuaca dingin.",
            price = 350000.0,
            imageUrl = "https://via.placeholder.com/300x300/9C27B0/FFFFFF?text=Hoodie",
            category = "Fashion",
            rating = 4.6f,
            reviewCount = 156
        ),
        Product(
            id = "5",
            name = "Headphone Wireless Sony WH-1000XM4",
            description = "Headphone wireless dengan teknologi noise cancelling terbaik. Kualitas audio premium untuk pengalaman mendengar musik yang luar biasa.",
            price = 3500000.0,
            imageUrl = "https://via.placeholder.com/300x300/FF9800/FFFFFF?text=Sony+WH1000XM4",
            category = "Electronics",
            rating = 4.9f,
            reviewCount = 203
        )
    )

    fun addToCart(product: Product, quantity: Int = 1) {
        val existingItem = cart.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            cart.add(CartItem(product, quantity))
        }
    }

    fun removeFromCart(productId: String) {
        cart.removeIf { it.product.id == productId }
    }

    fun updateCartItemQuantity(productId: String, quantity: Int) {
        val item = cart.find { it.product.id == productId }
        if (item != null) {
            if (quantity <= 0) {
                removeFromCart(productId)
            } else {
                item.quantity = quantity
            }
        }
    }

    fun getCartItems(): List<CartItem> = cart.toList()

    fun getCartTotal(): Double = cart.sumOf { it.getTotalPrice() }

    fun getCartItemCount(): Int = cart.sumOf { it.quantity }

    fun clearCart() {
        cart.clear()
    }

    fun createOrder(shippingAddress: String): Order {
        val order = Order(
            id = "ORD${System.currentTimeMillis()}",
            items = cart.toList(),
            totalAmount = getCartTotal(),
            orderDate = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(java.util.Date()),
            status = com.example.minimarketplace.data.OrderStatus.PENDING,
            shippingAddress = shippingAddress
        )
        orders.add(order)
        clearCart()
        return order
    }

    fun getOrders(): List<Order> = orders.toList()

    // User session management
    fun saveUserSession(context: Context, username: String) {
        val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val currentTime = System.currentTimeMillis()
        prefs.edit()
            .putString("username", username)
            .putLong("login_time", currentTime)
            .apply()
    }

    fun getUserSession(context: Context): String? {
        val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val username = prefs.getString("username", null)
        val loginTime = prefs.getLong("login_time", 0)
        val currentTime = System.currentTimeMillis()

        // Session expires after 24 hours (86400000 ms)
        val sessionTimeout = 24 * 60 * 60 * 1000L

        return if (username != null && (currentTime - loginTime) < sessionTimeout) {
            username
        } else {
            // Clear expired session
            clearUserSession(context)
            null
        }
    }

    fun clearUserSession(context: Context) {
        val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        // Also clear cart when session is cleared
        clearCart()
    }

    fun isSessionValid(context: Context): Boolean {
        return getUserSession(context) != null
    }

    // Fungsi untuk memvalidasi login dengan data dummy
    fun validateLogin(username: String, password: String): Boolean {
        return dummyUsers[username] == password
    }

    // Fungsi untuk mendapatkan daftar username yang tersedia (untuk testing)
    fun getAvailableUsers(): Map<String, String> {
        return dummyUsers.toMap()
    }

    // Fungsi untuk mengelola informasi pengiriman
    fun getShippingInfoForUser(username: String): List<ShippingInfo> {
        return dummyShippingInfo[username] ?: emptyList()
    }

    fun getDefaultShippingInfo(username: String): ShippingInfo? {
        return dummyShippingInfo[username]?.find { it.isDefault }
    }

    fun getAllShippingAddresses(username: String): List<String> {
        return dummyShippingInfo[username]?.map { "${it.fullName} - ${it.getFormattedAddress()}" } ?: emptyList()
    }
}
