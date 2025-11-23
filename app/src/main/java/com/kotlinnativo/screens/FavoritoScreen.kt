package com.kotlinnativo.screens

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices
import com.kotlinnativo.data.Planta
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

    // Estados para ubicación
    var hasLocationPermission by remember { mutableStateOf(false) }
    var currentLocation by remember { mutableStateOf<Location?>(null) }

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (hasLocationPermission) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    currentLocation = location
                }
            } catch (e: SecurityException) {
                // Error silencioso
            }
        }
    }

    // Verificar permisos al iniciar
    LaunchedEffect(Unit) {
        hasLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasLocationPermission) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    currentLocation = location
                }
            } catch (e: SecurityException) {
                // Error silencioso
            }
        } else {
            // Solicitar permisos automáticamente si no los tiene
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

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
                        PlantaFavoritaCard(
                            planta = planta,
                            currentLocation = currentLocation,
                            hasLocationPermission = hasLocationPermission,
                            onRequestLocationPermission = {
                                locationPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            },
                            onClick = { onNavigateToDetalle(planta.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlantaFavoritaCard(
    planta: Planta,
    currentLocation: Location?,
    hasLocationPermission: Boolean,
    onRequestLocationPermission: () -> Unit,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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
                        tint = Color.Red,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = when {
                            !hasLocationPermission -> {
                                "Sin permisos"
                            }
                            currentLocation == null -> {
                                "Sin ubicación"
                            }
                            planta.latitud != null && planta.longitud != null -> {
                                calcularDistancia(currentLocation, planta.latitud, planta.longitud)
                            }
                            else -> {
                                "Sin coordenadas"
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = if (!hasLocationPermission) {
                            Modifier.clickable { onRequestLocationPermission() }
                        } else {
                            Modifier
                        }
                    )
                }
            }
        }
    }
}

// Función para calcular distancia
private fun calcularDistancia(
    ubicacionActual: Location,
    latitudDestino: Double,
    longitudDestino: Double
): String {
    return try {
        val ubicacionDestino = Location("").apply {
            latitude = latitudDestino
            longitude = longitudDestino
        }

        val distanciaKm = ubicacionActual.distanceTo(ubicacionDestino) / 1000
        String.format("%.2f km", distanciaKm)

    } catch (e: Exception) {
        "Error"
    }
}