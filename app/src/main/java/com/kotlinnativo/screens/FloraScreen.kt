package com.kotlinnativo.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //*** Header Caleta en un click ***
        HeaderCaletaClick()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            // Grid de plantas
            if (plantas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator()
                    Text(
                        text = "Plantas disponibles",
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
                    items(plantas) { planta ->
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

    // Obtener primera imagen
    val primeraImagen = planta.imagenesRes.split(",").first().trim()
    val imagenRes = context.resources.getIdentifier(
        primeraImagen,
        "drawable",
        context.packageName
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (imagenRes != 0) {
                Image(
                    painter = painterResource(id = imagenRes),
                    contentDescription = planta.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                )
            } else {
                // Placeholder si no hay imagen
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
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 16.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}