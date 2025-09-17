package com.kotlinnativo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantaDao {
    @Query("SELECT * FROM plantas")
    fun obtenerPlantas(): Flow<List<Planta>>

    @Query("SELECT * FROM plantas WHERE id = :id")
    suspend fun obtenerPlanta(id: String): Planta?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPlantas(plantas: List<Planta>)
}

@Dao
interface FavoritosDao {
    @Query("SELECT EXISTS(SELECT 1 FROM favoritos WHERE plantaId = :id)")
    fun esFavorito(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun marcarFavorito(favorito: PlantaFavorito)

    @Query("DELETE FROM favoritos WHERE plantaId = :id")
    suspend fun quitarFavorito(id: String)

    suspend fun cambiarFavorito(id: String, esFavorito: Boolean) {
        if (esFavorito) {
            marcarFavorito(PlantaFavorito(id))
        } else {
            quitarFavorito(id)
        }
    }
}