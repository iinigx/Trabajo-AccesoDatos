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

## Funcion de cada paquete

| Paquete    | Responsabilidad                          |
|------------|------------------------------------------|
| `model`    | Datos + JAXB. No depende de nadie.       |
| `csv`      | Lectura/escritura secuencial del CSV.    |
| `xml`      | Marshalling/Unmarshalling con JAXB.      |
| `menu`     | Consola: mostrar menu y pedir datos.      |
| `util`     | Informacion de ficheros.                 |
| `exception`| Excepciones propias con mensajes en español. |


## Detalles importantes

- Acceso **secuencial** al CSV: `BufferedReader` + `readLine()`. Nada de `readAllLines()`.
- `codigoProveedor` = `@XmlTransient` -> **no** aparece en el XML.
- `id` = `@XmlAttribute` -> sale como `id="1"`. El resto, `@XmlElement`.
- Excepciones: capturar `IOException`/`NumberFormatException` dentro de `csv`/`xml`
  y re-lanzarlas como `CatalogoException` (traduccion de excepciones).
- El CSV de partida tiene `Switch 2` con coma dentro: hay que decidir como se separa
  y justificarlo en la defensa.

