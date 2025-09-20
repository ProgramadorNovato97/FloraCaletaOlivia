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
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polyline



object MapasService {

    //****************** Funcion para calcular distancia de un punto a otro ****************
    @RequiresPermission(
        anyOf = [
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ]
    )

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

    // ************** Polyline para marcar camino y unir los markers ****************
    @Composable
    fun MiPolyline(){
         Polyline(
            points = listOf(
                LatLng(-46.42253982376904, -67.52278891074239),
                LatLng(-46.422257603168156, -67.52327479706966),
                LatLng(-46.4223919797367, -67.52360286614024),
                LatLng(-46.422434735916326, -67.52419717983501),
                LatLng(-46.42205240607076, -67.5246725728372),
                LatLng(-46.4217247851242, -67.52472679777637),
                LatLng(-46.42135758348134, -67.52520844277981),
                LatLng(-46.4211717828787, -67.52574590757747),
                LatLng(-46.417927440656086, -67.52823705796133),
            ),
            color = Color.Blue,
            width = 8f,
            pattern = listOf(
                Dot(),
                Gap(12f)
            )
        )
    }
}