package com.kotlinnativo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//*** Tabla y Clase Planta***
@Entity(tableName = "plantas")
data class Planta(
    @PrimaryKey
    val id: String,
    val nombre: String,
    val descripcion: String,
    val imagenesRes: String,
    val latitud: Double? = null,
    val longitud: Double? = null
)

@Entity(tableName = "favoritos")
data class PlantaFavorito(
    @PrimaryKey
    val plantaId: String
)