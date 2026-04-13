package com.gastor.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.gastor.app.ui.screens.WelcomeScreen
import com.gastor.app.ui.screens.PinSetupScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {

        composable(Routes.WELCOME) {
            WelcomeScreen(navController)
        }
        composable(Routes.PIN_SETUP) {
            PinSetupScreen(navController)
        }

    }

}