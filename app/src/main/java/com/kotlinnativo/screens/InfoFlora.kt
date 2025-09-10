package com.kotlinnativo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlinnativo.R
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

//============ Uso de plantillas para realizar las pantallas ============
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

@Composable
fun UnadegatoScreen(onBack: () -> Unit) {
    PlantInfo(
        titulo = "Uña de gato",
        imagenesRes = listOf(
            R.drawable.unadegato,
            R.drawable.unadegato2,
        ),
        descripcion = "Es una planta baja y leñosa de hojas delgadas como agujas. De allí, se denomina también pinchabola o colchón de suegra. Se extiende sobre el suelo formando almohadones de gran magnitud y en terrenos bajos. A finales de noviembre, se cubre de flores de color amarillo – dorado. Cuando fructifican, los panaderos se diseminan lentamente. Por largo tiempo, permanecen en la planta restos secos y brillantes del mismo color de las flores que, por su aspecto, semejan las conocidas siemprevivas de los arreglos florales.",
        onBack = onBack
    )
}
//========================================================

// Plantilla para mostrar la informacion completa de la planta
@Composable
fun PlantInfo(
    titulo: String,
    imagenesRes: List<Int>,
    descripcion: String,
    onBack: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { imagenesRes.size })
    var esFavorito by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Barra superior fija
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            color = Color(0xFFf4efef),
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.Black
                    )
                }

                Text(
                    text = titulo,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                IconButton(onClick = { esFavorito = !esFavorito }) {
                    Icon(
                        imageVector = if (esFavorito) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                        contentDescription = "Favorito",
                        tint = if (esFavorito) Color.Blue else Color.Gray
                    )
                }
            }
        }
        // Contenido scrollable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                lineHeight = 20.sp
            )
        }
    }
}
