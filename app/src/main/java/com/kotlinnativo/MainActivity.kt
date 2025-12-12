package com.kotlinnativo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.kotlinnativo.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            enableEdgeToEdge()
        }

        // *** Colores de app ***
        val colorHeader = Color(0xFFf4efef)

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

