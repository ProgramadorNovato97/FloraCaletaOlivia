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
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.kotlinnativo.R


@Composable
fun MapasScreen(
    onNavigateToPlanta: (String) -> Unit = { }
) {
    val context = LocalContext.current

    val caletaOliviaLatLng = LatLng(-46.419516367268194, -67.52635102128295)
    val zoomInicial = 16f

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var hasLocationPermission by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }

    LaunchedEffect(Unit) {
        hasLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderCaletaClick()

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                !hasLocationPermission -> {
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(caletaOliviaLatLng, zoomInicial)
                    }


                    val markerState = rememberMarkerState()

                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = MapProperties(
                            isMyLocationEnabled = false,
                            mapType = MapType.SATELLITE
                        ),
                        uiSettings = MapUiSettings(
                            myLocationButtonEnabled = false,
                            zoomControlsEnabled = true
                        )
                    ) {
                        Marker(
                            state = MarkerState(LatLng(-46.42253982376904, -67.52278891074239)),
                            title = "Inicio de circuito",
                            icon = MapasService.NumMarker(context, "Ini", Color(0xFF1FFF49)),
                        )
                        Marker(
                            state = MarkerState(LatLng(-46.4181411, -67.5278034)),
                            title = "Fin de circuito",
                            icon = MapasService.NumMarker(context, "Fin", Color(0xFFFF4747)),
                        )

                        ListadeMarkers.forEach { markerPropio ->
                            Marker(
                                state = MarkerState(position = markerPropio.posicion),
                                title = markerPropio.titulo,
                                icon = MapasService.NumMarker(
                                    context,
                                    markerPropio.id.toString(),
                                    Color(0xFFFF9A24)
                                ),
                                onClick = {
                                    markerState.onMarkerClick(markerPropio)
                                    true
                                }
                            )
                        }

                        MapasService.MiPolyline()
                        MapasService.MiPolyline2()
                    }

                    markerState.markerSeleccionado?.let { marker ->
                        CardMarker(
                            marker = marker,
                            onCerrar = { markerState.cerrarCard() },
                            onNavigateToPlanta = onNavigateToPlanta
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.3f)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
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
                    }
                }

                else -> {
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(caletaOliviaLatLng, zoomInicial)
                    }

                    val markerState = rememberMarkerState()

                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = MapProperties(
                            isMyLocationEnabled = hasLocationPermission,
                            mapType = MapType.SATELLITE
                        ),
                        uiSettings = MapUiSettings(
                            myLocationButtonEnabled = hasLocationPermission,
                            zoomControlsEnabled = true
                        )
                    ) {
                        Marker(
                            state = MarkerState(LatLng(-46.42253982376904, -67.52278891074239)),
                            title = "Inicio de circuito",
                            icon = MapasService.NumMarker(context, "Ini", Color(0xFF1FFF49)),
                        )
                        Marker(
                            state = MarkerState(LatLng(-46.4181411, -67.5278034)),
                            title = "Fin de circuito",
                            icon = MapasService.NumMarker(context, "Fin", Color(0xFFFF4747)),
                        )

                        ListadeMarkers.forEach { markerPropio ->
                            Marker(
                                state = MarkerState(position = markerPropio.posicion),
                                title = markerPropio.titulo,
                                icon = MapasService.NumMarker(
                                    context,
                                    markerPropio.id.toString(),
                                    Color(0xFFFF9A24)
                                ),
                                onClick = {
                                    markerState.onMarkerClick(markerPropio)
                                    true
                                }
                            )
                        }

                        MapasService.MiPolyline()
                        MapasService.MiPolyline2()
                    }

                    markerState.markerSeleccionado?.let { marker ->
                        CardMarker(
                            marker = marker,
                            onCerrar = { markerState.cerrarCard() },
                            onNavigateToPlanta = onNavigateToPlanta
                        )
                    }
                }
            }
        }
    }
}


val ListadeMarkers = listOf(
    MarkerPropio(
        id = 21,
        titulo = "Parada 21:",
        posicion = LatLng(-46.45676127715445, -67.52002212577646),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.maihuenia,
                descripcion = "MAIHUENIA",
                plantaId = "maihuenia"
            ),
            ImagenMarker(
                drawable = R.drawable.cactusaustral,
                descripcion = "CACTUS AUSTRAL",
                plantaId = "cactusaustral"
            ),
            ImagenMarker(
                drawable = R.drawable.sulupe,
                descripcion = "SULUPE",
                plantaId = "sulupe"
            ),
        )
    ),

    MarkerPropio(
        id = 22,
        titulo = "Parada 22: ",
        posicion = LatLng(-46.45726631838521, -67.52119080036859),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.unadegato,
                descripcion = "UÑA DE GATO",
                plantaId = "unadegato"
            )
        )
    ),

    MarkerPropio(
        id = 1,
        titulo = "Parada 1: ",
        posicion = LatLng(-46.4224105, -67.5239494),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.unadegato,
                descripcion = "UÑA DE GATO",
                plantaId = "unadegato"
            ),
        )
    ),

    MarkerPropio(
        id = 2,
        titulo = "Parada 2: ",
        posicion = LatLng(-46.4222783, -67.5242653),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.zampa,
                descripcion = "ZAMPA",
                plantaId = "zampa"
            ),
            ImagenMarker(
                drawable = R.drawable.quilimbay,
                descripcion = "QUILIMBAY",
                plantaId = "quilimbay"
            ),
        )
    ),

    MarkerPropio(
        id = 3,
        titulo = "Parada 3: ",
        posicion = LatLng(-46.4212241, -67.5253720),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.falsotomillo,
                descripcion = "FALSO TOMILLO",
                plantaId = "falsotomillo"
            ),
            ImagenMarker(
                drawable = R.drawable.cactusaustral,
                descripcion = "CACTUS AUSTRAL",
                plantaId = "cactusaustral"
            ),

            )
    ),

    MarkerPropio(
        id = 4,
        titulo = "Parada 4: ",
        posicion = LatLng(-46.4206415, -67.5261311),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.tuna,
                descripcion = "TUNA",
                plantaId = "tuna"
            ),
            ImagenMarker(
                drawable = R.drawable.malaspina,
                descripcion = "MALASPINA",
                plantaId = "malaspina"
            ),
            ImagenMarker(
                drawable = R.drawable.coiron,
                descripcion = "COIRÓN",
                plantaId = "coiron"
            ),
        )
    ),

    MarkerPropio(
        id = 5,
        titulo = "Parada 5: ",
        posicion = LatLng(-46.4200740, -67.5266799),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.matalaguna,
                descripcion = "MATA LAGUNA",
                plantaId = "matalaguna"
            ),
            ImagenMarker(
                drawable = R.drawable.yaoyin,
                descripcion = "YAOYIN",
                plantaId = "yaoyin"
            ),
        )
    ),

    MarkerPropio(
        id = 6,
        titulo = "Parada 6: ",
        posicion = LatLng(-46.419634, -67.526907),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.duraznillo,
                descripcion = "DURAZNILLO",
                plantaId = "duraznillo"
            ),
            ImagenMarker(
                drawable = R.drawable.verbena,
                descripcion = "VERBENA",
                plantaId = "verbena"
            ),
            ImagenMarker(
                drawable = R.drawable.botondeoro,
                descripcion = "BOTÓN DE ORO",
                plantaId = "botondeoro"
            ),
        )
    ),

    MarkerPropio(
        id = 7,
        titulo = "Parada 7: ",
        posicion = LatLng(-46.420192, -67.528466),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.sulupe,
                descripcion = "SULUPE",
                plantaId = "sulupe"
            ),
        )
    ),

    MarkerPropio(
        id = 8,
        titulo = "Parada 8: ",
        posicion = LatLng(-46.419955, -67.528864),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.algarrobillo,
                descripcion = "ALGARROBILLO",
                plantaId = "algarrobillo"
            ),
        )
    ),

    MarkerPropio(
        id = 9,
        titulo = "Parada 9: ",
        posicion = LatLng(-46.4187621, -67.5275014),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.maihuenia,
                descripcion = "MAIHUENIA",
                plantaId = "maihuenia"
            ),
        )
    ),

    MarkerPropio(
        id = 10,
        titulo = "Parada 10: ",
        posicion = LatLng(-46.4181603, -67.5278547),
        imagenes = listOf(
            ImagenMarker(
                drawable = R.drawable.tomillo,
                descripcion = "TOMILLO",
                plantaId = "tomillo"
            ),
            ImagenMarker(
                drawable = R.drawable.fabiana,
                descripcion = "FABIANA",
                plantaId = "fabiana"
            ),
            ImagenMarker(
                drawable = R.drawable.brachiclados,
                descripcion = "BRACHICLADOS",
                plantaId = "brachiclados"
            ),
        )
    ),
)



data class ImagenMarker(
    val drawable: Int,
    val descripcion: String,
    val plantaId: String? = null
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
        if (markerSeleccionado?.id == marker.id) {
            markerSeleccionado = null
        } else {
            markerSeleccionado = null
            markerSeleccionado = marker
        }
    }

    fun cerrarCard() {
        markerSeleccionado = null
    }
}

@Composable
fun rememberMarkerState(): MarkerState {
    return remember { MarkerState() }
}

@Composable
fun CarouselSimple(
    imagenes: List<ImagenMarker>,
    markerId: Int,
    onImagenClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var imagenActual by remember { mutableIntStateOf(0) }

    if (imagenActual >= imagenes.size) {
        imagenActual = 0
    }

    if (imagenes.isEmpty()) return

    Column(modifier = modifier) {
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
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        imagenes[imagenActual].plantaId?.let { plantaId ->
                            onImagenClick(plantaId)
                        }
                    },
                contentScale = ContentScale.Crop
            )

            if (imagenes.size > 1) {
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
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }

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
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

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
    onNavigateToPlanta: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 1.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = marker.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,

                    )

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onCerrar() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar"
                    )
                }
            }

            Spacer(modifier = Modifier.height(1.dp))
            CarouselSimple(
                imagenes = marker.imagenes,
                markerId = marker.id,
                onImagenClick = onNavigateToPlanta,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}