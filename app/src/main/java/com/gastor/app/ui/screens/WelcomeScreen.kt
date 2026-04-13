package com.gastor.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gastor.app.ui.theme.CoreBackground
import com.gastor.app.ui.theme.NeonGreen
import com.gastor.app.ui.theme.SurfaceCard
import com.gastor.app.ui.theme.SurfaceCardBorder

@Composable
fun WelcomeScreen(navController: NavController) {

    // Gradiente cinematográfico de fondo
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            CoreBackground,
            Color(0xFF001524), // Tinte sutil cian profundo
            CoreBackground
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 40.dp)
            ) {

                Text(
                    text = "GASTOR",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    letterSpacing = 8.sp // Espaciado ultra moderno
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Gobernanza Financiera Inteligente",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.height(60.dp))

                FeatureItem(
                    title = "Control Total",
                    description = "Monitoreo en tiempo real con precisión milimétrica"
                )

                FeatureItem(
                    title = "Interfaz Glassmorphic",
                    description = "Experiencia visual de nueva generación"
                )

                FeatureItem(
                    title = "Cifrado Biométrico",
                    description = "Seguridad impenetrable para tus datos"
                )
            }

            Button(
                onClick = { navController.navigate("create_pin") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NeonGreen,
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "INICIALIZAR SISTEMA",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }
    }
}

@Composable
fun FeatureItem(title: String, description: String) {

    // Tarjeta estilo Glassmorphism
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceCard.copy(alpha = 0.6f))
            .border(1.dp, SurfaceCardBorder, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}