package com.kotlinnativo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlinnativo.services.ColorsService

@Preview
@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(1) } // Empezar en Mapa
    var currentPlant by remember { mutableStateOf<String?>(null) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFf4efef)
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        currentPlant = null // Reset
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Place, contentDescription = "Circuito") },
                    label = { Text("Circuito") },
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        currentPlant = null
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
                    label = { Text("Favoritos") },
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        currentPlant = null
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Info") },
                    label = { Text("Info") },
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                        currentPlant = null
                    }
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
                        when (currentPlant) {
                            "tuna" -> PlantaDetalleScreen(plantaId = "tuna") { }
                            "sulupe" -> PlantaDetalleScreen(plantaId = "sulupe") { }
                            "cactus" -> PlantaDetalleScreen(plantaId = "cactusaustral") { }
                            "maihuenia" -> PlantaDetalleScreen(plantaId = "maihuenia") { }
                            "unadegato" -> PlantaDetalleScreen(plantaId = "unadegato") { }

                        }
                    }
                }

                //1 -> MapasScreen()


                 1 -> {
                    if (currentPlant == null) {
                    MapasScreen { plantaId -> currentPlant = plantaId }
                        } else {
                        PlantaDetalleScreen(plantaId = currentPlant!!) {
                        currentPlant = null  // Volver a mapas
                            }
                        }
                    }




                2 -> {
                    if (currentPlant == null) {
                        FavoritosScreen { plantaId -> currentPlant = plantaId }
                    } else {
                        PlantaDetalleScreen(plantaId = currentPlant!!) {
                            currentPlant = null  // Volver a favoritos
                        }
                    }
                }

                3 -> InfoScreen()
            }
        }
    }
}


// Header que se repite en las pantallas
@Composable
fun HeaderCaletaClick() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = ColorsService.Header,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Caleta en un Click",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Circuito Flora",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

