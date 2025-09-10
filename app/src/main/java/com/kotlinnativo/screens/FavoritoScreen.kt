package com.kotlinnativo.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlinnativo.R

@Preview
@Composable
fun FavoritosScreen() {

    ImageCarouselCard(
        title = "Parada 3",
        images = listOf(
            ImageWithDescription(R.drawable.fabiana, "Fabiana"),
            ImageWithDescription(R.drawable.maihuenia, "Maihuenia"),
            ImageWithDescription(R.drawable.falsotomillo, "Falso Tomillo")
        )
    )


}



// Data class para imagen con descripción
    data class ImageWithDescription(
        val imageRes: Int,
        val description: String = ""
        // Agregar navegacion
    )

    // Card con carousel dentro. Cada img redirige a otra pantalla


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarouselCard(
    title: String,
    images: List<ImageWithDescription>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { images.size })

    Card(
        modifier = modifier
            .width(192.dp)
            .height(178.dp)
            .padding(0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            // Título y contador
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                // Contador de imágenes (¡Ahora funciona perfecto!)
                if (images.size > 1) {
                    Text(
                        text = "${pagerState.currentPage + 1}/${images.size}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Pager de imágenes (mucho mejor que LazyRow para esto)
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) { page ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = images[page].imageRes),
                        contentDescription = images[page].description,
                        modifier = Modifier
                            .width(170.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Descripción de la imagen (si existe)
                    if (images[page].description.isNotEmpty()) {
                        Text(
                            text = images[page].description,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .width(160.dp)
                                .padding(top = 4.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}