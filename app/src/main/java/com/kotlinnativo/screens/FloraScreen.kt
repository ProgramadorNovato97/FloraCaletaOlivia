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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlinnativo.R


@Composable
fun FloraScreen(onNavigate: (String) -> Unit) {

    Column(modifier = Modifier.fillMaxSize()) {
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
                Text(
                    text = "Flora Caleta Olivia",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }




        Spacer(modifier = Modifier.height(6.dp))
        // Grilla de cards
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //======================= Todos los cards de flora ===========================
            item {
                ItemCard(
                    nombre = "Uña de gato",
                    imagenRes = R.drawable.unadegato,
                    onClick = { onNavigate("unadegato") }
                )
            }

            item {
            ItemCard(
                nombre = "Cactus",
                imagenRes = R.drawable.cactusaustral,
                onClick = { onNavigate("cactus") }
            )
        }
        //===================================
    }
    }
}

// Plantilla reutilizable de card para mostar flora
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
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = imagenRes),
                contentDescription = nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            )

            Text(
                text = nombre,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 16.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp) // Sin padding
            )
        }
    }
}

