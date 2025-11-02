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
    var selectedTab by remember { mutableIntStateOf(0) } // Tab de inicio
    var currentPlant by remember { mutableStateOf<String?>(null) } //Pasamos planta actual para mostrar en detalle
    var plantOrigin by remember { mutableIntStateOf(0) } //Origen de donde se partio a detalle


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
                        plantOrigin = 0
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
                    label = { Text("Información") },
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
                    if (currentPlant == null) {
                        FloraScreen { plantaId -> currentPlant = plantaId }
                    } else {
                        when (currentPlant) {
                            //****** parada 1 ******
                            "unadegato" -> PlantaDetalleScreen(plantaId = "unadegato") {
                                currentPlant = null
                                selectedTab = plantOrigin //Para regresar nuevamente
                            }
                            //****** parada 2 ******
                            "zampa" -> PlantaDetalleScreen(plantaId = "zampa") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "quilimbay" -> PlantaDetalleScreen(plantaId = "quilimbay") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 3 ******
                            "falsotomillo" -> PlantaDetalleScreen(plantaId = "falsotomillo") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "cactusaustral" -> PlantaDetalleScreen(plantaId = "cactusaustral") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 4 ******
                            "tuna" -> PlantaDetalleScreen(plantaId = "tuna") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "malaspina" -> PlantaDetalleScreen(plantaId = "malaspina") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "chilca" -> PlantaDetalleScreen(plantaId = "chilca") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "coiron" -> PlantaDetalleScreen(plantaId = "coiron") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 5 ******
                            "matalaguna" -> PlantaDetalleScreen(plantaId = "matalaguna") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "yaoyin" -> PlantaDetalleScreen(plantaId = "yaoyin") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 6 ******
                            "duraznillo" -> PlantaDetalleScreen(plantaId = "duraznillo") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "verbena" -> PlantaDetalleScreen(plantaId = "verbena") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "botondeoro" -> PlantaDetalleScreen(plantaId = "botondeoro") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 7 ******
                            "sulupe" -> PlantaDetalleScreen(plantaId = "sulupe") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 8 ******
                            "algarrobillo" -> PlantaDetalleScreen(plantaId = "algarrobillo") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 9 ******
                            "maihuenia" -> PlantaDetalleScreen(plantaId = "maihuenia") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 10 ******
                            "tomillo" -> PlantaDetalleScreen(plantaId = "tomillo") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "fabiana" -> PlantaDetalleScreen(plantaId = "fabiana") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "brachiclados" -> PlantaDetalleScreen(plantaId = "brachiclados") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** Más paradas******

                            //****** Hierbas Adicionales******
                            "patadeperdiz" -> PlantaDetalleScreen(plantaId = "patadeperdiz") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "ortiga" -> PlantaDetalleScreen(plantaId = "ortiga") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "alfilerillo" -> PlantaDetalleScreen(plantaId = "alfilerillo") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "llanten" -> PlantaDetalleScreen(plantaId = "llanten") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "estrellita" -> PlantaDetalleScreen(plantaId = "estrellita") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "marancel" -> PlantaDetalleScreen(plantaId = "marancel") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "magallanatrialata" -> PlantaDetalleScreen(plantaId = "magallanatrialata") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "mostacilla" -> PlantaDetalleScreen(plantaId = "mostacilla") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "arjonatuberosa" -> PlantaDetalleScreen(plantaId = "arjonatuberosa") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                        }
                    }
                }


                1 -> {
                    if (currentPlant == null) {
                        MapasScreen { plantaId ->
                            currentPlant = plantaId
                            plantOrigin = 1  // AGREGAR ESTA LÍNEA
                        }
                    } else {
                        PlantaDetalleScreen(plantaId = currentPlant!!) {
                            currentPlant = null
                            selectedTab = plantOrigin  // CAMBIAR ESTAS DOS LÍNEAS
                        }
                    }
                }


                2 -> {
                    if (currentPlant == null) {
                        FavoritosScreen { plantaId ->
                            currentPlant = plantaId
                            plantOrigin = 2  // AGREGAR ESTA LÍNEA
                        }
                    } else {
                        PlantaDetalleScreen(plantaId = currentPlant!!) {
                            currentPlant = null
                            selectedTab = plantOrigin
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

