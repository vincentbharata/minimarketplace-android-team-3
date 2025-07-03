package com.example.minimarketplace.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.minimarketplace.data.CartItem
import com.example.minimarketplace.data.Order
import com.example.minimarketplace.data.Product
import com.example.minimarketplace.data.User

class MarketplaceViewModel : ViewModel() {

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    private val _cartItems = mutableStateOf<List<CartItem>>(emptyList())
    val cartItems: State<List<CartItem>> = _cartItems

    private val _orders = mutableStateOf<List<Order>>(emptyList())
    val orders: State<List<Order>> = _orders

    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    // Sample products
    val products = listOf(
        Product(
            id = "1",
            name = "Wireless Headphones",
            description = "High-quality wireless headphones with noise cancellation",
            price = 299.99,
            imageUrl = "android.resource://com.example.minimarketplace/drawable/product_headphones",
            category = "Electronics",
            rating = 4.5f,
            reviews = 128
        ),
        Product(
            id = "2",
            name = "Smart Watch",
            description = "Fitness tracker with heart rate monitor and GPS",
            price = 199.99,
            imageUrl = "android.resource://com.example.minimarketplace/drawable/product_smartwatch",
            category = "Electronics",
            rating = 4.2f,
            reviews = 89
        ),
        Product(
            id = "3",
            name = "Coffee Maker",
            description = "Programmable coffee maker with thermal carafe",
            price = 129.99,
            imageUrl = "android.resource://com.example.minimarketplace/drawable/product_coffee_maker",
            category = "Home",
            rating = 4.0f,
            reviews = 234
        ),
        Product(
            id = "4",
            name = "Yoga Mat",
            description = "Non-slip yoga mat for exercise and meditation",
            price = 39.99,
            imageUrl = "android.resource://com.example.minimarketplace/drawable/product_yoga_mat",
            category = "Sports",
            rating = 4.7f,
            reviews = 156
        ),
        Product(
            id = "5",
            name = "Backpack",
            description = "Durable travel backpack with multiple compartments",
            price = 89.99,
            imageUrl = "android.resource://com.example.minimarketplace/drawable/product_backpack",
            category = "Travel",
            rating = 4.3f,
            reviews = 67
        )
    )

    fun login(email: String, password: String): Boolean {
        // Simulate login - accept any email/password
        if (email.isNotEmpty() && password.isNotEmpty()) {
            _currentUser.value = User(
                id = "user1",
                name = "John Doe",
                email = email,
                phone = "+1234567890",
                address = "123 Main Street, City, State 12345"
            )
            _isLoggedIn.value = true
            return true
        }
        return false
    }

    fun register(name: String, email: String, password: String): Boolean {
        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            _currentUser.value = User(
                id = "user1",
                name = name,
                email = email,
                phone = "",
                address = ""
            )
            _isLoggedIn.value = true
            return true
        }
        return false
    }

    fun logout() {
        _currentUser.value = null
        _isLoggedIn.value = false
        _cartItems.value = emptyList()
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        val currentCart = _cartItems.value.toMutableList()
        val existingItem = currentCart.find { it.product.id == product.id }

        if (existingItem != null) {
            val index = currentCart.indexOf(existingItem)
            currentCart[index] = existingItem.copy(quantity = existingItem.quantity + quantity)
        } else {
            currentCart.add(CartItem(product, quantity))
        }

        _cartItems.value = currentCart
    }

    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filter { it.product.id != productId }
    }

    fun updateCartItemQuantity(productId: String, quantity: Int) {
        _cartItems.value = _cartItems.value.map { item ->
            if (item.product.id == productId) {
                item.copy(quantity = quantity)
            } else {
                item
            }
        }
    }

    fun getCartTotal(): Double {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }

    fun checkout(shippingAddress: String) {
        val order = Order(
            id = "order_${System.currentTimeMillis()}",
            items = _cartItems.value,
            total = getCartTotal(),
            date = "2024-01-${(1..30).random()}",
            status = "Processing",
            shippingAddress = shippingAddress
        )

        _orders.value = _orders.value + order
        _cartItems.value = emptyList()
    }

    fun getProductById(productId: String): Product? {
        return products.find { it.id == productId }
    }
}
