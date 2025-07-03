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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minimarketplace.viewmodel.MarketplaceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: MarketplaceViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToOrderHistory: () -> Unit,
    onLogout: () -> Unit
) {
    val user by viewModel.currentUser

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
                    text = "Profile",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User Profile Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Avatar
                Card(
                    modifier = Modifier.size(80.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF6200EE)
                    ),
                    shape = RoundedCornerShape(40.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = user?.name ?: "Guest User",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Text(
                    text = user?.email ?: "guest@example.com",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileStat(
                        title = "Orders",
                        value = "${viewModel.orders.value.size}",
                        icon = Icons.Default.ShoppingBag
                    )

                    ProfileStat(
                        title = "Wishlist",
                        value = "0",
                        icon = Icons.Default.Favorite
                    )

                    ProfileStat(
                        title = "Reviews",
                        value = "0",
                        icon = Icons.Default.Star
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Menu
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                ProfileMenuItem(
                    icon = Icons.Default.Person,
                    title = "Edit Profile",
                    subtitle = "Update your personal information",
                    onClick = { /* TODO: Navigate to edit profile */ }
                )

                ProfileMenuItem(
                    icon = Icons.Default.History,
                    title = "Order History",
                    subtitle = "View your past orders",
                    onClick = onNavigateToOrderHistory
                )

                ProfileMenuItem(
                    icon = Icons.Default.LocationOn,
                    title = "Addresses",
                    subtitle = "Manage shipping addresses",
                    onClick = { /* TODO: Navigate to addresses */ }
                )

                ProfileMenuItem(
                    icon = Icons.Default.CreditCard,
                    title = "Payment Methods",
                    subtitle = "Manage your payment options",
                    onClick = { /* TODO: Navigate to payment methods */ }
                )

                ProfileMenuItem(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    subtitle = "Configure your preferences",
                    onClick = { /* TODO: Navigate to notifications */ }
                )

                ProfileMenuItem(
                    icon = Icons.Default.Help,
                    title = "Help & Support",
                    subtitle = "Get help with your account",
                    onClick = { /* TODO: Navigate to help */ }
                )

                ProfileMenuItem(
                    icon = Icons.Default.Info,
                    title = "About",
                    subtitle = "App version and information",
                    onClick = { /* TODO: Navigate to about */ }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logout Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Logout",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFE53935)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun ProfileStat(
    title: String,
    value: String,
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = title,
            tint = Color(0xFF6200EE),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = Color(0xFF6200EE),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Navigate",
                tint = Color.Gray
            )
        }
    }
}
