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
                // ****** Parada 1 (-46.4224105, -67.5239494)******
                Planta(
                    id = "unadegato",
                    nombre = "Uña de gato",
                    descripcion = "Chuquiraga aurea. Es una planta baja y leñosa de hojas delgadas como agujas. De allí, se denomina también pinchabola o colchón de suegra. Se extiende sobre el suelo formando almohadones de gran magnitud y en terrenos bajos. Arbustos de este género (Chuquiraga), tienen la capacidad de desarrollar una raíz principal muy profunda, pudiendo alcanzar los 20 metros de ser necesario. A finales de noviembre, se cubre de flores de color amarillo – dorado. Cuando fructifican, los panaderos se diseminan lentamente. Por largo tiempo, permanecen en la planta restos secos y brillantes del mismo color de las flores que, por su aspecto, semejan las conocidas siemprevivas de los arreglos florales.",
                    imagenesRes = "unadegato,unadegato2".toString(),
                    latitud = -46.4224105,
                    longitud = -67.5239494
                ),
                // ****** Parada 2 (-46.4222783, -67.5242653) ******
                Planta(
                    id = "zampa",
                    nombre = "Zampa",
                    descripcion = "Atriplex lampa. Es un arbusto de amplio volumen y color grisáceo, que alcanza hasta 1 m. de altura. Se pueden encontrar plantas masculinas y femeninas, las cuales se distinguen a simple vista al florecer y que varían del color fucsia al amarillo anaranjado. Las femeninas cuando fructifican, toman un color amarillo – ocre. Los frutos son secos, pequeños y están protegidos por dos membranas que se abren como una almeja, dejándolos en libertad. Es común encontrarlo en suelos de materiales salinos. Se ha denominado también “yerba del diablo”, ya que los pueblos nativos lo utilizaban para alejar los malos espíritus.",
                    imagenesRes = "zampa,zampa2,zampa3".toString(),
                    latitud = -46.4222783,
                    longitud = -67.5242653
                ),
                Planta(
                    id = "quilimbay",
                    nombre = "Quilimbay",
                    descripcion = "Chuquiraga avellanedae. Es un arbusto de hasta 1 m. de altura cuyas hojas, anchas y duras, terminan en una pequeña espina. Tiene una floración prolongada y permanecen restos secos de color amarillo intenso gran parte del año. Comienza a fines de noviembre y entre diciembre y enero se encuentra en plenitud. Los frutos son panaderos blancos y peludos que viajan en grupos llevados por el viento. Es utilizado en medicina popular para calmar las irritaciones de garganta. Flores y frutos son consumidos por el ganado. Se asocia a uña de gato en suelos arcillosos y a falso tomillo en arcilloso – salinos. Es muy abundante en la estepa patagónica.",
                    imagenesRes = "quilimbay,quilimbay2,quilimbay3,quilimbay4",
                    latitud = -46.4222783,
                    longitud = -67.5242653
                ),
                // ****** Parada 3 (-46.4212241, -67.5253720) ******
                Planta(
                    id = "falsotomillo",
                    nombre = "Falso Tomillo",
                    descripcion = "Frankenia patagónica. Es una mata baja con ramas muy abiertas y desgarradas, tendidas sobre el suelo. En el inicio de la primavera, desarrolla hojas muy pequeñas y de color verde grisáceo por la presencia de sales. Suele confundirse con el tomillo (de ahí su nombre), pero al saborearla se detecta lo salada que es. Por esta característica, se denomina mata salada. Muchos arbustos como este, muestran un crecimiento totalmente diferente en espacios muy cercanos. Esta estrategia se denomina plasticidad fenotípica y tiene directa relación con las condiciones climáticas y otros factores que deben soportar del ambiente en el que viven. Sus flores son blancas y tenues. Se destacan a fines de enero y principios de febrero porque cuando no hay casi plantas florecidas, matizan sus ramas. En la estación más fría, su follaje se vuelve color ocre y se destacan las ramas de color gris claro terminadas en punta.",
                    imagenesRes = "falsotomillo".toString(),
                    latitud = -46.4212241,
                    longitud =  -67.5253720
                ),
                Planta(
                    id = "cactusaustral",
                    nombre = "Cactus Austral",
                    descripcion = "Austrocactus bertinii. Es una planta columnar con costillas rectas y espinas radiales y o centrales algo arqueadas. Las flores son acampanadas rosado – amarillentas y de un tamaño grande, dispuestas próximas al ápice en número de tres – cinco o más, recubiertas externamente por mechones lanosos y cerdas oscuras. El estigma de la flor está formado por 16 lóbulos de color púrpura. Fácilmente observable debajo de otros arbustos. La época de floración es en el comienzo del verano. Sus flores atraen una gran diversidad e insectos polinizadores. Las semillas germinan con mucha facilidad.",
                    imagenesRes = "cactusaustral,cactusaustral2,cactusaustral3".toString(),
                    latitud = -46.4212241,
                    longitud =  -67.5253720
                ),
                // ****** Parada 4 (-46.4206415, -67.5261311) ******
                Planta(
                    id = "tuna",
                    nombre = "Tuna",
                    descripcion = "Maihuenopsis darwinii. Es un cactus bajo y rastrero, formado por eslabones carnosos de color verde, con numerosas espinas. En primavera, intensifican su color y se vuelven turgentes, preparándose para la floración. Presentan numerosos estambres densamente agrupados. Cuando los frutos maduran, matizan de color anaranjado intenso el almohadón de espinas. Su fruto es azucarado y lo consumen los animales. Son plantas adaptadas al clima desértico, durante la noche con temperaturas bajo cero en la mayor parte del año y durante el día, con intensa radiación solar. Esta familia es endémica de América. La distribución de esta especie es exclusiva de América del Sur.",
                    imagenesRes = "tuna,tuna2,tuna3,tuna4".toString(),
                    latitud = -46.4206415,
                    longitud = -67.5261311
                ),
                Planta(
                    id = "malaspina",
                    nombre = "Malaspina",
                    descripcion = "Retanilla patagónica. Es un arbusto de gran tamaño que muestra una arquitectura especial. Desarrolla espinas opuestas y gruesas que se transforman en ramas simétricas de color verde claro. Al inicio de la primavera, abundantes flores pequeñas de color blanco cremoso lo cubren cambiando su aspecto hostil. Simultáneamente, aparecen sus hojas que varían de tamaño de acuerdo a las condiciones de humedad y protección. Los frutos son carnosos cuando inmaduros y se secan descascarándose al madurar. Sus grandes semillas son muy apetecidas por los roedores de la zona. Es muy abundante en los cañadones costeros formando, en algunos casos, matorrales densos. Es compañera inseparable del duraznillo.",
                    imagenesRes = "malaspina,malaspina2".toString(),
                    latitud = -46.4206415,
                    longitud = -67.5261311
                ),
                Planta(
                    id = "chilca",
                    nombre = "Chilca",
                    descripcion = "Baccharis darwinii. Es una planta leñosa que alcanza los 50 cm. De altura, similar al yuyo moro en el porte, pero no tiene aspecto ceniciento. Sus hojas son delgadas y las flores pequeñas de color blanco – amarillento, dispuestas en inflorescencias, en forma de cabezuela. Cuando todavía no han abierto, están tonalizadas por líneas moradas. En otoño, es muy vistosa, porque en los ápices de las ramas, persisten numerosas estrellitas plateadas. Estas formas provienen de un grupo de piezas dispuestas alrededor de la inflorescencia.",
                    imagenesRes = "".toString(),
                    latitud = -46.4206415,
                    longitud = -67.5261311
                ),
                Planta(
                    id = "coiron",
                    nombre = "Coirón",
                    descripcion = "Stipa humilis. Es una hierba que caracteriza el paisaje estepario de Patagonia. Su nombre se debe a que mantiene la forma de “llama”, dado que no es consumida por los herbívoros. Las hojas son pajizas blanco-amarillentas, al apoyar la mano sobre ellas, se percibe su rigidez. Sus flores diminutas y simples se reúnen en una espiga muy tenue y grácil. Los frutos son pequeños y plumosos. Poseen una larga y delgada extensión llamada arista que, en su base, se enrolla como una espira y en el extremo, se dobla en ángulo recto. Estas características le permiten ser desplazados por el viento, clavarse en el suelo y así, cubrir nuevos espacios. Entre el follaje, persisten durante el año, pequeñas cintas doradas que corresponden a restos de la espiga.",
                    imagenesRes = "coiron".toString(),
                    latitud = -46.4206415,
                    longitud = -67.5261311
                ),
                // ****** Parada 5 (-46.4200740, -67.5266799) ******
                Planta(
                    id = "matalaguna",
                    nombre = "Mata Laguna",
                    descripcion = "Lycium ameghinoi. Es un arbusto de corteza rugosa y rústica, cuya silueta se reconoce a distancia por su contorno zigzagueante. Es frecuente observar su leño cubierto por formaciones de color anaranjado, muy llamativas, fácilmente visibles cuando la planta se encuentra sin follaje. Dichos organismos se llaman líquenes y se desarrollan cuando el ambiente no está contaminado. En primavera, se cubre de hojas algo carnosas y más pequeñas que las del yaoyín. Las flores de color blanco-cremoso, se encuentran adheridas a la rama. Tienen forma de una diminuta campana. Se encuentra en floración desde octubre hasta diciembre.",
                    imagenesRes = "matalaguna",
                    latitud = -46.4200740,
                    longitud = -67.5266799
                ),
                Planta(
                    id = "yaoyin",
                    nombre = "Yaoyín",
                    descripcion = "Lycium chilense. Es un arbusto que, de acuerdo al lugar en el que se desarrolla, presenta distinta densidad de follaje. Las ramificaciones son rectas, flexibles y terminadas en espinas. Hacia fines de verano y principio de otoño, pierde las hojas dejando sus ramas grises y delgadas. Las flores son pequeños embudos de color blanco-verdosos. Los frutos tienen forma de “tomate perita”, jugosa y translúcida, que varían su color desde el anaranjado hacia el púrpura.  Vive en terrenos algo arenosos y en sectores más protegidos de cañadones, generalmente se lo observa acompañado por verbena.",
                    imagenesRes = "yaoyin",
                    latitud = -46.4200740,
                    longitud = -67.5266799
                ),
                // ****** Parada 6 (-46.419634, -67.526907) ******
                Planta(
                    id = "duraznillo",
                    nombre = "Duraznillo",
                    descripcion = "Colliguaja integérrima. Es un arbusto típico siempre verde de la estepa patagónica.  Define junto a la malaspina, el Distrito florístico del Golfo San Jorge. Las hojas son grandes, de color verde botella brillante y al cortarlas, derraman una sustancia blanca llamada látex. Las comunidades nativas posiblemente la utilizaban para colocar en las puntas de lanza para la caza de guanacos. Cuando florece, se cubre de espigas rojizas que, al madurar, se vuelven amarillas y liberan abundante polen. En la base de la espiga, se desarrollan dos o tres frutos. Estos se parecen, por su forma y color, a pequeños duraznillos. Cuando maduran se transforman en frutos marrones y secos, como dos avellanas, que se abren liberando grandes semillas. Esta es una de las pocas especies que persiste el avance de los médanos. En medicina popular, se utiliza para quebraduras y dolores.",
                    imagenesRes = "duraznillo,duraznillo2",
                    latitud = -46.419634,
                    longitud = -67.526907
                ),
                Planta(
                    id = "verbena",
                    nombre = "Verbena",
                    descripcion = "Junelia ligustrina. Es un arbusto de ramas flexibles. Se la observa muchas veces creciendo entre otros arbustos más rígidos. Las flores, de color amarillo-azufre con tintes rojizos, se disponen en como espigas cilíndricas. Forman grupos de flores pequeñas llamadas inflorescencias. Se puede ver en floración en los meses de noviembre-diciembre. Son fuertemente aromáticas, especialmente al atardecer.",
                    imagenesRes = "verbena",
                    latitud = -46.419634,
                    longitud = -67.526907
                ),
                Planta(
                    id = "botondeoro",
                    nombre = "Botón de oro",
                    descripcion = "Grindelia chiloensis. Es una planta muy ramosa en la base y resinosa, característica de regiones secas, rocosas o arenosas en toda la Patagonia. Sus hojas son grandes. Las flores de color amarillo, similares a margaritas, se ubican en los extremos de las ramas. En el pimpollo se acumula una sustancia lechosa llamada resina que, al tocarla, es muy pegajosa. Una vez fructificada y diseminados sus frutos permanecen, por largo tiempo, los talluelos rígidos de color marrón brillante.  Por su belleza, estas formas son colectadas para arreglos florales y artesanías. Además, se utiliza toda la planta para infusiones que bajan la fiebre y para curar dolores como una especie de ungüento.",
                    imagenesRes = "botondeoro,botondeoro2,botondeoro3,botondeoro4".toString(),
                    latitud = -46.419634,
                    longitud = -67.526907
                ),
                // ****** Parada 7 (-46.420192, -67.528466) ******
                Planta(
                    id = "sulupe",
                    nombre = "Sulupe",
                    descripcion = "Ephedra ochreata. Es una planta de gran tamaño, muy ramificada desde la base. Sus ramas son verdes, cilíndricas que se adelgazan hacia la parte superior. Las hojas son muy pequeñas, transformadas en escamas de color pardo grisáceo, que se disponen alrededor de los nudos. Se pueden encontrar plantas femeninas y masculinas. Es más frecuente detectar las plantas masculinas a finales de octubre, porque en los nudos se desarrollan numerosos botones amarillos de los que sobresalen los estambres. En las plantas femeninas, se observan pequeños conos de color verde cuando inmaduros. En los meses de diciembre – enero, se vuelven carnosos y jugosos de color rojo. Estos frutos son apetecidos por los animales de la zona. Contienen una sustancia llamada efedrina. Su uso en medicina casera no es aconsejable. Dicha especie es una representante de la estepa patagónica, que pertenece al grupo de Gimnospermas nativas de los bosques andinos patagónicos, tales como el alerce, ciprés de la cordillera y araucaria, lo que quiere decir que sus antecesores evolutivos son muy antiguos. Por dicho motivo, no tiene flores.",
                    imagenesRes = "sulupe,sulupe2,sulupe3,sulupe4".toString(),
                    latitud = -46.420192,
                    longitud = -67.528466
                ),
                // ****** Parada 8 (-46.419955, -67.528864) ******
                Planta(
                    id = "algarrobillo",
                    nombre = "Algarrobillo",
                    descripcion = "Prosopis denudans. Es un arbusto que alcanza los 2 metros de altura, ramoso desde la base formando una copa muy amplia. En esta zona, por su ubicación, se encuentra achaparrado y de pequeño porte.  Presenta espinas gruesas y rígidas que cambian de color a medida que transcurre el tiempo. Se pueden distinguir las ramas nuevas porque tienen un color rojizo brillante. En diciembre, se llena de abundantes espigas amarillas y finalizando el verano, se desarrollan legumbres de color marrón oscuro, las que permanecen durante años en la planta. Las legumbres o chauchas presentan distinto grado de enrollamiento, se pueden observar en forma de C, rectas o con una o más vueltas en espiral. Los pueblos nativos y estancieros seguramente, lo han utilizado como un alimento que se parece a una pasta de harina dulce.",
                    imagenesRes = "algarrobillo",
                    latitud = -46.419955,
                    longitud = -67.528864
                ),
                // ****** Parada 9 (-46.4187621, -67.5275014) ******
                Planta(
                    id = "maihuenia",
                    nombre = "Maihuenia patagonica",
                    descripcion = "Estas plantas forman densos cojines de no mucha altura pero que pueden alcanzar varios metros de diámetro. Presentan espinas en grupos de a tres, todas aplanadas lateralmente. Las hojas son pequeñas y se encuentran agrupadas sobre las ramas jóvenes la mayoría de las veces. Las flores son blancas y muy frecuentadas por los insectos polinizadores. Es una planta bastante común en distintos tipos de suelos áridos. Su floración puede surgir principalmente en el mes de diciembre.",
                    imagenesRes = "maihuenia,maihuenia2,maihuenia3,maihuenia4",
                    latitud = -46.4187621,
                    longitud = -67.5275014
                ),
                // ****** Parada 10 (-46.4181603, -67.5278547) ******
                Planta(
                    id = "tomillo",
                    nombre = "Tomillo",
                    descripcion = "Acantholippia seriphioides. Es un arbusto muy ramoso que habita zonas secas y áridas. Es generalmente bajo y aromático. Se reconoce fácilmente tomando las hojitas y frotándolas entre los dedos, ya que libera una fragancia cítrica especial. Se estudian sus aceites esenciales como estimulantes y se lo utiliza como condimento. En otoño – invierno, su follaje vira de tonos ocráceos intensos hasta el morado. Su mejor compañero es el tomillo rosa. Ambos en este periodo, se mimetizan, pero se lo puede reconocer por su aroma. A mediados de primavera, se cubre de pequeños ramilletes de flores blanco – cremosas. El tomillo es una de las plantas medicinales más antiguas. Procede de los países mediterráneos y crece con abundante sol en suelo seco, árido y rocoso. En la antigüedad, los médicos griegos Hipócrates, Teofrasto y Dioscórides y a lo tenían en alta estima por sus poderes curativos. Sus efectos expectorantes en caso de tos y bronquitis también han sido confirmados por la ciencia actual. Sus efectos calmantes de la tos y antiespasmódicos, incluso se han podido comprobar en la tos ferina. Empleado en forma de baños refuerza los nervios y actúa favorablemente en trastornos cutáneos de tipo inflamatorio. El tomillo también es una hierba de cocina sumamente apreciaba, ya que ayuda a digerir mejor las comidas.",
                    imagenesRes = "tomillo",
                    latitud = -46.4181603,
                    longitud = -67.5278547
                ),
                Planta(
                    id = "fabiana",
                    nombre = "Fabiana",
                    descripcion = "Fabiana nana. Es un arbusto de apariencia áfila, muy ramoso que llega a 1.5 m de altura, pero puede encontrarse como cojín al ras del suelo. Las ramas jóvenes presentan pequeñas hojitas que crecen paralelas a las ramas. Sin embargo, presenta un aspecto áfilo (sin hojas), debido a su tamaño milimétrico. Las flores son tubuladas y de color blanco-amarillento o amarillo-verdoso. Se disponen en gran cantidad en los extremos de las ramitas jóvenes.",
                    imagenesRes = "fabiana,fabiana2,fabiana3",
                    latitud = -46.4181603,
                    longitud = -67.5278547
                ),
                Planta(
                    id = "brachiclados",
                    nombre = "Brachiclados",
                    descripcion = "Arbusto bajo y resistente. Se caracteriza por un crecimiento ramificado, con hojas pequeñas y angostas que se enrollan en los bordes lo que les ayuda a conservar agua. Posee flores, parecidas a margaritas, son de color amarillo y aparecen de manera solitaria en las puntas de las ramas. Están adaptadas a condiciones extremas de viento, sequías y suelos pobres. Suelen ser muy bajas, con el aspecto de una piedra, desarrollándose bien cerca del suelo. Cumplen un papel importante en el ecosistema: ayudan a evitar la erosión, brindan refugio a insectos polinizadores y mantienen la vegetación en ambientes áridos.",
                    imagenesRes = "brachiclados",
                    latitud = -46.4181603,
                    longitud = -67.5278547
                ),

                // ****** Agregamos más paradas ******

            )

            plantaDao.insertarPlantas(plantas)
        }
    }
}