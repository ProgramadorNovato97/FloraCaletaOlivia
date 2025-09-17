package com.kotlinnativo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plantas")
data class Planta(
    @PrimaryKey
    val id: String,
    val nombre: String,
    val descripcion: String,
    val imagenesRes: String
)

@Entity(tableName = "favoritos")
data class PlantaFavorito(
    @PrimaryKey
    val plantaId: String
)