package com.example.minimarketplace.data

data class ShippingInfo(
    val fullName: String,
    val phoneNumber: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val isDefault: Boolean = false
) {
    fun getFormattedAddress(): String {
        return "$address, $city $postalCode"
    }
}
