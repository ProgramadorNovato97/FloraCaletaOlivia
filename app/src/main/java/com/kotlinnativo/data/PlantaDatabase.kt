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

            //*** Lista de plantas para agregar a BDD ***
            val plantas = listOf(
                Planta(
                    id = "unadegato",
                    nombre = "Uña de gato",
                    descripcion = "Chuquiraga aurea. Es una planta baja y leñosa de hojas delgadas como agujas. De allí, se denomina también pinchabola o colchón de suegra. Se extiende sobre el suelo formando almohadones de gran magnitud y en terrenos bajos. Arbustos de este género (Chuquiraga), tienen la capacidad de desarrollar una raíz principal muy profunda, pudiendo alcanzar los 20 metros de ser necesario. A finales de noviembre, se cubre de flores de color amarillo – dorado. Cuando fructifican, los panaderos se diseminan lentamente. Por largo tiempo, permanecen en la planta restos secos y brillantes del mismo color de las flores que, por su aspecto, semejan las conocidas siemprevivas de los arreglos florales.",
                    imagenesRes = "unadegato,unadegato2".toString(),
                    latitud = -46.42253982376904,
                    longitud = -67.52278891074239
                ),
                Planta(
                    id = "cactusaustral",
                    nombre = "Cactus Austral",
                    descripcion = "Austrocactus bertinii. Es una planta columnar con costillas rectas y espinas radiales y o centrales algo arqueadas. Las flores son acampanadas rosado – amarillentas y de un tamaño grande, dispuestas próximas al ápice en número de tres – cinco o más, recubiertas externamente por mechones lanosos y cerdas oscuras. El estigma de la flor está formado por 16 lóbulos de color púrpura. Fácilmente observable debajo de otros arbustos. La época de floración es en el comienzo del verano. Sus flores atraen una gran diversidad e insectos polinizadores. Las semillas germinan con mucha facilidad.",
                    imagenesRes = "cactusaustral,cactusaustral2,cactusaustral3".toString(),
                    latitud = -46.45676127715445,
                    longitud = -67.52002212577646
                ),

                Planta(
                    id = "tuna",
                    nombre = "Tuna",
                    descripcion = "Maihuenopsis darwinii. Es un cactus bajo y rastrero, formado por eslabones carnosos de color verde, con numerosas espinas. En primavera, intensifican su color y se vuelven turgentes, preparándose para la floración. Presentan numerosos estambres densamente agrupados. Cuando los frutos maduran, matizan de color anaranjado intenso el almohadón de espinas. Su fruto es azucarado y lo consumen los animales. Son plantas adaptadas al clima desértico, durante la noche con temperaturas bajo cero en la mayor parte del año y durante el día, con intensa radiación solar. Esta familia es endémica de América. La distribución de esta especie es exclusiva de América del Sur.",
                    imagenesRes = "tuna,tuna2,tuna3,tuna4".toString(),
                    latitud = -46.45676127715445,
                    longitud = -67.52002212577646
                ),
                Planta(
                    id = "botondeoro",
                    nombre = "Botón de oro",
                    descripcion = "Grindelia chiloensis. Es una planta muy ramosa en la base y resinosa, característica de regiones secas, rocosas o arenosas en toda la Patagonia. Sus hojas son grandes. Las flores de color amarillo, similares a margaritas, se ubican en los extremos de las ramas. En el pimpollo se acumula una sustancia lechosa llamada resina que, al tocarla, es muy pegajosa. Una vez fructificada y diseminados sus frutos permanecen, por largo tiempo, los talluelos rígidos de color marrón brillante.  Por su belleza, estas formas son colectadas para arreglos florales y artesanías. Además, se utiliza toda la planta para infusiones que bajan la fiebre y para curar dolores como una especie de ungüento.",
                    imagenesRes = "botondeoro,botondeoro2,botondeoro3".toString(),
                    latitud = -46.417927440656086,
                    longitud = -67.52823705796133
                ),
                Planta(
                    id = "sulupe",
                    nombre = "Sulupe",
                    descripcion = "Ephedra ochreata. Es una planta de gran tamaño, muy ramificada desde la base. Sus ramas son verdes, cilíndricas que se adelgazan hacia la parte superior. Las hojas son muy pequeñas, transformadas en escamas de color pardo grisáceo, que se disponen alrededor de los nudos. Se pueden encontrar plantas femeninas y masculinas. Es más frecuente detectar las plantas masculinas a finales de octubre, porque en los nudos se desarrollan numerosos botones amarillos de los que sobresalen los estambres. En las plantas femeninas, se observan pequeños conos de color verde cuando inmaduros. En los meses de diciembre – enero, se vuelven carnosos y jugosos de color rojo. Estos frutos son apetecidos por los animales de la zona. Contienen una sustancia llamada efedrina. Su uso en medicina casera no es aconsejable. Dicha especie es una representante de la estepa patagónica, que pertenece al grupo de Gimnospermas nativas de los bosques andinos patagónicos, tales como el alerce, ciprés de la cordillera y araucaria, lo que quiere decir que sus antecesores evolutivos son muy antiguos. Por dicho motivo, no tiene flores.",
                    imagenesRes = "sulupe,sulupe2,sulupe3,sulupe4".toString(),
                    latitud = -46.417927440656086,
                    longitud = -67.52823705796133
                ),
                Planta(
                    id = "maihuenia",
                    nombre = "Maihuenia patagonica",
                    descripcion = "Estas plantas forman densos cojines de no mucha altura pero que pueden alcanzar varios metros de diámetro. Presentan espinas en grupos de a tres, todas aplanadas lateralmente. Las hojas son pequeñas y se encuentran agrupadas sobre las ramas jóvenes la mayoría de las veces. Las flores son blancas y muy frecuentadas por los insectos polinizadores. Es una planta bastante común en distintos tipos de suelos áridos. Su floración puede surgir principalmente en el mes de diciembre.",
                    imagenesRes = "maihuenia,maihuenia2,maihuenia3,maihuenia4",
                    latitud = -46.45676127715445,
                    longitud = -67.52002212577646
                ),


                // ... Agregamos mas plantas ...
            )

            plantaDao.insertarPlantas(plantas)
        }
    }
}