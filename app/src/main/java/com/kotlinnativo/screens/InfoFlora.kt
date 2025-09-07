package com.kotlinnativo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlinnativo.R
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun FabianaScreen(onBack: () -> Unit) {
    PlantInfo(
        titulo = "Fabiana",
        imagenesRes = listOf(
            R.drawable.fabiana,
            R.drawable.ortiga,
        ),
        descripcion = "Información detallada sobre la fabiana...",
        onBack = onBack
    )
}
@Composable
fun CactusScreen(onBack: () -> Unit) {
    PlantInfo(
        titulo = "Cactus Austral",
        imagenesRes = listOf(
            R.drawable.cactusaustral,
            R.drawable.cactusaustral2,
        ),
        descripcion = "Información detallada sobre cactus austral...",
        onBack = onBack
    )
}


// 4. PlantInfo modificado (con botón volver)
@Composable
fun PlantInfo(
    titulo: String,
    imagenesRes: List<Int>,
    descripcion: String,
    onBack: () -> Unit
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

        // Pager deslizable con zoom
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .clipToBounds()
        ) { page ->
            Image(
                painter = painterResource(id = imagenesRes[page]),
                contentDescription = titulo,
                modifier = Modifier
                    .fillMaxSize()
                    .zoomable(rememberZoomState()),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón volver
        Button(onClick = onBack) {
            Text("Volver")
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