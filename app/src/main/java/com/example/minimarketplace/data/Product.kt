package com.example.minimarketplace.data

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val rating: Float = 4.0f,
    val reviewCount: Int = 0
) {
    fun getFormattedPrice(): String {
        return "Rp ${String.format("%,.0f", price)}"
    }
}
