package com.kotlinnativo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.kotlinnativo.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // *** Colores de app ***
        val colorHeader = Color(0xFFf4efef)

        // ********* Color de status bar ********
        window.statusBarColor = android.graphics.Color.parseColor("#f4efef")
        // Iconos oscuros
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        // ************** Pantalla inicial **************
        setContent {
            MaterialTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        MainScreen() // Navbar y navegaciones
                    }
                }
            }
        }
    }
}