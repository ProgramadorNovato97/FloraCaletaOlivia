package com.kotlinnativo.services

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.annotation.RequiresPermission

object DistanciaService {

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
}