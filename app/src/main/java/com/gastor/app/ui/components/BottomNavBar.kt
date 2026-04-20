package com.gastor.app.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gastor.app.ui.theme.AccentCyan
import com.gastor.app.ui.theme.CoreBackground
import com.gastor.app.ui.theme.SurfaceCard

@Composable
fun GastorBottomNavBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = CoreBackground,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { if (currentRoute != "home") navController.navigate("home") },
            icon = { Text("❖") },
            label = { Text("Inicio") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AccentCyan,
                indicatorColor = SurfaceCard
            )
        )
        NavigationBarItem(
            selected = currentRoute == "statistics",
            onClick = { if (currentRoute != "statistics") navController.navigate("statistics") },
            icon = { Text("↗") },
            label = { Text("Estadísticas") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AccentCyan,
                indicatorColor = SurfaceCard
            )
        )
        NavigationBarItem(
            selected = currentRoute == "history",
            onClick = { if (currentRoute != "history") navController.navigate("history") },
            icon = { Text("≡") },
            label = { Text("Historial") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AccentCyan,
                indicatorColor = SurfaceCard
            )
        )
    }
}
