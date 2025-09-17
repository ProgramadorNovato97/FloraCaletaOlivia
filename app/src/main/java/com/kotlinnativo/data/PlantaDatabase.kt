package com.kotlinnativo.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Planta::class, PlantaFavorito::class],
    version = 1,
    exportSchema = false
)
abstract class PlantaDatabase : RoomDatabase() {
    abstract fun plantaDao(): PlantaDao
    abstract fun favoritosDao(): FavoritosDao

    companion object {
        @Volatile
        private var INSTANCE: PlantaDatabase? = null

        fun getDatabase(context: Context): PlantaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantaDatabase::class.java,
                    "planta_database"
                ).addCallback(PlantaDatabaseCallback()).build()
                INSTANCE = instance
                instance
            }
        }

        private class PlantaDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        precargarDatos(database.plantaDao())
                    }
                }
            }
        }

        private suspend fun precargarDatos(plantaDao: PlantaDao) {


            // AQUÍ VAN LAS PLANTAS ESTÁTICAS
            val plantas = listOf(
                Planta(
                    id = "maihuenia",
                    nombre = "Maihuenia",
                    descripcion = "Planta verde de la patagonia",
                    imagenesRes = "maihuenia"
                ),
                Planta(
                    id = "cactusaustral",
                    nombre = "Cactus Austral",
                    descripcion = "Esta planta se encuentra en las zonas aridas de la ",
                    imagenesRes = "cactusaustral,cactusaustral2".toString()
                ),
                Planta(
                    id = "unadegato",
                    nombre = "Uña de gato",
                    descripcion = "Esta planta lorem ipsum  en las zonas aridas de la ",
                    imagenesRes = "unadegato,unadegato2".toString()
                ),
                Planta(
                    id = "tuna",
                    nombre = "Tuna",
                    descripcion = "Esta planta lorem ipsum  en las zonas aridas de la ",
                    imagenesRes = "tuna,tuna2,tuna3".toString()
                ),
                Planta(
                    id = "sulupe",
                    nombre = "Sulupe",
                    descripcion = "Esta planta lorem ipsum  en las zonas aridas de la ",
                    imagenesRes = "sulupe,sulupe2,sulupe3".toString()
                ),
                // ... Agregamos mas plantas ...
            )

            plantaDao.insertarPlantas(plantas)
        }
    }
}