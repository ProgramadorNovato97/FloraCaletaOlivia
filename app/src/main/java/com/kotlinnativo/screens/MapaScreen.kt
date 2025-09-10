package com.kotlinnativo.screens
import com.kotlinnativo.services.MapasService

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.ui.graphics.Color
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*



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
                    //******************* Colocacion de Markers **********************
                    Marker(
                        state = MarkerState(position = LatLng(-46.42259543739387, -67.52280032391299)),
                        title = "Cartel de bienvenida",
                        icon = MapasService.NumMarker(context, "Ini", Color(0xFF3EC287))
                    )
                    Marker(
                        state = MarkerState(position = LatLng(-46.418020652385955, -67.52814378788271)),
                        title = "Fina de recorrido",
                        icon = MapasService.NumMarker(context, "Fin", Color(0xFFF08080))
                    )
                    Marker(
                        state = MarkerState(position = LatLng(-46.42245345008987, -67.52397626334698)),
                        title = "Uña de gato",
                        icon = MapasService.NumMarker(context, "1", Color(0xFF3EC288))
                    )
                    Marker(
                        state = MarkerState(position = LatLng(-46.42232549558373, -67.52434386665593)),
                        title = "Zampa",
                        icon = MapasService.NumMarker(context, "2", Color(0xFF3EC288))
                    )

                    Marker(
                        state = MarkerState(position = LatLng(-46.421224693345955, -67.52543549188105)),
                        title = "Cactus Austral y Falso Tomillo ",
                        icon = MapasService.NumMarker(context, "3", Color(0xFF3EC288))
                    )




                    Marker(
                        state = MarkerState(position = LatLng(-46.45676127715445, -67.52002212577646)),
                        title = "Marker de pruebas",
                        icon = MapasService.NumMarker(context, "0", Color(0xFF3EC245))
                    )

                    //======================= Fin Markers =============================
                    // ***************** Unir markers con puntos ************
                    Polyline(
                        points = listOf(
                            LatLng(-46.42259543739387, -67.52280032391299),
                            LatLng(-46.42245345008987, -67.52397626334698),
                            LatLng(-46.42232549558373, -67.52434386665593),
                            LatLng(-46.421224693345955, -67.52543549188105),
                            LatLng(-46.418020652385955, -67.52814378788271),

                            ),
                        color = Color.Blue,
                        width = 8f,
                        pattern = listOf(
                            Dot(), // Puntos
                            Gap(8f)   // Un espacio de 8 puntos
                        )
                    )
                    //========================================================
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