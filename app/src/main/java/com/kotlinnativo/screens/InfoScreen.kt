package com.kotlinnativo.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import com.kotlinnativo.services.MapasService

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlinnativo.R
import com.kotlinnativo.services.ColorsService


@Preview
@Composable
fun InfoScreen() {

    val context = LocalContext.current // Para boton Ir
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //*** Header Circuito Flora ***
        HeaderCaletaClick()
        /*
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
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
                        text = "Circuito Flora",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } */


        //***
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.pruebaic_launcherplaystore),
                contentDescription = "Imagen del circuito",
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )

            InfoSection(
                title = "📍 Inicio del circuito",
                description = "La caminata comienza en el sendero junto al cartel 'Bienvenidos a Caleta Olivia' (Acceso Norte de la ciudad)."
            )
            InfoSection(
                title = "🌱 ¿Qué vas a ver?",
                description = "Especies autóctonas de la estepa, su relación con la fauna terrestre y el mar, además de miradores panorámicos."
            )
            InfoSection(
                title = "⏱️ Duración",
                description = "Se recorre un total de 1,2 km aproximadamente con duración entre 1h 30m y 2h."
            )
            InfoSection(
                title = "🥾 Dificultad",
                description = "Recorrido media/alta: incluye un ascenso inicial y dos finales más exigentes (opcionales)."
            )
            InfoSection(
                title = "⚠️ Recomendaciones",
                description = "Llevar agua, protección solar, sombrero y calzado cómodo."
            )
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=-46.42253982376904, -67.52278891074239"))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir al inicio del circuito")
            }
        }
        //****

    }
}

@Composable
fun InfoSection(title: String, description: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,

        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,

        )
    }
}

