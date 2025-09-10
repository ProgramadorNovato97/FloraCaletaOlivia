package com.kotlinnativo.services

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.location.Location
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object MapasService {

    //****************** Funcion para calcular distancia de un punto a otro ****************
    @RequiresPermission(
        anyOf = [
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ]
    )
    fun calcularDistancia(
        context: Context,
        latitudDestino: Double,
        longitudDestino: Double
    ): String {
        return try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val ubicacionActual = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                ?: return "Ubicación no disponible"

            val ubicacionDestino = Location("").apply {
                latitude = latitudDestino
                longitude = longitudDestino
            }

            val distanciaKm = ubicacionActual.distanceTo(ubicacionDestino) / 1000
            String.format("%.2f km", distanciaKm)

        } catch (e: Exception) {
            "Error"
        }
    }



    //****************** Funcion para numerar los markes ****************
    fun NumMarker(
        context: android.content.Context,
        number: String,
        circleColor: androidx.compose.ui.graphics.Color
    ): BitmapDescriptor {
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint().apply {
            color = circleColor.toArgb()
            isAntiAlias = true
        }
        canvas.drawCircle(40f, 40f, 30f, paint)
        // Dibujar texto (número)
        val textPaint = Paint().apply {
            color = Color.Black.toArgb()
            textSize = 30f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            typeface = Typeface.DEFAULT_BOLD
        }
        val textBounds = Rect()
        textPaint.getTextBounds(number, 0, number.length, textBounds)
        val textY = 40f + textBounds.height() / 2f
        canvas.drawText(number, 40f, textY, textPaint)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}