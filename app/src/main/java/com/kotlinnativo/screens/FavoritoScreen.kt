package com.kotlinnativo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.kotlinnativo.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable


@Preview
@Composable
fun FavoritosScreen() {
    PlantInfo(
        titulo = "Ortiga",
        imagenesRes = listOf(
            R.drawable.ortiga,
            R.drawable.fabiana,
            R.drawable.duraznillo
        ),
        descripcion = "Planta que se destaca por sus llamativas flores de color amarillo intenso. A pesar de su nombre, no es una verdadera ortiga y no pica. Sus hojas son ovaladas y a menudo tienen un patrón plateado que las hace muy decorativas."
    )
}



// PLantilla para mostrar info de la planta
@Composable
fun PlantInfo(
    titulo: String,
    imagenesRes: List<Int>, // Lista de IDs de drawable
    descripcion: String
) {
    val pagerState = rememberPagerState(pageCount = { imagenesRes.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pager deslizable
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) { page ->
            Image(
                painter = painterResource(id = imagenesRes[page]),
                contentDescription = titulo,
                modifier = Modifier
                    .fillMaxSize()
                    .zoomable(rememberZoomState()), // ← Esto agrega zoom
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Descripción
        Text(
            text = descripcion,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            lineHeight = 20.sp
        )
    }
}