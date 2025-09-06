package com.kotlinnativo.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.kotlinnativo.services.DistanciaService

@Preview
@Composable
fun FavoritosScreen() {
    val context = LocalContext.current
    var distanciaUACO by remember { mutableStateOf("Calculando...") }

    // Actualizar cada 10 segundos
    LaunchedEffect(Unit) {
        while (true) {
            distanciaUACO = DistanciaService.calcularDistancia(context, -46.43301168157879, -67.52073934753625)
            delay(10000)
        }
    }

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = "Distancia de la UACO: $distanciaUACO",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}