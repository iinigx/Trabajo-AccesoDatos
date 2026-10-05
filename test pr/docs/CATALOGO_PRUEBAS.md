# Catalogo de pruebas

Proyecto: PromeHub Data Exchange | Responsable: QA

Hay **dos tipos de pruebas**:

1. **Pruebas automaticas** (JUnit 5) -> se ejecutan con `mvn test`
2. **Pruebas manuales** -> se hacen a mano desde el menu de la aplicacion

---

# PARTE 1 - PRUEBAS AUTOMATICAS (JUnit 5)

Comando para ejecutarlas:

```bash
mvn test
```

Resultado obtenido:

```
Tests run: 28, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

| Fichero de pruebas | Numero de pruebas | Resultado |
|---|---|---|
| `model/VideojuegoTest.java` | 5 | TODAS CORRECTAS |
| `model/CatalogoTest.java` | 4 | TODAS CORRECTAS |
| `csv/CatalogoCSVTest.java` | 7 | TODAS CORRECTAS |
| `xml/CatalogoXMLTest.java` | 6 | TODAS CORRECTAS |
| `menu/BuscadorTest.java` | 6 | TODAS CORRECTAS |
| **TOTAL** | **28** | **28/28 CORRECTAS** |

## Detalle de las pruebas automaticas

### `CatalogoCSVTest` (acceso al CSV)

| Prueba | Que comprueba |
|---|---|
| `testLeerCsvCorrecto` | Se leen 5 videojuegos |
| `testDatosDelPrimerJuego` | Todos los datos del juego 1 son correctos |
| `testPlataformaConComa` | **La plataforma "Switch 2" se lee bien** (lleva coma) |
| `testFicheroInexistente` | Da error si el CSV no existe |
| `testRegistroConCamposDeMenos` | Da error si una linea no tiene 7 campos |
| `testPrecioNoNumerico` | Da error si el precio no es un numero |
| `testEscribirYVolverALeer` | El flujo escribir -> leer mantiene los datos |

### `CatalogoXMLTest` (JAXB) - LAS MAS IMPORTANTES

| Prueba | Que comprueba |
|---|---|
| `testCodigoProveedorNoApareceEnElXml` | **Ni "codigoProveedor" ni "PROV-" aparecen en el XML** |
| `testIdEsAtributoXml` | El id sale como atributo `id="1"`, no como `<id>` |
| `testElementoRaizCatalogo` | El elemento raiz es `<catalogo>` |
| `testFlujoCompleto` | **CSV -> Java -> XML -> Java -> CSV** entero |
| `testJuegosDelXmlNoTienenProveedor` | Los juegos del XML llegan sin proveedor |
| `testXmlInexistente` | Da error si el XML no existe |

### `BuscadorTest` (opcion 6)

| Prueba | Que comprueba |
|---|---|
| `testBuscarPorIdExistente` | Buscar id 1 -> Cyberpunk 2077 |
| `testBuscarPorIdNoExistente` | Buscar id 999 -> null |
| `testBuscarPorTituloExacto` | Buscar "GTA V" -> id 5 |
| `testBuscarPorTituloSinMayusculas` | Buscar "minecraft" -> id 3 |
| `testBuscarPorTituloParcial` | Buscar "mario" -> Mario Kart World |
| `testBuscarPorTituloNoExistente` | Un titulo inexistente -> null |

### `VideojuegoTest` y `CatalogoTest` (modelo de datos)

| Prueba | Que comprueba |
|---|---|
| `testConstructorCompleto` | El constructor guarda los 7 datos |
| `testConstructorVacio` | El constructor vacio (para JAXB) existe |
| `testSetters` | Los setters funcionan |
| `testToString` | El toString muestra los datos |
| `testToStringSinProveedor` | Si no hay proveedor, no lo muestra |
| `testCatalogoVacio` | Un catalogo nuevo esta vacio |
| `testAddVideojuegos` | Se pueden anadir juegos |
| `testToStringConJuegos` | El catalogo numera los juegos |

---

# PARTE 2 - PRUEBAS MANUALES DESDE EL MENU

## Pruebas de funcionamiento correcto

| # | Prueba | Resultado esperado | Resultado obtenido | Estado |
|---|--------|--------------------|--------------------|--------|
| 1 | Cargar correctamente el CSV (opcion 1) | 5 registros leidos | `OK: Se han cargado 5 videojuegos desde el CSV.` | CORRECTA |
| 2 | Mostrar el catalogo por consola (opcion 2) | 5 juegos con todos sus datos | Muestra los 5 juegos con id, titulo, plataforma, genero, precio, stock y proveedor | CORRECTA |
| 3 | Generar el XML (opcion 3) | Se crea `data/salida/catalogo.xml` | `OK: XML generado en ...\data\salida\catalogo.xml` | CORRECTA |
| 4 | **`codigoProveedor` NO aparece en el XML** | No aparece la cadena | No aparece ni `codigoProveedor` ni ningun `PROV-00X` | CORRECTA |
| 5 | `id` aparece como ATRIBUTO XML | `<videojuego id="1">` | Correcto | CORRECTA |
| 6 | Los demas campos son ELEMENTOS | `<titulo>`, `<precio>`... | Correcto: titulo, plataforma, genero, precio y stock son elementos | CORRECTA |
| 7 | Cargar nuevamente el XML (opcion 4) | 5 objetos reconstruidos | `OK: Se han cargado 5 videojuegos desde el XML.` | CORRECTA |
| 8 | Generar un CSV a partir del XML (opcion 5) | CSV con 5 filas | `OK: CSV generado en ...\videojuegos_export.csv` con las 5 filas | CORRECTA |
| 9 | Buscar por id (opcion 6) | Devuelve el juego correcto | Introduce `3` -> devuelve Minecraft | CORRECTA |
| 10 | Buscar por titulo (opcion 6) | Devuelve el juego correcto | Introduce `mario` -> devuelve Mario Kart World | CORRECTA |
| 11 | Informacion de ficheros (opcion 7) | Existencia, tamano y ruta | Muestra ruta, existe SI y tamano en bytes de los 3 ficheros | CORRECTA |

## Pruebas de gestion de errores

| # | Prueba | Entrada | Mensaje obtenido | Estado |
|---|--------|---------|------------------|--------|
| 12 | Cargar un XML que no existe (opcion 4) | Se borra `catalogo.xml` | `ERROR: No se encuentra el fichero XML de entrada: C:\...\data\salida\catalogo.xml` | CORRECTA |
| 13 | Opcion de menu incorrecta (opcion 9) | `9` | `ERROR: La opcion 9 no existe. Elige un numero entre 0 y 7.` | CORRECTA |
| 14 | Texto que no es un numero | `xyz` | `ERROR: La opcion "xyz" no es un numero valido. Elige un numero entre 0 y 7.` | CORRECTA |
| 15 | **Registro CSV con campos de menos** | Linea con 6 campos | `ERROR: Registro incorrecto en la linea 2 del CSV: se esperaban 7 campos y se han encontrado 6.` | CORRECTA |
| 16 | **Precio no numerico** | precio = `treinta` | `ERROR: Error de conversion numerica en la linea 2 del CSV: id, precio o stock no son numeros validos.` | CORRECTA |
| 17 | Exportar con el catalogo vacio (opcion 3) | Sin cargar el CSV antes | `ERROR: El catalogo esta vacio. Carga el CSV primero (opcion 1).` | CORRECTA |

## CASO ESPECIAL 1: la coma dentro de un valor

La linea 4 del CSV original es:

```
4,Mario Kart World,Switch 2,Carreras,79.99,6,PROV-004
```

La plataforma `Switch 2` lleva una coma dentro, asi que un `split(",")` normal
daria 8 campos en vez de 7 y el precio seria ` 2` en lugar de `79.99`.

**Solucion aplicada:** el metodo `separarCampos()` de `CatalogoCSV` detecta que
hay mas de 7 campos y vuelve a juntar los sobrantes de la plataforma.

**Resultado obtenido en el XML:**

```xml
<videojuego id="4">
    <titulo>Mario Kart World</titulo>
    <plataforma>Switch 2</plataforma>
    <genero>Carreras</genero>
    <precio>79.99</precio>
    <stock>6</stock>
</videojuego>
```

CORRECTA: la plataforma es `Switch 2` y el precio es `79.99`.

## CASO ESPECIAL 2: los campos vacios al final

Cuando exportamos a CSV los juegos que vienen del XML, el campo
`codigoProveedor` se deja vacio, asi que la linea acaba en coma:

```
1,Cyberpunk 2077,PC,RPG,39.99,12,
```

**Este fallo lo encontraron las pruebas automaticas.** Resultaba que al volver a
leer ese CSV Java devolvia solo 6 campos en vez de 7, y salia este error:

```
Registro incorrecto en la linea 2 del CSV: se esperaban 7 campos y se han encontrado 6.
```

**Causa:** en Java `split(",")` **descarta los ultimos campos si estan vacios**.

**Solucion:** usar `split(",", -1)`, que mantiene los campos vacios del final.

CORRECTA: las pruebas `testEscribirYVolverALeer` y `testFlujoCompleto` lo confirman.

---

## Conclusion

- **28 pruebas automaticas**: todas correctas (`BUILD SUCCESS`)
- **17 pruebas manuales**: todas correctas
- El flujo completo `CSV -> Java -> XML -> Java -> CSV` funciona
- `codigoProveedor` nunca aparece en el XML

## Como volver a repetir las pruebas

```bash
# Todas las pruebas automaticas
mvn test

# Compilar y ejecutar la aplicacion
mvn clean compile
mvn exec:java
```

Prueba manual 12 (XML inexistente), borrar antes el fichero:

```bash
del data\salida\catalogo.xml
```

Pruebas manuales 15 y 16: crear `data/entrada/videojuegos_error.csv` con datos erroneos

```csv
id,titulo,plataforma,genero,precio,stock,codigoProveedor
1,Test Mal Formato,PC,RPG,39.99,PROV-009
2,Precio Mal,PC,RPG,treinta,5,PROV-009
```

y cambiar en `Main.java` la ruta de entrada a ese fichero.

| # | Prueba | Resultado esperado | Resultado obtenido | Estado |
|---|--------|--------------------|--------------------|--------|
| 1 | Cargar correctamente el CSV (opcion 1) | 5 registros leidos | `OK: Se han cargado 5 videojuegos desde el CSV.` | CORRECTA |
| 2 | Mostrar el catalogo por consola (opcion 2) | 5 juegos con todos sus datos | Muestra los 5 juegos con id, titulo, plataforma, genero, precio, stock y proveedor | CORRECTA |
| 3 | Generar el XML (opcion 3) | Se crea `data/salida/catalogo.xml` | `OK: XML generado en ...\data\salida\catalogo.xml` | CORRECTA |
| 4 | **`codigoProveedor` NO aparece en el XML** | No aparece la cadena | No aparece ni `codigoProveedor` ni ningun `PROV-00X` | CORRECTA |
| 5 | `id` aparece como ATRIBUTO XML | `<videojuego id="1">` | Correcto | CORRECTA |
| 6 | Los demas campos son ELEMENTOS | `<titulo>`, `<precio>`... | Correcto: titulo, plataforma, genero, precio y stock son elementos | CORRECTA |
| 7 | Cargar nuevamente el XML (opcion 4) | 5 objetos reconstruidos | `OK: Se han cargado 5 videojuegos desde el XML.` | CORRECTA |
| 8 | Generar un CSV a partir del XML (opcion 5) | CSV con 5 filas | `OK: CSV generado en ...\videojuegos_export.csv` con las 5 filas | CORRECTA |
| 9 | Buscar por id (opcion 6) | Devuelve el juego correcto | Introduce `3` -> devuelve Minecraft | CORRECTA |
| 10 | Buscar por titulo (opcion 6) | Devuelve el juego correcto | Introduce `mario` -> devuelve Mario Kart World | CORRECTA |
| 11 | Informacion de ficheros (opcion 7) | Existencia, tamano y ruta | Muestra ruta, existe SI y tamano en bytes de los 3 ficheros | CORRECTA |

## Pruebas de gestion de errores

| # | Prueba | Entrada | Mensaje obtenido | Estado |
|---|--------|---------|------------------|--------|
| 12 | Cargar un XML que no existe (opcion 4) | Se borra `catalogo.xml` | `ERROR: No se encuentra el fichero XML de entrada: C:\...\data\salida\catalogo.xml` | CORRECTA |
| 13 | Opcion de menu incorrecta (opcion 9) | `9` | `ERROR: La opcion 9 no existe. Elige un numero entre 0 y 7.` | CORRECTA |
| 14 | Texto que no es un numero | `xyz` | `ERROR: La opcion "xyz" no es un numero valido. Elige un numero entre 0 y 7.` | CORRECTA |
| 15 | **Registro CSV con campos de menos** | Linea con 6 campos | `ERROR: Registro incorrecto en la linea 2 del CSV: se esperaban 7 campos y se han encontrado 6.` | CORRECTA |
| 16 | **Precio no numerico** | precio = `treinta` | `ERROR: Error de conversion numerica en la linea 2 del CSV: id, precio o stock no son numeros validos.` | CORRECTA |
| 17 | Exportar con el catalogo vacio (opcion 3) | Sin cargar el CSV antes | `ERROR: El catalogo esta vacio. Carga el CSV primero (opcion 1).` | CORRECTA |

## Caso especial: la coma dentro de un valor

La linea 4 del CSV original es:

```
4,Mario Kart World,Switch 2,Carreras,79.99,6,PROV-004
```

La plataforma `Switch 2` lleva una coma dentro, asi que un `split(",")` normal
daria 8 campos en vez de 7 y el precio seria ` 2` en lugar de `79.99`.

**Solucion aplicada:** el metodo `separarCampos()` de `CatalogoCSV` detecta que
hay mas de 7 campos y vuelve a juntar los sobrantes de la plataforma.

**Resultado obtenido en el XML:**

```xml
<videojuego id="4">
    <titulo>Mario Kart World</titulo>
    <plataforma>Switch 2</plataforma>
    <genero>Carreras</genero>
    <precio>79.99</precio>
    <stock>6</stock>
</videojuego>
```

CORRECTA: la plataforma es `Switch 2` y el precio es `79.99`.

## Conclusion

Las 17 pruebas se ejecutaron y **todas dan el resultado esperado**.
El flujo completo `CSV -> Java -> XML -> Java -> CSV` funciona.

## Como volver a repetir las pruebas

```bash
mvn clean compile
mvn exec:java
```

Prueba 12 (XML inexistente): borrar antes el fichero.

```bash
del data\salida\catalogo.xml
```

Pruebas 15 y 16: crear `data/entrada/videojuegos_error.csv` con datos erroneos

```csv
id,titulo,plataforma,genero,precio,stock,codigoProveedor
1,Test Mal Formato,PC,RPG,39.99,PROV-009
2,Precio Mal,PC,RPG,treinta,5,PROV-009
```

y cambiar en `Main.java` la ruta de entrada a ese fichero.