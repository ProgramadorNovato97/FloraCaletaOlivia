package com.kotlinnativo.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(1) } // Empezar en Mapa
    var currentPlant by remember { mutableStateOf<String?>(null) }

    Scaffold(
        bottomBar = {
            NavigationBar (
                containerColor = Color(0xFFf4efef)
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Flora") },
                    label = { Text("Flora") },
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        currentPlant = null // Reset al volver a flora
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Place, contentDescription = "Circuito") },
                    label = { Text("Circuito") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
                    label = { Text("Favoritos") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Info") },
                    label = { Text("Info") },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> {
                    // Info de las plantas
                    if (currentPlant == null) {
                        FloraScreen(onNavigate = { plantName -> currentPlant = plantName })
                    } else {
                        when(currentPlant) {
                            "unadegato" -> UnadegatoScreen(onBack = { currentPlant = null })
                            "cactus" -> CactusScreen(onBack = { currentPlant = null })

                        }
                    }
                }
                1 -> MapasScreen()
                2 -> FavoritosScreen()
                3 -> InfoScreen()
            }
        }
    }
}


