package com.gastor.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.gastor.app.ui.screens.*

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {

        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("create_pin") {
            CreatePinScreen(navController)
        }
        composable("biometric") {
            BiometricScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("add_expense") {
            AddExpenseScreen(navController)
        }
        composable("history") {
            HistoryScreen()
        }
        composable("statistics") {
            StatisticsScreen()
        }
        composable("add_income") {
            AddIncomeScreen(navController)
        }
        composable("settings") {
            SettingsScreen()
        }
    }
}