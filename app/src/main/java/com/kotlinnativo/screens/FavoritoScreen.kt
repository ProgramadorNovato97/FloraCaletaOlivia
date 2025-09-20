package com.kotlinnativo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kotlinnativo.data.PlantaDatabase
import com.kotlinnativo.data.PlantaRepository
import com.kotlinnativo.viewmodel.FavoritosViewModel

@Composable
fun FavoritosScreen(
    onNavigateToDetalle: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val database = PlantaDatabase.getDatabase(context)
    val repository = PlantaRepository(database)
    val viewModel: FavoritosViewModel = viewModel { FavoritosViewModel(repository) }

    val plantasFavoritas by viewModel.plantasFavoritas.collectAsState()



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //*** Header Caleta en un click ***
        HeaderCaletaClick()
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 4.dp))
            {

            if (plantasFavoritas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Todavía no tienes favoritos",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                // Lista
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(plantasFavoritas) { planta ->

                        // Card simple
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavigateToDetalle(planta.id) }
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {

                                // Imagen
                                val imagenRes = context.resources.getIdentifier(
                                    planta.imagenesRes.split(",").first().trim(),
                                    "drawable",
                                    context.packageName
                                )

                                if (imagenRes != 0) {
                                    Image(
                                        painter = painterResource(id = imagenRes),
                                        contentDescription = planta.nombre,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(250.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Nombre
                                    Text(
                                        text = planta.nombre,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    // Distancia con ícono
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = "Ubicación",
                                            tint = Color.Red, // o el color que prefieras
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "2.3 km",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}