package com.kotlinnativo.screens
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kotlinnativo.R

@Composable
fun FloraScreen(onNavigate: (String) -> Unit) {



    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ItemCard(
                nombre = "Fabiana",
                imagenRes = R.drawable.fabiana,
                onClick = { onNavigate("fabiana") }
            )
        }
        item {
            ItemCard(
                nombre = "Cactus",
                imagenRes = R.drawable.cactusaustral,
                onClick = { onNavigate("cactus") }
            )
        }

    }
}

// Plantilla card para mostar flora
@Composable
fun ItemCard(
    nombre: String,
    imagenRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Image(
                painter = painterResource(id = imagenRes),
                contentDescription = nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

//            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = nombre,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

