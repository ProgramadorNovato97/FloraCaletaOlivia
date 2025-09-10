package com.kotlinnativo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kotlinnativo.R

@Composable
fun FavoritosScreen() {

    CarouselCard(
        title = "Galería de productos",
        images = listOf(R.drawable.cactusaustral, R.drawable.fabiana, R.drawable.maihuenia),
        onImageClick = { imageId ->
            //navController.navigate("detail/$imageId")
        },
        onClose = {
            // lógica para ocultar el card
        }
    )


}



// Card con carousel dentro. Cada img redirige a otra pantalla
@Composable
fun CarouselCard(
    modifier: Modifier = Modifier,
    title: String = "",
    images: List<Int>, // Lista de IDs de imágenes (puede ser URLs si usás Coil)
    onImageClick: (Int) -> Unit,
    onClose: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            if (title.isNotEmpty()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(images) { imageRes ->
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(140.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onImageClick(imageRes) },
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onClose,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Cerrar")
            }
        }
    }
}
