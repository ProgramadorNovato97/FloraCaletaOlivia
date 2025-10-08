package com.kotlinnativo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kotlinnativo.data.PlantaDatabase
import com.kotlinnativo.data.PlantaRepository
import com.kotlinnativo.services.ColorsService
import com.kotlinnativo.viewmodel.PlantaViewModel
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

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
    // Obtener recursos drawable por nombre (.jpg)
    val imagenesRes = plantaActual.imagenesRes.split(",").map { nombreImagen ->
        context.resources.getIdentifier(
            nombreImagen.trim(), // quitar espacios
            "drawable",
            context.packageName
        )
    }.filter { it != 0 } // Filtrar IDs inválidos

    Column(modifier = Modifier.fillMaxSize()) {
       // *** Boton volver ***
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            color = ColorsService.Header,
            shadowElevation = 4.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Atrás",
                        tint = Color.Black
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Caleta en un Click",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
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

        // Contenido scrollable
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
                    .height(320.dp)
                    .clipToBounds()
            ) {
                if (imagenesRes.isNotEmpty()) {
                    Image(
                        painter = painterResource(id = imagenesRes[imagenActual]),
                        contentDescription = plantaActual.nombre,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp))
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

                    // Indicadores de imagen
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

                Text(
                    text = plantaActual.nombre, // DESDE LA BD
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Justify
                )

                IconButton(onClick = { viewModel.cambiarFavorito() }) { // CONECTADO A BD
                    Icon(
                        imageVector = if (esFavorito) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                        contentDescription = "Favorito",
                        tint = if (esFavorito) Color.Red else Color.Gray
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
