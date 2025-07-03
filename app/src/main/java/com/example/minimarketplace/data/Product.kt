package com.example.minimarketplace.data

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val rating: Float = 0f,
    val reviews: Int = 0
)

data class CartItem(
    val product: Product,
    val quantity: Int
)

data class Order(
    val id: String,
    val items: List<CartItem>,
    val total: Double,
    val date: String,
    val status: String,
    val shippingAddress: String
)

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String
)
