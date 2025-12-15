package com.kotlinnativo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.kotlinnativo.data.Planta
import com.kotlinnativo.data.PlantaDatabase
import com.kotlinnativo.data.PlantaRepository
import com.kotlinnativo.viewmodel.FloraViewModel


@Composable
fun FloraScreen(
    onNavigateToPlanta: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val database = PlantaDatabase.getDatabase(context)
    val repository = PlantaRepository(database)
    val viewModel: FloraViewModel = viewModel { FloraViewModel(repository) }

    val plantas by viewModel.plantas.collectAsState()

    // Estado para el buscador
    var textoBusqueda by remember { mutableStateOf("") }

    // Filtrar plantas
    val plantasFiltradas = remember(plantas, textoBusqueda) {
        if (textoBusqueda.isEmpty()) {
            plantas
        } else {
            plantas.filter { planta ->
                planta.nombre.contains(textoBusqueda, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderCaletaClick()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo de búsqueda
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                placeholder = { Text("Buscar planta...") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            if (plantas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Cargando flora...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else if (plantasFiltradas.isEmpty()) {
                // *** NUEVO: Mensaje cuando no hay resultados ***
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se encontraron plantas",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(plantasFiltradas, key = { it.id }) { planta ->
                        PlantaCard(
                            planta = planta,
                            onClick = { onNavigateToPlanta(planta.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlantaCard(
    planta: Planta,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    // Calcular el recurso de imagen una sola vez
    val imagenRes = remember(planta.id) {
        val primeraImagen = planta.imagenesRes.split(",").first().trim()
        context.resources.getIdentifier(
            primeraImagen,
            "drawable",
            context.packageName
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (imagenRes != 0) {
                AsyncImage(  // Cambio de Image a AsyncImage
                    model = imagenRes,
                    contentDescription = planta.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🌱",
                        fontSize = 32.sp
                    )
                }
            }

            Text(
                text = planta.nombre,
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}