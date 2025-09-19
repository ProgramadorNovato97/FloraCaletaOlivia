package com.kotlinnativo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kotlinnativo.data.PlantaDatabase
import com.kotlinnativo.data.PlantaRepository
import com.kotlinnativo.viewmodel.FavoritosViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment

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
            .padding(4.dp)) {
            //Verificamos si hay favoritos
            if (plantasFavoritas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Todavia no tienes favoritos",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                // Lista
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(plantasFavoritas) { planta ->

                        // Card simple
                        Card {
                            Column(modifier = Modifier.padding(16.dp)) {

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
                                            .height(200.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Nombre
                                Text(
                                    text = planta.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Botón con navegación
                                Button(
                                    onClick = { onNavigateToDetalle(planta.id) },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Ver más")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}