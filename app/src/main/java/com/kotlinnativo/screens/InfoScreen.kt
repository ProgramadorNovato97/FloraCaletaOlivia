package com.kotlinnativo.screens

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
import com.kotlinnativo.services.DistanciaService
import kotlinx.coroutines.delay


@Preview
@Composable
fun InfoScreen() {

    val context = LocalContext.current
    var distanciaCasa by remember { mutableStateOf("Calculando...") }

    // Actualizar cada 10 segundos
    LaunchedEffect(Unit) {
        while (true) {
            distanciaCasa = DistanciaService.calcularDistancia(context, -46.454086485689864, -67.51711476722836)
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
                    text = "Distancia a lugar x: $distanciaCasa",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}