package com.kotlinnativo.data

import kotlinx.coroutines.flow.Flow

class PlantaRepository(private val database: PlantaDatabase) {

    // Obtener todas las plantas
    fun obtenerPlantas(): Flow<List<Planta>> = database.plantaDao().obtenerPlantas()

    // Obtener una planta específica
    suspend fun obtenerPlanta(id: String): Planta? = database.plantaDao().obtenerPlanta(id)

    // Verificar si es favorita
    fun esFavorito(id: String): Flow<Boolean> = database.favoritosDao().esFavorito(id)

    // Cambiar estado de favorito
    suspend fun cambiarFavorito(id: String, esFavorito: Boolean) {
        database.favoritosDao().cambiarFavorito(id, esFavorito)
    }
}