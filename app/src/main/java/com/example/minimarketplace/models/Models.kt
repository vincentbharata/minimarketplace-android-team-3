package com.example.minimarketplace.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int,
    val category: String,
    val rating: Float = 0f,
    val reviewCount: Int = 0
) : Parcelable

@Parcelize
data class CartItem(
    val product: Product,
    var quantity: Int
) : Parcelable

data class Order(
    val id: String,
    val userId: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val orderDate: String,
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
