package com.kotlinnativo.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*




private fun createNumberedMarker(context: android.content.Context, number: String): BitmapDescriptor {
    // Crear bitmap
    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Dibujar círculo
    val paint = Paint().apply {
        color = androidx.compose.ui.graphics.Color.Green.toArgb()
        isAntiAlias = true
    }
    canvas.drawCircle(50f, 50f, 40f, paint)

    // Dibujar texto (número)
    val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 30f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
        typeface = Typeface.DEFAULT_BOLD
    }

    val textBounds = Rect()
    textPaint.getTextBounds(number, 0, number.length, textBounds)
    val textY = 50f + textBounds.height() / 2f

    canvas.drawText(number, 50f, textY, textPaint)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}



@Preview
@Composable
fun MapasScreen() {
    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

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

            isLoadingLocation -> {
                // Cargando ubicación
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
                    position = CameraPosition.fromLatLngZoom(myLatLng, 15f)
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(
                        isMyLocationEnabled = true
                    ),
                    uiSettings = MapUiSettings(
                        myLocationButtonEnabled = true,
                        zoomControlsEnabled = true
                    )
                ) {
                    Marker(
                        state = MarkerState(position = LatLng(-46.45677411633962, -67.5200897410983)),
                        title = "Punto 1",
                        icon = createNumberedMarker(context, "1")
                    )

                    Marker(
                        state = MarkerState(position = LatLng(-46.454084709359535, -67.51711944780367)),
                        title = "Punto 2",
                        icon = createNumberedMarker(context, "2")
                    )

                    // Línea que conecta los dos puntos
                    Polyline(
                        points = listOf(
                            LatLng(-46.45677411633962, -67.5200897410983),
                            LatLng(-46.455609400936915, -67.52002394452384),
                            LatLng(-46.45512176734035, -67.51686444789506),
                            LatLng(-46.454084709359535, -67.51711944780367)
                        ),
                        color = androidx.compose.ui.graphics.Color.Blue,
                        width = 8f,

                        pattern = listOf(
                        Dot(), // Puntos
                        Gap(10f)   // Un espacio de 10 puntos
                    )
                    )

                }
            }

            else -> {
                // No se pudo obtener ubicación
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