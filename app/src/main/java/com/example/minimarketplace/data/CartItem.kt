package com.example.minimarketplace.data

data class CartItem(
    val product: Product,
    var quantity: Int = 1
) {
    fun getTotalPrice(): Double {
        return product.price * quantity
    }

    fun getFormattedTotalPrice(): String {
        return "Rp ${String.format("%,.0f", getTotalPrice())}"
    }
}
