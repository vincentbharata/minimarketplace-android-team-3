package com.example.minimarketplace.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minimarketplace.viewmodel.MarketplaceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MarketplaceViewModel,
    onNavigateToProducts: () -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onLogout: () -> Unit
) {
    val user by viewModel.currentUser
    val cartItems by viewModel.cartItems

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
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
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Welcome back! 👋",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = user?.name ?: "Guest",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6200EE)
                    )
                }

                IconButton(
                    onClick = onLogout,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0xFFFFEBEE)
                    )
                ) {
                    Icon(
                        Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint = Color(0xFFE53935)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Store Info Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF6200EE)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF6200EE),
                                Color(0xFF3700B3)
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "🛍️ MiniMarketplace",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Discover amazing products at great prices",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Menu Items
        Text(
            text = "Explore",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Products Card
        MenuCard(
            icon = Icons.Default.ShoppingBag,
            title = "Products",
            subtitle = "Browse our collection",
            badgeCount = viewModel.products.size,
            backgroundColor = Color(0xFFE3F2FD),
            iconColor = Color(0xFF1976D2),
            onClick = onNavigateToProducts
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Cart Card
        MenuCard(
            icon = Icons.Default.ShoppingCart,
            title = "Shopping Cart",
            subtitle = "Review your items",
            badgeCount = cartItems.size,
            backgroundColor = Color(0xFFE8F5E8),
            iconColor = Color(0xFF4CAF50),
            onClick = onNavigateToCart
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Profile Card
        MenuCard(
            icon = Icons.Default.Person,
            title = "Profile",
            subtitle = "Manage your account",
            badgeCount = null,
            backgroundColor = Color(0xFFFFF3E0),
            iconColor = Color(0xFFFF9800),
            onClick = onNavigateToProfile
        )
    }
}

@Composable
fun MenuCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    badgeCount: Int?,
    backgroundColor: Color,
    iconColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(50.dp),
                colors = CardDefaults.cardColors(
                    containerColor = backgroundColor
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        contentDescription = title,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333)
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            if (badgeCount != null && badgeCount > 0) {
                Badge(
                    containerColor = Color(0xFF6200EE)
                ) {
                    Text(
                        text = badgeCount.toString(),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Navigate",
                tint = Color.Gray
            )
        }
    }
}
