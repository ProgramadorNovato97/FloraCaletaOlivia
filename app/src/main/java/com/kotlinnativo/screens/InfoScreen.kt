package com.kotlinnativo.screens
import com.kotlinnativo.services.MapasService

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.delay

@Preview
@Composable
fun InfoScreen() {

//******************************
    val context = LocalContext.current
    var distancia by remember { mutableStateOf("Calculando...") }
    // Actualizar cada 10 segundos
    LaunchedEffect(Unit) {
        while (true) {
            distancia = MapasService.calcularDistancia(context, -46.42245345008987, -67.52397626334698)
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
                    text = "Distancia a Uña de gato es: $distancia",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
//******************************


