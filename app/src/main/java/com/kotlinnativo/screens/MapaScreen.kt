package com.kotlinnativo.screens
import com.kotlinnativo.services.MapasService

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.ui.graphics.Color
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.kotlinnativo.R


@Preview
@Composable
fun MapasScreen() {






    val context = LocalContext.current // Contexto para icons de markers

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }// Ultima localización proporcionada por el usuario

    var hasLocationPermission by remember { mutableStateOf(false) }
    var currentLocation by remember { mutableStateOf<Location?>(null) }
    var isLoadingLocation by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (hasLocationPermission) {
            // Obtener ubicación inmediatamente después de dar permisos
            isLoadingLocation = true
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    currentLocation = location
                    isLoadingLocation = false
                }
            } catch (e: SecurityException) {
                isLoadingLocation = false
            }
        }
    }
    LaunchedEffect(Unit) {
        hasLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (hasLocationPermission) {
            isLoadingLocation = true
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    currentLocation = location
                    isLoadingLocation = false
                }
            } catch (e: SecurityException) {
                isLoadingLocation = false
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            !hasLocationPermission -> {
                // Sin permisos - mostrar botón centrado
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Se necesita acceso a la ubicación",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            locationPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }
                    ) {
                        Text("Solicitar Permisos")
                    }
                }
            }

            // Mensaje mientras se Obteniene la ubicación
            isLoadingLocation -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Obteniendo ubicación...")
                }
            }

            currentLocation != null -> {
                // Mostrar mapa con mi ubicación
                val myLatLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(myLatLng, 13f)
                }
                val markerState = rememberMarkerState() //***** Para Markers ****
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(
                        isMyLocationEnabled = true,
                        //mapType = MapType.SATELLITE //Vista satelite de mapa
                    ),
                    uiSettings = MapUiSettings(
                        myLocationButtonEnabled = true,
                        zoomControlsEnabled = true
                    )
                ) {
                    // Mostramos Marker Inicio y Fin
                    Marker(
                        state = MarkerState(LatLng(-46.42253982376904, -67.52278891074239)),
                        title = "Entrada a Caleta",
                        icon = MapasService.NumMarker(context, "ini", Color(0xFF6BD16B)),
                    )
                    Marker(
                        state = MarkerState(LatLng(-46.417927440656086, -67.52823705796133)),
                        title = "Final",
                        icon = MapasService.NumMarker(context, "fin", Color(0xFFF56D53)),
                    )


                    // Mostramos todos nuetra lista de marker propios
                    ListadeMarkers.forEach { markerPropio ->
                    Marker(
                        state = MarkerState(position = markerPropio.posicion),
                        title = markerPropio.titulo,
                        icon = MapasService.NumMarker(context, markerPropio.id.toString(), Color(0xFF3EC245)),
                        onClick = {
                            markerState.onMarkerClick(markerPropio)
                            true // Evento
                        }
                    )
                }

                    // ***************** Aca va Polylines ************

                }
                // Mostramos los card
                markerState.markerSeleccionado?.let { marker ->
                    CardMarker(
                        marker = marker,
                        onCerrar = { markerState.cerrarCard() }
                    )
                }

            }

            else -> {
                // Caso de que falle y No se pudo obtener ubicación
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No se pudo obtener la ubicación")
                    Button(
                        onClick = {
                            isLoadingLocation = true
                            try {
                                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                                    currentLocation = location
                                    isLoadingLocation = false
                                }
                            } catch (e: SecurityException) {
                                isLoadingLocation = false
                            }
                        }
                    ) {
                        Text("Reintentar")
                    }
                }
            }
        }
    }
}




//***********************************************


//Lista de mis marcadores propios

val ListadeMarkers = listOf(
    MarkerPropio(
        id = 1,
        titulo = "Parada 1",
        posicion = LatLng(-46.45676127715445, -67.52002212577646),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.maihuenia,
                descripcion = "Maihuenia"
            ),
            ImagenMarker(
                drawable = R.drawable.cactusaustral,
                descripcion = "Cactus"
            ),
            ImagenMarker(
                drawable = R.drawable.fabiana,
                descripcion = "Fabiana"
            ),
        )
    ),

    MarkerPropio(
    id = 2,
    titulo = "PARADA 2",
    posicion = LatLng(-46.45726631838521, -67.52119080036859),
    imagenes = listOf(
        ImagenMarker(
            drawable = R.drawable.unadegato,
            descripcion = "UÑA DE GATO"
        )
    )
)

)



// ***** Clases necesarias ****
data class ImagenMarker(
    val drawable: Int, // R.drawable.imagen1
    val descripcion: String
)

data class MarkerPropio(
    val id: Int,
    val titulo: String,
    val posicion: LatLng,
    val imagenes: List<ImagenMarker>
)

class MarkerState {
    var markerSeleccionado by mutableStateOf<MarkerPropio?>(null)
        private set

    fun onMarkerClick(marker: MarkerPropio) {
        markerSeleccionado = marker
    }

    fun cerrarCard() {
        markerSeleccionado = null
    }
}

// Composable para usar el estado
@Composable
fun rememberMarkerState(): MarkerState {
    return remember { MarkerState() }
}

@Composable
fun CarouselSimple(
    imagenes: List<ImagenMarker>,
    modifier: Modifier = Modifier
) {
    var imagenActual by remember { mutableIntStateOf(0) }

    if (imagenes.isEmpty()) return

    Column(modifier = modifier) {
        // Imagen con descripción superpuesta
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = imagenes[imagenActual].drawable),
                contentDescription = imagenes[imagenActual].descripcion,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            // Botones de navegación (solo si hay más de una imagen)
            if (imagenes.size > 1) {
                // Botón anterior
                IconButton(
                    onClick = {
                        imagenActual = if (imagenActual > 0)
                            imagenActual - 1
                        else
                            imagenes.size - 1
                    },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .background(
                            Color.Black.copy(alpha = 0.3f),
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Anterior",
                        tint = Color.White
                    )
                }

                // Botón siguiente
                IconButton(
                    onClick = {
                        imagenActual = (imagenActual + 1) % imagenes.size
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .background(
                            Color.Black.copy(alpha = 0.3f),
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Siguiente",
                        tint = Color.White
                    )
                }
            }

            // Descripción superpuesta
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = imagenes[imagenActual].descripcion,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Indicadores
        if (imagenes.size > 1) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(imagenes.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == imagenActual)
                                    MaterialTheme.colorScheme.primary
                                else
                                    Color.Gray.copy(alpha = 0.5f)
                            )
                    )
                    if (index < imagenes.size - 1) {
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CardMarker(
    marker: MarkerPropio,
    onCerrar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Header con título y botón cerrar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = marker.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onCerrar) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar"
                    )
                }
            }

            // Carousel (sin Spacer)
            CarouselSimple(
                imagenes = marker.imagenes,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}