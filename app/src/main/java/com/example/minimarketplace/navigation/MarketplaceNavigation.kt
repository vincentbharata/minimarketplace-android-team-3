package com.example.minimarketplace.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.minimarketplace.ui.screens.*
import com.example.minimarketplace.viewmodel.MarketplaceViewModel

@Composable
fun MarketplaceNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: MarketplaceViewModel = viewModel()
) {
    val isLoggedIn by viewModel.isLoggedIn

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // Login Screen
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = viewModel,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Register Screen
        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        // Home Screen
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToProducts = {
                    navController.navigate(Screen.ProductList.route)
                },
                onNavigateToCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onLogout = {
                    viewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        // Product List Screen
        composable(Screen.ProductList.route) {
            ProductListScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDetail = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            )
        }

        // Product Detail Screen
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToCart = {
                    navController.navigate(Screen.Cart.route)
                }
            )
        }

        // Cart Screen
        composable(Screen.Cart.route) {
            CartScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToCheckout = {
                    navController.navigate(Screen.Checkout.route)
                }
            )
        }

        // Checkout Screen
        composable(Screen.Checkout.route) {
            CheckoutScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onOrderSuccess = {
                    navController.navigate(Screen.OrderHistory.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        // Profile Screen
        composable(Screen.Profile.route) {
            ProfileScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToOrderHistory = {
                    navController.navigate(Screen.OrderHistory.route)
                },
                onLogout = {
                    viewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        // Order History Screen
        composable(Screen.OrderHistory.route) {
            OrderHistoryScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
