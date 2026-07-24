package com.kotlinnativo.screens

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.media.RingtoneManager
import android.os.Build
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.kotlinnativo.MainActivity
import com.kotlinnativo.services.ColorsService

@Preview
@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) } // Tab de inicio
    var currentPlant by remember { mutableStateOf<String?>(null) } //Pasamos planta actual para mostrar en detalle
    var plantOrigin by remember { mutableIntStateOf(0) } //Origen de donde se partio a detalle


    // Notificacion proximidad de paradas
    val context = LocalContext.current
    val paradasActivadas = remember { mutableSetOf<Int>() }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val locationCallback = remember {
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val ubicacion = result.lastLocation ?: return

                ListadeMarkers.forEach { parada ->
                    val distancia = FloatArray(1)
                    Location.distanceBetween(
                        ubicacion.latitude, ubicacion.longitude,
                        parada.posicion.latitude, parada.posicion.longitude,
                        distancia
                    )
                    // DISTANCIA
                    if (distancia[0] <= 150f && !paradasActivadas.contains(parada.id)) {
                        paradasActivadas.add(parada.id)

                        mostrarNotificacionProximidad(context, parada.titulo)

                        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(500L, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            vibrator.vibrate(500L)
                        }

                        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                        val ringtone = RingtoneManager.getRingtone(context, notification)
                        ringtone.play()
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        try {
            fusedLocationClient.requestLocationUpdates(
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000L)
                    .setMinUpdateDistanceMeters(1f)
                    .build(),
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) { }
    }

    DisposableEffect(Unit) {
        onDispose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
    //
    LaunchedEffect(MainActivity.irAMapa) {
        if (MainActivity.irAMapa) {
            selectedTab = 1
            currentPlant = null
            MainActivity.irAMapa = false
        }
    }
    //

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFf4efef)
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        currentPlant = null // Reset
                        plantOrigin = 0
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Place, contentDescription = "Circuito") },
                    label = { Text("Circuito") },
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        currentPlant = null
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
                    label = { Text("Favoritos") },
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        currentPlant = null
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Info") },
                    label = { Text("Información") },
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                        currentPlant = null
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {

                0 -> {
                    if (currentPlant == null) {
                        FloraScreen { plantaId -> currentPlant = plantaId }
                    } else {
                        when (currentPlant) {
                            //****** parada 1 ******
                            "parada01" -> PlantaDetalleScreen(plantaId = "parada01") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "parada02" -> PlantaDetalleScreen(plantaId = "parada02") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "parada03" -> PlantaDetalleScreen(plantaId = "parada03") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "parada04" -> PlantaDetalleScreen(plantaId = "parada04") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "parada05" -> PlantaDetalleScreen(plantaId = "parada05") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "parada06" -> PlantaDetalleScreen(plantaId = "parada06") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "parada07" -> PlantaDetalleScreen(plantaId = "parada07") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "parada08" -> PlantaDetalleScreen(plantaId = "parada08") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "parada09" -> PlantaDetalleScreen(plantaId = "parada09") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "parada10" -> PlantaDetalleScreen(plantaId = "parada10") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }


                            //***
                            "unadegato" -> PlantaDetalleScreen(plantaId = "unadegato") {
                                currentPlant = null
                                selectedTab = plantOrigin //Para regresar nuevamente
                            }
                            //****** parada 2 ******
                            "zampa" -> PlantaDetalleScreen(plantaId = "zampa") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "quilimbay" -> PlantaDetalleScreen(plantaId = "quilimbay") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 3 ******
                            "falsotomillo" -> PlantaDetalleScreen(plantaId = "falsotomillo") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "cactusaustral" -> PlantaDetalleScreen(plantaId = "cactusaustral") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 4 ******
                            "tuna" -> PlantaDetalleScreen(plantaId = "tuna") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "malaspina" -> PlantaDetalleScreen(plantaId = "malaspina") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "chilca" -> PlantaDetalleScreen(plantaId = "chilca") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "coiron" -> PlantaDetalleScreen(plantaId = "coiron") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 5 ******
                            "matalaguna" -> PlantaDetalleScreen(plantaId = "matalaguna") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "yaoyin" -> PlantaDetalleScreen(plantaId = "yaoyin") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 6 ******
                            "duraznillo" -> PlantaDetalleScreen(plantaId = "duraznillo") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "verbena" -> PlantaDetalleScreen(plantaId = "verbena") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "botondeoro" -> PlantaDetalleScreen(plantaId = "botondeoro") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 7 ******
                            "sulupe" -> PlantaDetalleScreen(plantaId = "sulupe") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 8 ******
                            "algarrobillo" -> PlantaDetalleScreen(plantaId = "algarrobillo") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 9 ******
                            "maihuenia" -> PlantaDetalleScreen(plantaId = "maihuenia") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** parada 10 ******
                            "tomillo" -> PlantaDetalleScreen(plantaId = "tomillo") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "fabiana" -> PlantaDetalleScreen(plantaId = "fabiana") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }

                            "brachiclados" -> PlantaDetalleScreen(plantaId = "brachiclados") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            //****** Más paradas******

                            //****** Hierbas Adicionales******
                            "patadeperdiz" -> PlantaDetalleScreen(plantaId = "patadeperdiz") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "ortiga" -> PlantaDetalleScreen(plantaId = "ortiga") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "alfilerillo" -> PlantaDetalleScreen(plantaId = "alfilerillo") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "llanten" -> PlantaDetalleScreen(plantaId = "llanten") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "estrellita" -> PlantaDetalleScreen(plantaId = "estrellita") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "marancel" -> PlantaDetalleScreen(plantaId = "marancel") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "magallanatrialata" -> PlantaDetalleScreen(plantaId = "magallanatrialata") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "mostacilla" -> PlantaDetalleScreen(plantaId = "mostacilla") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "arjonatuberosa" -> PlantaDetalleScreen(plantaId = "arjonatuberosa") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                            "colapiche" -> PlantaDetalleScreen(plantaId = "colapiche") {
                                currentPlant = null
                                selectedTab = plantOrigin
                            }
                        }
                    }
                }


                1 -> {
                    if (currentPlant == null) {
                        MapasScreen { plantaId ->
                            currentPlant = plantaId
                            plantOrigin = 1  // AGREGAR ESTA LÍNEA
                        }
                    } else {
                        PlantaDetalleScreen(plantaId = currentPlant!!) {
                            currentPlant = null
                            selectedTab = plantOrigin  // CAMBIAR ESTAS DOS LÍNEAS
                        }
                    }
                }


                2 -> {
                    if (currentPlant == null) {
                        FavoritosScreen { plantaId ->
                            currentPlant = plantaId
                            plantOrigin = 2  // AGREGAR ESTA LÍNEA
                        }
                    } else {
                        PlantaDetalleScreen(plantaId = currentPlant!!) {
                            currentPlant = null
                            selectedTab = plantOrigin
                        }
                    }
                }

                3 -> InfoScreen()
            }
        }
    }




}


// Header que se repite en las pantallas
@Composable
fun HeaderCaletaClick() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = ColorsService.Header,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Caleta en un Click",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
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
}

// Notificaciones
@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
fun mostrarNotificacionProximidad(context: Context, titulo: String) {
    val channelId = "proximidad_channel"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Proximidad de paradas",
            NotificationManager.IMPORTANCE_HIGH
        )
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
    val intent = Intent(context, MainActivity::class.java).apply {
        putExtra("ir_a_mapa", true)
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    val pendingIntent = PendingIntent.getActivity(
        context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )


    val notificacion = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(com.kotlinnativo.R.mipmap.pruebaic_launcher)
        .setContentTitle("Caleta en un Click")
        .setContentText("${titulo} cerca ~15m")
        .setSubText("Circuito Flora")
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .build()
    NotificationManagerCompat.from(context).notify(titulo.hashCode(), notificacion)
}