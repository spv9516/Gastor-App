package com.gastor.app

import android.os.Bundle
import com.gastor.app.navigation.NavGraph
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gastor.app.ui.screens.WelcomeScreen
import com.gastor.app.ui.theme.GastorTheme
import com.gastor.app.navigation.AppNavigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GastorTheme {
                AppNavigation()
            }
        }
    }
}