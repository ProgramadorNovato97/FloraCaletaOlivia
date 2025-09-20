package com.kotlinnativo.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import com.google.android.gms.maps.model.LatLng
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

            //  PLANTAS ESTÁTICAS
            val plantas = listOf(
                Planta(
                    id = "maihuenia",
                    nombre = "Maihuenia patagonica",
                    descripcion = "Estas plantas forman densos cojines de no mucha altura pero que pueden alcanzar varios metros de diámetro. Presentan espinas en grupos de a tres, todas aplanadas lateralmente. Las hojas son pequeñas y se encuentran agrupadas sobre las ramas jóvenes la mayoría de las veces. Las flores son blancas y muy frecuentadas por los insectos polinizadores. Es una planta bastante común en distintos tipos de suelos áridos. Su floración se da principalmente en el mes de diciembre.",
                    imagenesRes = "maihuenia",
                    latitud = -46.45676127715445,
                    longitud = -67.52002212577646
                ),
                Planta(
                    id = "cactusaustral",
                    nombre = "Cactus Austral",
                    descripcion = "Es una planta columnar con costillas rectas y espinas radiales y o centrales algo arqueadas. Las flores son acampanadas rosado – amarillentas, dispuestas próximas al ápice en número de tres – cinco o más, recubiertas externamente por mechones lanosos y cerdas oscuras. El estigma de la flor está formado por 16 lóbulos de color púrpura. Fácilmente observable debajo de otros arbustos. La época de floración es en el comienzo del verano. Sus flores atraen una gran diversidad e insectos polinizadores. Las semillas germinan con mucha facilidad.",
                    imagenesRes = "cactusaustral,cactusaustral2".toString(),
                    latitud = -46.45676127715445,
                    longitud = -67.52002212577646
                ),
                Planta(
                    id = "unadegato",
                    nombre = "Uña de gato",
                    descripcion = "Es una planta baja y leñosa de hojas delgadas como agujas. De allí, se denomina también pinchabola o colchón de suegra. Se extiende sobre el suelo formando almohadones de gran magnitud y en terrenos bajos. A finales de noviembre, se cubre de flores de color amarillo – dorado. Cuando fructifican, los panaderos se diseminan lentamente. Por largo tiempo, permanecen en la planta restos secos y brillantes del mismo color de las flores que, por su aspecto, semejan las conocidas siemprevivas de los arreglos florales. ",
                    imagenesRes = "unadegato,unadegato2".toString(),
                    latitud = -46.42253982376904,
                    longitud = -67.52278891074239
                ),
                Planta(
                    id = "tuna",
                    nombre = "Tuna",
                    descripcion = "Esta planta lorem ipsum  en las zonas aridas de la ",
                    imagenesRes = "tuna,tuna2,tuna3".toString(),
                    latitud = -46.45676127715445,
                    longitud = -67.52002212577646
                ),
                Planta(
                    id = "sulupe",
                    nombre = "Sulupe",
                    descripcion = "Esta planta lorem ipsum  en las zonas aridas de la ",
                    imagenesRes = "sulupe,sulupe2,sulupe3".toString(),
                    latitud = -46.417927440656086,
                    longitud = -67.52823705796133
                ),
                // ... Agregamos mas plantas ...
            )

            plantaDao.insertarPlantas(plantas)
        }
    }
}