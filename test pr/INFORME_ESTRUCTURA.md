# PromeHub Data Exchange - Estructura y plan de inicio

U1 - Persistencia en ficheros (incluido XML) | Modulo: Acceso a Datos | Curso 2026-27
Aplicacion Java de consola: CSV -> Java -> XML y XML -> Java -> CSV, con JAXB.

## Estructura

    test pr/
    |-- pom.xml                    JAXB + JUnit (JAXB ya no viene en el JDK)
    |-- README.md                  Documentacion del proyecto
    |-- INFORME_ESTRUCTURA.md      Este documento
    |-- data/
    |   |-- entrada/videojuegos.csv    CSV original (NO se modifica)
    |   `-- salida/                    XML y CSV que genera la app
    |-- docs/
    |   |-- PLANIFICACION.md
    |   `-- CATALOGO_PRUEBAS.md
    `-- src/
        |-- main/
        |   |-- java/com/promehub/dataexchange/
        |   |   |-- Main.java                   Punto de entrada -> lanza el menu
        |   |   |-- model/Videojuego.java       Modelo + anotaciones JAXB
        |   |   |-- model/Catalogo.java         @XmlRootElement("catalogo")
        |   |   |-- csv/CatalogoCSV.java        Acceso SECUENCIAL al CSV
        |   |   |-- xml/CatalogoXML.java        Lectura/escritura XML con JAXB
        |   |   |-- menu/Menu.java              Menu de 7 opciones
        |   |   |-- menu/Buscador.java          Buscar por id o titulo
        |   |   |-- util/InfoFichero.java       Existencia, tamano y ruta
        |   |   `-- exception/CatalogoException.java
        |   `-- resources/
        `-- test/java/...                 Pruebas JUnit 5

## Responsabilidad de cada paquete

| Paquete    | Responsabilidad                          |
|------------|------------------------------------------|
| `model`    | Datos + JAXB. No depende de nadie.       |
| `csv`      | Lectura/escritura secuencial del CSV.    |
| `xml`      | Marshalling/Unmarshalling con JAXB.      |
| `menu`     | Consola: mostrar menu y pedir datos.      |
| `util`     | Informacion de ficheros.                 |
| `exception`| Excepciones propias con mensajes en español. |

## Como empezamos (orden)

0. `mvn clean compile` -> comprobar que el entorno funciona.
1. **model/Videojuego.java** y **model/Catalogo.java**  <- aqui empezamos
   - Las 5 anotaciones: @XmlRootElement, @XmlAccessorType, @XmlAttribute,
     @XmlElement y @XmlTransient (esta ultima excluye `codigoProveedor`).
   - Constructor vacio obligatorio (lo necesita JAXB) + constructor completo + toString.
2. `exception/CatalogoException.java` -> `csv/CatalogoCSV.java`
   - Comprobar existencia -> `BufferedReader` + `readLine()` -> saltar cabecera ->
     validar 7 campos -> crear `Videojuego` -> anadir a la lista.
3. `xml/CatalogoXML.java` -> `escribir()` (Marshaller) y `leer()` (Unmarshaller).
4. `util/InfoFichero.java`, `menu/Buscador.java`, `menu/Menu.java`, `Main.java`.
5. Rellenar `docs/CATALOGO_PRUEBAS.md` con las 8 pruebas del enunciado.
6. Actualizar `README.md` y repartir la defensa.

## Detalles que no hay que olvidar

- Acceso **secuencial** al CSV: `BufferedReader` + `readLine()`. Nada de `readAllLines()`.
- `codigoProveedor` = `@XmlTransient` -> **no** aparece en el XML.
- `id` = `@XmlAttribute` -> sale como `id="1"`. El resto, `@XmlElement`.
- Excepciones: capturar `IOException`/`NumberFormatException` dentro de `csv`/`xml`
  y re-lanzarlas como `CatalogoException` (traduccion de excepciones).
- El CSV de partida tiene `Switch 2` con coma dentro: hay que decidir como se separa
  y justificarlo en la defensa.

## Comandos

    mvn clean compile                 compilar
    mvn exec:java                     ejecutar la aplicacion de consola
    mvn clean package && java -jar target/promehub-data-exchange.jar
    mvn test                          pruebas
