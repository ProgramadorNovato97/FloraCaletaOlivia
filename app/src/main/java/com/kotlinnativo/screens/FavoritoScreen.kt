package com.kotlinnativo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import com.kotlinnativo.data.PlantaDatabase
import com.kotlinnativo.data.PlantaRepository
import com.kotlinnativo.viewmodel.PlantaViewModel

@Composable
fun PlantaDetalleScreen(
    plantaId: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val database = PlantaDatabase.getDatabase(context)
    val repository = PlantaRepository(database)
    val viewModel: PlantaViewModel = viewModel { PlantaViewModel(repository) }

    val planta by viewModel.planta.collectAsState()
    val esFavorito by viewModel.esFavorito.collectAsState()

    var imagenActual by remember { mutableIntStateOf(0) }

    // Cargar planta cuando se crea la screen
    LaunchedEffect(plantaId) {
        viewModel.cargarPlanta(plantaId)
    }

    // Mostrar loading mientras carga
    if (planta == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val plantaActual = planta!!
    // Obtener recursos drawable por nombre
    // Obtener recursos drawable por nombre (.jpg)
    val imagenesRes = plantaActual.imagenesRes.split(",").map { nombreImagen ->
        context.resources.getIdentifier(
            nombreImagen.trim(), // quitar espacios
            "drawable",
            context.packageName
        )
    }.filter { it != 0 } // Filtrar IDs inválidos

    Column(modifier = Modifier.fillMaxSize()) {
        // Tu HeaderCaletaClick (mantenlo como está)
        // HeaderCaletaClick()

        // Contenido scrollable con TUS datos de BD
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Container de imagen con botones de navegación
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .clipToBounds()
            ) {
                if (imagenesRes.isNotEmpty()) {
                    Image(
                        painter = painterResource(id = imagenesRes[imagenActual]),
                        contentDescription = plantaActual.nombre,
                        modifier = Modifier
                            .fillMaxSize()
                            .zoomable(rememberZoomState()),
                        contentScale = ContentScale.Crop
                    )
                }

                // Botones de navegación (solo si hay más de una imagen)
                if (imagenesRes.size > 1) {
                    // Botón anterior
                    IconButton(
                        onClick = {
                            imagenActual = if (imagenActual > 0)
                                imagenActual - 1
                            else
                                imagenesRes.size - 1
                        },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(8.dp)
                            .background(
                                Color.Black.copy(alpha = 0.5f),
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Anterior",
                            tint = Color.White,
                            modifier = Modifier.size(72.dp)
                        )
                    }

                    // Botón siguiente
                    IconButton(
                        onClick = {
                            imagenActual = (imagenActual + 1) % imagenesRes.size
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(8.dp)
                            .background(
                                Color.Black.copy(alpha = 0.5f),
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Siguiente",
                            tint = Color.White,
                            modifier = Modifier.size(72.dp)
                        )
                    }

                    // Indicadores en la parte inferior
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(imagenesRes.size) { index ->
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (index == imagenActual)
                                            Color.White
                                        else
                                            Color.White.copy(alpha = 0.5f)
                                    )
                                    .clickable { imagenActual = index }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.Black
                    )
                }

                Text(
                    text = plantaActual.nombre, // DESDE LA BD
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                IconButton(onClick = { viewModel.cambiarFavorito() }) { // CONECTADO A BD
                    Icon(
                        imageVector = if (esFavorito) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                        contentDescription = "Favorito",
                        tint = if (esFavorito) Color.Blue else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = plantaActual.descripcion, // DESDE LA BD
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                lineHeight = 20.sp
            )
        }
    }
}
