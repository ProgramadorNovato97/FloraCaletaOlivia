package com.kotlinnativo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.core.view.WindowCompat

import com.kotlinnativo.screens.MainScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ********* Color de status bar ********
        window.statusBarColor = android.graphics.Color.parseColor("#f4efef")
        // Iconos oscuros
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        // ************** Pantalla inicial **************
        setContent {
            MaterialTheme {
            MainScreen() //Navbar y navegaciones
            }
        }
    }
}

