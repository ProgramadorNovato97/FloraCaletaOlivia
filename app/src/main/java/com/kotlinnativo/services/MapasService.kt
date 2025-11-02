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
    fun MiPolyline() {
        Polyline(
            points = listOf(
                LatLng(-46.42253982376904, -67.52278891074239),
                LatLng(-46.4222541743375, -67.52312535891035),
                LatLng(-46.4222717646025, -67.52331195647793),
                LatLng(-46.42236631217954, -67.52348898493943),
                LatLng(-46.42242622887339, -67.52395946599096),
                LatLng(-46.422435023978835, -67.52419311166317),
                LatLng(-46.422386101185516, -67.52430714351325),
                LatLng(-46.42210575624624, -67.52461255748283),
                LatLng(-46.42205078647816, -67.52468033865338),
                LatLng(-46.42171437030901, -67.52472658933301),
                LatLng(-46.421595634692146, -67.52482945722481),
                LatLng(-46.42141093433359, -67.5250894179409),
                LatLng(-46.421225683642064, -67.52549769979996),
                LatLng(-46.42116794233725, -67.52576751362905),
                LatLng(-46.42034245073856, -67.52628871230401),
                LatLng(-46.42019822745446, -67.52637454299368),
                LatLng(-46.420027192938484, -67.52660856573),
                LatLng(-46.41972881159088, -67.52691626427819),
                LatLng(-46.41955924346594, -67.52697092540248),
                LatLng(-46.41912186291701, -67.52728522686871),
                LatLng(-46.41873609474359, -67.52759259963267),
                LatLng(-46.41853395312387, -67.52765523676487),
                LatLng(-46.41821876691938, -67.52785280942054),
                LatLng(-46.4181603, -67.5278547),
                LatLng(-46.4181411, -67.5278034),
            ),
            color = Color.Blue,
            width = 8f,
            pattern = listOf(
                Dot(),
                Gap(12f)
            )
        )
    }

    @Composable
    fun MiPolyline2() {
        Polyline(
            points = listOf(
                LatLng(-46.419728246350935, -67.526989828724),
                LatLng(-46.419799450045765, -67.52732574147868),
                LatLng(-46.419889630967056, -67.52785803004203),
                LatLng(-46.42012907613468, -67.52834520935083),
                LatLng(-46.420192, -67.528466),
                LatLng(-46.4201244530658, -67.52847998847555),
                LatLng(-46.42001905874588, -67.52851217498174),
                LatLng(-46.41995156929026, -67.52859398238286),
                LatLng(-46.41987826351405, -67.52886802360752),
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