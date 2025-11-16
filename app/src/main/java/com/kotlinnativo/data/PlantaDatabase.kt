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
                    nombre = "Uña de Gato",
                    descripcion = "Chuquiraga Aurea. Es una planta baja y leñosa de hojas delgadas como agujas. De allí, se denomina también pinchabola o colchón de suegra. Se extiende sobre el suelo formando almohadones de gran magnitud y en terrenos bajos. Arbustos de este género (Chuquiraga), tienen la capacidad de desarrollar una raíz principal muy profunda, pudiendo alcanzar los 20 metros de ser necesario. A finales de noviembre, se cubre de flores de color amarillo – dorado. Cuando fructifican, los panaderos se diseminan lentamente. Por largo tiempo, permanecen en la planta restos secos y brillantes del mismo color de las flores que, por su aspecto, semejan las conocidas siemprevivas de los arreglos florales.",
                    imagenesRes = "unadegato,unadegato2,unadegato3".toString(),
                    latitud = -46.4224105,
                    longitud = -67.5239494
                ),
                // ****** Parada 2 (-46.4222783, -67.5242653) ******
                Planta(
                    id = "zampa",
                    nombre = "Zampa",
                    descripcion = "Atriplex Lampa. Es un arbusto de amplio volumen y color grisáceo, que alcanza hasta 1 m. de altura. Se pueden encontrar plantas masculinas y femeninas, las cuales se distinguen a simple vista al florecer y que varían del color fucsia al amarillo anaranjado. Las femeninas cuando fructifican, toman un color amarillo – ocre. Los frutos son secos, pequeños y están protegidos por dos membranas que se abren como una almeja, dejándolos en libertad. Es común encontrarlo en suelos de materiales salinos. Se ha denominado también “yerba del diablo”, ya que los pueblos nativos lo utilizaban para alejar los malos espíritus.",
                    imagenesRes = "zampa,zampa2,zampa3,zampa4".toString(),
                    latitud = -46.4222783,
                    longitud = -67.5242653
                ),
                Planta(
                    id = "quilimbay",
                    nombre = "Quilimbay",
                    descripcion = "Chuquiraga Avellanedae. Es un arbusto de hasta 1 m. de altura cuyas hojas, anchas y duras, terminan en una pequeña espina. Tiene una floración prolongada y permanecen restos secos de color amarillo intenso gran parte del año. Comienza a fines de noviembre y entre diciembre y enero se encuentra en plenitud. Los frutos son panaderos blancos y peludos que viajan en grupos llevados por el viento. Es utilizado en medicina popular para calmar las irritaciones de garganta. Flores y frutos son consumidos por el ganado. Se asocia a uña de gato en suelos arcillosos y a falso tomillo en arcilloso – salinos. Es muy abundante en la estepa patagónica.",
                    imagenesRes = "quilimbay,quilimbay2,quilimbay3,quilimbay4",
                    latitud = -46.4222783,
                    longitud = -67.5242653
                ),
                // ****** Parada 3 (-46.4212241, -67.5253720) ******
                Planta(
                    id = "falsotomillo",
                    nombre = "Falso Tomillo",
                    descripcion = "Frankenia Patagónica. Es una mata baja con ramas muy abiertas y desgarradas, tendidas sobre el suelo. En el inicio de la primavera, desarrolla hojas muy pequeñas y de color verde grisáceo por la presencia de sales. Suele confundirse con el tomillo (de ahí su nombre), pero al saborearla se detecta lo salada que es. Por esta característica, se denomina mata salada. Muchos arbustos como este, muestran un crecimiento totalmente diferente en espacios muy cercanos. Esta estrategia se denomina plasticidad fenotípica y tiene directa relación con las condiciones climáticas y otros factores que deben soportar del ambiente en el que viven. Sus flores son blancas y tenues. Se destacan a fines de enero y principios de febrero porque cuando no hay casi plantas florecidas, matizan sus ramas. En la estación más fría, su follaje se vuelve color ocre y se destacan las ramas de color gris claro terminadas en punta.",
                    imagenesRes = "falsotomillo,falsotomillo2,falsotomillo3".toString(),
                    latitud = -46.4212241,
                    longitud =  -67.5253720
                ),
                Planta(
                    id = "cactusaustral",
                    nombre = "Cactus Austral",
                    descripcion = "Austrocactus Bertinii. Es una planta columnar con costillas rectas y espinas radiales y o centrales algo arqueadas. Las flores son acampanadas rosado – amarillentas y de un tamaño grande, dispuestas próximas al ápice en número de tres – cinco o más, recubiertas externamente por mechones lanosos y cerdas oscuras. El estigma de la flor está formado por 16 lóbulos de color púrpura. Fácilmente observable debajo de otros arbustos. La época de floración es en el comienzo del verano. Sus flores atraen una gran diversidad e insectos polinizadores. Las semillas germinan con mucha facilidad.",
                    imagenesRes = "cactusaustral,cactusaustral2,cactusaustral3,cactusaustral4".toString(),
                    latitud = -46.4212241,
                    longitud =  -67.5253720
                ),
                // ****** Parada 4 (-46.4206415, -67.5261311) ******
                Planta(
                    id = "tuna",
                    nombre = "Tuna",
                    descripcion = "Maihuenopsis Darwinii. Es un cactus bajo y rastrero, formado por eslabones carnosos de color verde, con numerosas espinas. En primavera, intensifican su color y se vuelven turgentes, preparándose para la floración. Presentan numerosos estambres densamente agrupados. Cuando los frutos maduran, matizan de color anaranjado intenso el almohadón de espinas. Su fruto es azucarado y lo consumen los animales. Son plantas adaptadas al clima desértico, durante la noche con temperaturas bajo cero en la mayor parte del año y durante el día, con intensa radiación solar. Esta familia es endémica de América. La distribución de esta especie es exclusiva de América del Sur.",
                    imagenesRes = "tuna,tuna2,tuna3,tuna4".toString(),
                    latitud = -46.4206415,
                    longitud = -67.5261311
                ),
                Planta(
                    id = "malaspina",
                    nombre = "Malaspina",
                    descripcion = "Retanilla Patagónica. Es un arbusto de gran tamaño que muestra una arquitectura especial. Desarrolla espinas opuestas y gruesas que se transforman en ramas simétricas de color verde claro. Al inicio de la primavera, abundantes flores pequeñas de color blanco cremoso lo cubren cambiando su aspecto hostil. Simultáneamente, aparecen sus hojas que varían de tamaño de acuerdo a las condiciones de humedad y protección. Los frutos son carnosos cuando inmaduros y se secan descascarándose al madurar. Sus grandes semillas son muy apetecidas por los roedores de la zona. Es muy abundante en los cañadones costeros formando, en algunos casos, matorrales densos. Es compañera inseparable del duraznillo.",
                    imagenesRes = "malaspina,malaspina2,malaspina3".toString(),
                    latitud = -46.4206415,
                    longitud = -67.5261311
                ),
                Planta(
                    id = "chilca",
                    nombre = "Chilca",
                    descripcion = "Baccharis Darwinii. Es una planta leñosa que alcanza los 50 cm. De altura, similar al yuyo moro en el porte, pero no tiene aspecto ceniciento. Sus hojas son delgadas y las flores pequeñas de color blanco – amarillento, dispuestas en inflorescencias, en forma de cabezuela. Cuando todavía no han abierto, están tonalizadas por líneas moradas. En otoño, es muy vistosa, porque en los ápices de las ramas, persisten numerosas estrellitas plateadas. Estas formas provienen de un grupo de piezas dispuestas alrededor de la inflorescencia.",
                    imagenesRes = "chilca".toString(),
                    latitud = -46.4206415,
                    longitud = -67.5261311
                ),
                Planta(
                    id = "coiron",
                    nombre = "Coirón",
                    descripcion = "Stipa Humilis. Es una hierba que caracteriza el paisaje estepario de Patagonia. Su nombre se debe a que mantiene la forma de “llama”, dado que no es consumida por los herbívoros. Las hojas son pajizas blanco-amarillentas, al apoyar la mano sobre ellas, se percibe su rigidez. Sus flores diminutas y simples se reúnen en una espiga muy tenue y grácil. Los frutos son pequeños y plumosos. Poseen una larga y delgada extensión llamada arista que, en su base, se enrolla como una espira y en el extremo, se dobla en ángulo recto. Estas características le permiten ser desplazados por el viento, clavarse en el suelo y así, cubrir nuevos espacios. Entre el follaje, persisten durante el año, pequeñas cintas doradas que corresponden a restos de la espiga.",
                    imagenesRes = "coiron".toString(),
                    latitud = -46.4206415,
                    longitud = -67.5261311
                ),
                // ****** Parada 5 (-46.4200740, -67.5266799) ******
                Planta(
                    id = "matalaguna",
                    nombre = "Mata Laguna",
                    descripcion = "Lycium Ameghinoi. Es un arbusto de corteza rugosa y rústica, cuya silueta se reconoce a distancia por su contorno zigzagueante. Es frecuente observar su leño cubierto por formaciones de color anaranjado, muy llamativas, fácilmente visibles cuando la planta se encuentra sin follaje. Dichos organismos se llaman líquenes y se desarrollan cuando el ambiente no está contaminado. En primavera, se cubre de hojas algo carnosas y más pequeñas que las del yaoyín. Las flores de color blanco-cremoso, se encuentran adheridas a la rama. Tienen forma de una diminuta campana. Se encuentra en floración desde octubre hasta diciembre.",
                    imagenesRes = "matalaguna,matalaguna2,matalaguna3,matalaguna4",
                    latitud = -46.4200740,
                    longitud = -67.5266799
                ),
                Planta(
                    id = "yaoyin",
                    nombre = "Yaoyín",
                    descripcion = "Lycium Chilense. Es un arbusto que, de acuerdo al lugar en el que se desarrolla, presenta distinta densidad de follaje. Las ramificaciones son rectas, flexibles y terminadas en espinas. Hacia fines de verano y principio de otoño, pierde las hojas dejando sus ramas grises y delgadas. Las flores son pequeños embudos de color blanco-verdosos. Los frutos tienen forma de “tomate perita”, jugosa y translúcida, que varían su color desde el anaranjado hacia el púrpura.  Vive en terrenos algo arenosos y en sectores más protegidos de cañadones, generalmente se lo observa acompañado por verbena.",
                    imagenesRes = "yaoyin,yaoyin2",
                    latitud = -46.4200740,
                    longitud = -67.5266799
                ),
                // ****** Parada 6 (-46.419634, -67.526907) ******
                Planta(
                    id = "duraznillo",
                    nombre = "Duraznillo",
                    descripcion = "Colliguaja Integérrima. Es un arbusto típico siempre verde de la estepa patagónica.  Define junto a la malaspina, el Distrito florístico del Golfo San Jorge. Las hojas son grandes, de color verde botella brillante y al cortarlas, derraman una sustancia blanca llamada látex. Las comunidades nativas posiblemente la utilizaban para colocar en las puntas de lanza para la caza de guanacos. Cuando florece, se cubre de espigas rojizas que, al madurar, se vuelven amarillas y liberan abundante polen. En la base de la espiga, se desarrollan dos o tres frutos. Estos se parecen, por su forma y color, a pequeños duraznillos. Cuando maduran se transforman en frutos marrones y secos, como dos avellanas, que se abren liberando grandes semillas. Esta es una de las pocas especies que persiste el avance de los médanos. En medicina popular, se utiliza para quebraduras y dolores.",
                    imagenesRes = "duraznillo,duraznillo2,duraznillo3,duraznillo4",
                    latitud = -46.419634,
                    longitud = -67.526907
                ),
                Planta(
                    id = "verbena",
                    nombre = "Verbena",
                    descripcion = "Junelia ligustrina. Es un arbusto de ramas flexibles. Se la observa muchas veces creciendo entre otros arbustos más rígidos. Las flores, de color amarillo-azufre con tintes rojizos, se disponen en como espigas cilíndricas. Forman grupos de flores pequeñas llamadas inflorescencias. Se puede ver en floración en los meses de noviembre-diciembre. Son fuertemente aromáticas, especialmente al atardecer.",
                    imagenesRes = "verbena,verbena2",
                    latitud = -46.419634,
                    longitud = -67.526907
                ),
                Planta(
                    id = "botondeoro",
                    nombre = "Botón de Oro",
                    descripcion = "Grindelia Chiloensis. Es una planta muy ramosa en la base y resinosa, característica de regiones secas, rocosas o arenosas en toda la Patagonia. Sus hojas son grandes. Las flores de color amarillo, similares a margaritas, se ubican en los extremos de las ramas. En el pimpollo se acumula una sustancia lechosa llamada resina que, al tocarla, es muy pegajosa. Una vez fructificada y diseminados sus frutos permanecen, por largo tiempo, los talluelos rígidos de color marrón brillante.  Por su belleza, estas formas son colectadas para arreglos florales y artesanías. Además, se utiliza toda la planta para infusiones que bajan la fiebre y para curar dolores como una especie de ungüento.",
                    imagenesRes = "botondeoro,botondeoro2,botondeoro3,botondeoro4".toString(),
                    latitud = -46.419634,
                    longitud = -67.526907
                ),
                // ****** Parada 7 (-46.420192, -67.528466) ******
                Planta(
                    id = "sulupe",
                    nombre = "Sulupe",
                    descripcion = "Ephedra Ochreata. Es una planta de gran tamaño, muy ramificada desde la base. Sus ramas son verdes, cilíndricas que se adelgazan hacia la parte superior. Las hojas son muy pequeñas, transformadas en escamas de color pardo grisáceo, que se disponen alrededor de los nudos. Se pueden encontrar plantas femeninas y masculinas. Es más frecuente detectar las plantas masculinas a finales de octubre, porque en los nudos se desarrollan numerosos botones amarillos de los que sobresalen los estambres. En las plantas femeninas, se observan pequeños conos de color verde cuando inmaduros. En los meses de diciembre – enero, se vuelven carnosos y jugosos de color rojo. Estos frutos son apetecidos por los animales de la zona. Contienen una sustancia llamada efedrina. Su uso en medicina casera no es aconsejable. Dicha especie es una representante de la estepa patagónica, que pertenece al grupo de Gimnospermas nativas de los bosques andinos patagónicos, tales como el alerce, ciprés de la cordillera y araucaria, lo que quiere decir que sus antecesores evolutivos son muy antiguos. Por dicho motivo, no tiene flores.",
                    imagenesRes = "sulupe,sulupe2,sulupe3,sulupe4".toString(),
                    latitud = -46.420192,
                    longitud = -67.528466
                ),
                // ****** Parada 8 (-46.419955, -67.528864) ******
                Planta(
                    id = "algarrobillo",
                    nombre = "Algarrobillo",
                    descripcion = "Prosopis Denudans. Es un arbusto que alcanza los 2 metros de altura, ramoso desde la base formando una copa muy amplia. En esta zona, por su ubicación, se encuentra achaparrado y de pequeño porte.  Presenta espinas gruesas y rígidas que cambian de color a medida que transcurre el tiempo. Se pueden distinguir las ramas nuevas porque tienen un color rojizo brillante. En diciembre, se llena de abundantes espigas amarillas y finalizando el verano, se desarrollan legumbres de color marrón oscuro, las que permanecen durante años en la planta. Las legumbres o chauchas presentan distinto grado de enrollamiento, se pueden observar en forma de C, rectas o con una o más vueltas en espiral. Los pueblos nativos y estancieros seguramente, lo han utilizado como un alimento que se parece a una pasta de harina dulce.",
                    imagenesRes = "algarrobillo,algarrobillo2,algarrobillo3,algarrobillo4",
                    latitud = -46.419955,
                    longitud = -67.528864
                ),
                // ****** Parada 9 (-46.4187621, -67.5275014) ******
                Planta(
                    id = "maihuenia",
                    nombre = "Maihuenia Patagonica",
                    descripcion = "Estas plantas forman densos cojines de no mucha altura pero que pueden alcanzar varios metros de diámetro. Presentan espinas en grupos de a tres, todas aplanadas lateralmente. Las hojas son pequeñas y se encuentran agrupadas sobre las ramas jóvenes la mayoría de las veces. Las flores son blancas y muy frecuentadas por los insectos polinizadores. Es una planta bastante común en distintos tipos de suelos áridos. Su floración puede surgir principalmente en el mes de diciembre.",
                    imagenesRes = "maihuenia,maihuenia2,maihuenia3,maihuenia4",
                    latitud = -46.4187621,
                    longitud = -67.5275014
                ),
                // ****** Parada 10 (-46.4181603, -67.5278547) ******
                Planta(
                    id = "tomillo",
                    nombre = "Tomillo",
                    descripcion = "Acantholippia Seriphioides. Es un arbusto muy ramoso que habita zonas secas y áridas. Es generalmente bajo y aromático. Se reconoce fácilmente tomando las hojitas y frotándolas entre los dedos, ya que libera una fragancia cítrica especial. Se estudian sus aceites esenciales como estimulantes y se lo utiliza como condimento. En otoño – invierno, su follaje vira de tonos ocráceos intensos hasta el morado. Su mejor compañero es el tomillo rosa. Ambos en este periodo, se mimetizan, pero se lo puede reconocer por su aroma. A mediados de primavera, se cubre de pequeños ramilletes de flores blanco – cremosas. El tomillo es una de las plantas medicinales más antiguas. Procede de los países mediterráneos y crece con abundante sol en suelo seco, árido y rocoso. En la antigüedad, los médicos griegos Hipócrates, Teofrasto y Dioscórides y a lo tenían en alta estima por sus poderes curativos. Sus efectos expectorantes en caso de tos y bronquitis también han sido confirmados por la ciencia actual. Sus efectos calmantes de la tos y antiespasmódicos, incluso se han podido comprobar en la tos ferina. Empleado en forma de baños refuerza los nervios y actúa favorablemente en trastornos cutáneos de tipo inflamatorio. El tomillo también es una hierba de cocina sumamente apreciaba, ya que ayuda a digerir mejor las comidas.",
                    imagenesRes = "tomillo,tomillo2",
                    latitud = -46.4181603,
                    longitud = -67.5278547
                ),
                Planta(
                    id = "fabiana",
                    nombre = "Fabiana",
                    descripcion = "Fabiana Nana. Es un arbusto de apariencia áfila, muy ramoso que llega a 1.5 m de altura, pero puede encontrarse como cojín al ras del suelo. Las ramas jóvenes presentan pequeñas hojitas que crecen paralelas a las ramas. Sin embargo, presenta un aspecto áfilo (sin hojas), debido a su tamaño milimétrico. Las flores son tubuladas y de color blanco-amarillento o amarillo-verdoso. Se disponen en gran cantidad en los extremos de las ramitas jóvenes.",
                    imagenesRes = "fabiana,fabiana2,fabiana3",
                    latitud = -46.4181603,
                    longitud = -67.5278547
                ),
                Planta(
                    id = "brachiclados",
                    nombre = "Brachiclados",
                    descripcion = "Arbusto bajo y resistente. Se caracteriza por un crecimiento ramificado, con hojas pequeñas y angostas que se enrollan en los bordes lo que les ayuda a conservar agua. Posee flores, parecidas a margaritas, son de color amarillo y aparecen de manera solitaria en las puntas de las ramas. Están adaptadas a condiciones extremas de viento, sequías y suelos pobres. Suelen ser muy bajas, con el aspecto de una piedra, desarrollándose bien cerca del suelo. Cumplen un papel importante en el ecosistema: ayudan a evitar la erosión, brindan refugio a insectos polinizadores y mantienen la vegetación en ambientes áridos.",
                    imagenesRes = "brachiclados,brachiclados2",
                    latitud = -46.4181603,
                    longitud = -67.5278547
                ),
                // ****** Agregamos más paradas ******


                // ****** Hierbas adicionales ******
                Planta(
                    id = "patadeperdiz",
                    nombre = "Pata de Perdiz",
                    descripcion = "Hoffmannseggia Trifoliata. Su nombre vulgar proviene de poseer hojas divididas en tres semejando la pata de perdiz. Es una hierba pequeña de color verde morado cuyo follaje se extiende sobre el suelo pedregoso. Sus flores son grandes y anaranjadas, con pequeñas manchas oscuras. Se disponen a lo largo de un talluelo, siendo las inferiores, las primeras en abrir. Produce frutos en forma de chaucha de pared muy delgada, lo que permite contar las semillas. Cuando los frutos maduran, se desprenden fácilmente. Es común su desarrollo en bordes de camino y en áreas descubiertas entre los arbustos y coirones.",
                    imagenesRes = "patadeperdiz,patadeperdiz2",
                    latitud = -46.419516367268194,
                    longitud = -67.52635102128295
                ),
                Planta(
                    id = "ortiga",
                    nombre = "Ortiga",
                    descripcion = "Amsinckia Hispida. Es una hierba anual que alcanza los 4 cm. De altura. La planta se presenta totalmente cubierta por pelos blanquecinos y rígidos. Las flores son pequeñas, de color amarillo – anaranjado. Se disponen en la porción superior del talluelo orientadas hacia un lado y enrolladas en espiral. Las flores se van abriendo desde arriba hacia abajo. Su atractivo aspecto invita a tomarla entre las manos, pero al tocarla se siente lo punzante que es. De esta característica, deriva el nombre vulgar. Su rigidez le permite mantenerse en pie durante largo tiempo cuando se seca.",
                    imagenesRes = "ortiga,ortiga2",
                    latitud = -46.419516367268194,
                    longitud = -67.52635102128295
                ),
                Planta(
                    id = "alfilerillo ",
                    nombre = "Alfilerillo ",
                    descripcion = "Erodium Cicutarium. A esta hierba se la conoce también como peludilla, por la abundante cantidad de pelos que presenta. Tiene hojas muy divididas y prolijamente extendidas sobre el suelo en forma de abanico. La tonalidad puede variar desde el rojo al anaranjado. Las flores son de color fucsia y los frutos pequeños y secos. Están caracterizados por tener una flechilla en forma de tirabuzón, que le permite adherirse a cualquier superficie, como el pelo de los animales, medias y pantalones. Se propaga con mucha facilidad, cubriendo la superficie. Crece en terrenos cultivados, suelos modificados y lugares abiertos desde la meseta hasta la costa. Su distribución es mundial. Es consumida por los animales y empleada como medicinal en infusiones. Se realizan cataplasmas para curar heridas y úlceras.",
                    imagenesRes = "alfilerillo",
                    latitud = -46.419516367268194,
                    longitud = -67.52635102128295
                ),
                Planta(
                    id = "llanten",
                    nombre = "Llantén",
                    descripcion = "Plantago Patagónica. Es una hierba anual común en médanos y otros tipos de suelos sueltos o modificados, donde forma verdaderos mantos de un color verde ceniciento. Las hojas basales, forman una roseta, lineales y ascendentes, cubiertas por una porosidad muy fina.",
                    imagenesRes = "llanten,llanten2,llanten3",
                    latitud = -46.419516367268194,
                    longitud = -67.52635102128295
                ),
                Planta(
                    id = "estrellita",
                    nombre = "Estrellita",
                    descripcion = "Tristagma Patagonicum. Es una hierba vivaz, pequeña y muy delgada, que alcanza los 9 cm. De altura. Su flor es blanca, elegante y muy atractiva. El aspecto es de una estrella y presenta en el lado externo, líneas pardo- verdosas. En época de floración, se la puede observar cercana a los arbustos y esparcida entre coirones. Presenta un bulbo subterráneo pequeño y blanco. Es una de las tantas plantas bulbosas, que semejan por su belleza, a las que se cultivan en los jardines. Los suelos en los que habita son arenosos y sueltos.",
                    imagenesRes = "estrellita,estrellita2,estrellita3",
                    latitud = -46.419516367268194,
                    longitud = -67.52635102128295
                ),
                Planta(
                    id = "marancel",
                    nombre = "Marancel",
                    descripcion = "Sisyrinchium Junceum. Es una hierba con pocas hojas basales, que pasan desapercibidas. Tiene el aspecto de un lirio. Se destaca el eje floral con 2 a 5 flores de color blanquecino con venas purpúreas.  Elige vivir en suelos arenosos y sueltos. Cuando encuentra el lugar más apropiado, cómodamente se extiende formando un sendero florido.",
                    imagenesRes = "marancel,marancel2,marancel3",
                    latitud = -46.419516367268194,
                    longitud = -67.52635102128295
                ),
                Planta(
                    id = "magallanatrialata",
                    nombre = "Magallana Trialata",
                    descripcion = "Es una hierba trepadora que no llega a alcanzar 1 cm. De longitud. Los pétalos son amarillos – anaranjados y el fruto es salado (sámara), con tres alas recubriendo una sola semilla grande. Esta enredadera suele verse comúnmente en médanos u otros suelos sueltos, donde sus tubérculos alcanzan una considerable profundidad. Es a partir de estos, que la planta se renueva anualmente. Florece desde fines de septiembre a principios de octubre y fructifica en noviembre y diciembre. Crece sobre matas de Quilimbay.",
                    imagenesRes = "magallanatrialata,magallanatriata2",
                    latitud = -46.419516367268194,
                    longitud = -67.52635102128295
                ),
                Planta(
                    id = "mostacilla",
                    nombre = "Mostacilla",
                    descripcion = "Diplotaxis Tenuifolia. Es una hierba perenne de base leñosa, que brota anualmente. Las flores son hermafroditas amarillas, de cuatro pétalos. Son polinizadas por una gran variedad de insectos. El fruto es una vaina alargada con muchas semillas de gran fertilidad. El rebrote comienza a fines del invierno. Florece en la primavera hasta avanzado el verano. Esta planta cosmopolita, conocida como mostacilla, es común encontrarla en terrenos baldíos, banquinas de rutas y caminos rurales y en muchos casos, como planta colonizadora de suelos en los que se ha extraído la flora autóctona.",
                    imagenesRes = "mostacilla,mostacilla2",
                    latitud = -46.419516367268194,
                    longitud = -67.52635102128295
                ),
                Planta(
                    id = "arjonatuberosa",
                    nombre = "Arjona Tuberosa",
                    descripcion = "Es una hierba que alcanza los 20 cm. De altura, pubescente en su totalidad. Las hojas son enteras, envainadoras, algo coriáceas, terminadas en punta. Los tallos subterráneos poseen estalones muy ramificados. Las flores se disponen en el vástago central y son de color blanco – violáceo, muy perfumadas y cubiertas de vellosidad. A esta especie suele vérsela en floración, en los meses de octubre – noviembre, en suelos bien drenados, generalmente, cubiertas de otras plantas mayores.",
                    imagenesRes = "arjonatuberosa",
                    latitud = -46.419516367268194,
                    longitud = -67.52635102128295
                ),
            )

            plantaDao.insertarPlantas(plantas)
        }
    }
}