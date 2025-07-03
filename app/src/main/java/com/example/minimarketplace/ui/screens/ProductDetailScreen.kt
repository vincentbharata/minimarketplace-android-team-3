package com.example.minimarketplace.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.minimarketplace.viewmodel.MarketplaceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String,
    viewModel: MarketplaceViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit
) {
    val product = viewModel.getProductById(productId)
    var quantity by remember { mutableStateOf(1) }
    var showAddedToCart by remember { mutableStateOf(false) }

    // Handle null product case to prevent crashes
    if (product == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Product not found",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onNavigateBack) {
                    Text("Go Back")
                }
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Top Bar
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF6200EE)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Product Details",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.weight(1f)
                )

                // Cart Icon with Badge
                Box {
                    IconButton(onClick = onNavigateToCart) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Color(0xFF6200EE)
                        )
                    }

                    // Cart Badge
                    val cartItemCount = viewModel.cartItems.value.sumOf { it.quantity }
                    if (cartItemCount > 0) {
                        Badge(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-4).dp, y = 4.dp),
                            containerColor = Color(0xFFE53935)
                        ) {
                            Text(
                                text = if (cartItemCount > 99) "99+" else cartItemCount.toString(),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Product Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Product Image
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Product Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = product.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = product.category,
                        fontSize = 14.sp,
                        color = Color(0xFF6200EE),
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) { index ->
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Star",
                                tint = if (index < product.rating.toInt()) Color(0xFFFFD700) else Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = " ${product.rating} (${product.reviews} reviews)",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "$${product.price}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6200EE)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Description",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF333333)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = product.description,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quantity and Add to Cart
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Quantity",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF333333)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilledTonalButton(
                            onClick = { if (quantity > 1) quantity-- },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color(0xFF6200EE).copy(alpha = 0.1f),
                                contentColor = Color(0xFF6200EE)
                            ),
                            modifier = Modifier.size(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }

                        Text(
                            text = quantity.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )

                        FilledTonalButton(
                            onClick = { quantity++ },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color(0xFF6200EE).copy(alpha = 0.1f),
                                contentColor = Color(0xFF6200EE)
                            ),
                            modifier = Modifier.size(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            viewModel.addToCart(product, quantity)
                            showAddedToCart = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showAddedToCart) Color(0xFF4CAF50) else Color(0xFF6200EE)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            if (showAddedToCart) Icons.Default.Check else Icons.Default.ShoppingCart,
                            contentDescription = if (showAddedToCart) "Added to Cart" else "Add to Cart",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (showAddedToCart) "Added to Cart!" else "Add to Cart - $${String.format("%.2f", product.price * quantity)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Show snackbar when item is added to cart
    if (showAddedToCart) {
        LaunchedEffect(showAddedToCart) {
            kotlinx.coroutines.delay(2000)
            showAddedToCart = false
        }
    }
}
