# PromeHub Data Exchange

**Unidad 1 – Persistencia en ficheros (incluido XML)**
**Módulo: Acceso a Datos · Ciclo: Desarrollo de Aplicaciones Multiplataforma · Curso 2026-27**

## Equipo

| Persona | Rol |
|---|---|
| Raúl Herranz | Team Leader |
| Iñigo Rojo | Programador experto |
| Mohamed Bouka | Responsable de pruebas (QA) |
| Nacho de León | Responsable de documentación |

## Qué hace la aplicación

Aplicación Java de consola que actúa como intermediario entre dos aplicaciones:

- **PromeHub Manager** (interna) → trabaja con **CSV**
- **PromeHub Store** (distribución) → trabaja con **XML**

Las dos aplicaciones **no** se conectan entre sí. Nuestra aplicación hace de puente:

```
CSV ──► Java ──► XML        (opciones 1 y 3)
XML ──► Java ──► CSV        (opciones 4 y 5)
```

---

## 1. Requisitos previos

| Herramienta | Versión | Estado |
|---|---|---|
| JDK | 21 o superior | Instalado (jdk-25) |
| Maven | 3.9 o superior | `mvn -v` |
| JAXB | 4.x | Se descarga sola por Maven |

> JAXB **ya no viene incluido en el JDK** desde Java 11, por eso el proyecto lo declara
> como dependencia en el `pom.xml`.

---

## 2. Estructura del proyecto

```
test pr/
│
├── pom.xml                     Dependencias (JAXB, JUnit) y configuración de ejecución
├── README.md                   Este documento
├── INFORME_ESTRUCTURA.md       Informe de estructura y plan de inicio
│
├── data/                       Ficheros de trabajo (NO son código fuente)
│   ├── entrada/
│   │   └── videojuegos.csv     CSV original proporcionado por el profesor
│   └── salida/                 XML y CSV que genera la aplicación (se regeneran)
│
├── docs/                       Documentación del proyecto
│   ├── CATALOGO_PRUEBAS.md     Registro de pruebas realizadas y su resultado
│   └── PLANIFICACION.md        Primera planificación del proyecto
│
└── src/
    ├── main/
    │   ├── java/com/promehub/dataexchange/
    │   │   ├── Main.java               Punto de entrada. Crea el contexto y lanza el menú.
    │   │   │
    │   │   ├── model/                  MODELO DE DATOS (paquete central)
    │   │   │   ├── Videojuego.java      Clase JAXB: un videojuego (id, titulo, ...)
    │   │   │   └── Catalogo.java        Clase JAXB raíz @XmlRootElement("catalogo")
    │   │   │
    │   │   ├── csv/                    LECTURA / ESCRITURA SECUENCIAL
    │   │   │   └── CatalogoCSV.java     Leer CSV → objetos, y objetos → CSV
    │   │   │
    │   │   ├── xml/                    LECTURA / ESCRITURA XML (JAXB)
    │   │   │   └── CatalogoXML.java     Leer XML → objetos, y objetos → XML
    │   │   │
    │   │   ├── menu/                   INTERFAZ DE CONSOLA
    │   │   │   ├── Menu.java            Muestra el menú y lee la opción
    │   │   │   └── Buscador.java        RF6: buscar por id o por título
    │   │   │
    │   │   ├── util/                   UTILIDADES
    │   │   │   └── InfoFichero.java     RF7: existencia, tamaño y ruta
    │   │   │
    │   │   └── exception/              EXCEPCIONES PERSONALIZADAS
    │   │       └── CatalogoException.java
    │   │
    │   └── resources/              Recursos embebidos (ficheros de apoyo)
    │
    └── test/java/com/promehub/dataexchange/    Pruebas unitarias (JUnit 5)
```

---

## 3. Responsabilidad de cada paquete

| Paquete | Responsabilidad | No debe hacer |
|---|---|---|
| `model` | Definir las clases de datos y sus anotaciones JAXB. | Leer ficheros ni mostrar menús. |
| `csv` | Acceso **secuencial** al CSV. | Conocer XML o JAXB. |
| `xml` | Marshalling/Unmarshalling con JAXB. | Conocer el formato CSV. |
| `menu` | Mostrar el menú de 7 opciones + 0. Salir, y pedir datos al usuario. | Escribir ficheros directamente. |
| `util` | Información de ficheros y ayudas transversales. | Lógica de negocio. |
| `exception` | Excepciones propias con mensajes en español. | — |

> **Regla de oro:** el `model` no depende de nadie, y nadie del `model` depende de él
> salvo los paquetes que lo usan. Así el modelo es **reutilizable** y **fácil de explicar
> en la defensa** (criterio 1 de la rúbrica, 50 % de la nota).

---

## 4. Cómo compilar y ejecutar

Desde la raíz del proyecto (`test pr`):

```bash
# Compilar
mvn clean compile

# Ejecutar la aplicación de consola
mvn exec:java

# Empaquetar en un JAR ejecutable (crea target/promehub-data-exchange.jar)
mvn clean package
java -jar target/promehub-data-exchange.jar

# Ejecutar las pruebas
mvn test
```

---

## 5. Menú de la aplicación

========================================
       PROMEHUB DATA EXCHANGE
========================================

1. Cargar catálogo desde CSV
2. Mostrar catálogo
3. Exportar catálogo a XML
4. Cargar catálogo desde XML
5. Exportar catálogo a CSV
6. Buscar videojuego
7. Información de ficheros
0. Salir


---

## 6. Formato del XML generado

`codigoProveedor` es información interna de PHManager, por eso **no** aparece en el XML
(se marca con `@XmlTransient`):

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<catalogo>
    <videojuego id="1">
        <titulo>Cyberpunk 2077</titulo>
        <plataforma>PC</plataforma>
        <genero>RPG</genero>
        <precio>39.99</precio>
        <stock>12</stock>
    </videojuego>
    ...
</catalogo>
```

---

## 7. Rutas de los ficheros

| Fichero | Ruta |
|---|---|
| CSV de entrada | `data/entrada/videojuegos.csv` |
| XML de salida | `data/salida/catalogo.xml` |
| CSV de salida | `data/salida/videojuegos_export.csv` |

Se guardan **fuera de `src/`** a propósito: son datos, no código.

---

## 8. Estado de implementación

Todas las clases están **implementadas y probadas** (17/17 pruebas correctas):

| Paquete | Clase | Estado |
|---|---|---|
| — | `Main.java` | ✅ |
| `model` | `Videojuego.java` | ✅ Las 5 anotaciones JAXB |
| `model` | `Catalogo.java` | ✅ `@XmlRootElement("catalogo")` |
| `csv` | `CatalogoCSV.java` | ✅ Acceso secuencial + escritura |
| `xml` | `CatalogoXML.java` | ✅ Marshaller / Unmarshaller |
| `menu` | `Menu.java` | ✅ 7 opciones + 0. Salir |
| `menu` | `Buscador.java` | ✅ Por id y por título |
| `util` | `InfoFichero.java` | ✅ Existencia, tamaño, ruta |
| `exception` | `CatalogoException.java` | ✅ Mensajes en español |

Resultado verificado del XML generado:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<catalogo>
    <videojuego id="1">
        <titulo>Cyberpunk 2077</titulo>
        <plataforma>PC</plataforma>
        <genero>RPG</genero>
        <precio>39.99</precio>
        <stock>12</stock>
    </videojuego>
    ...
</catalogo>
```

`codigoProveedor` **no aparece** en el XML (gracias a `@XmlTransient`).

### Detalle importante: la coma de "Switch 2"

El CSV original tiene `4,Mario Kart World,Switch 2,Carreras,79.99,6,PROV-004`.
La plataforma lleva una coma dentro, así que un `split(",")` normal daría 8 campos.
El método `separarCampos()` de `CatalogoCSV` lo detecta y vuelve a juntar los
campos sobrantes, obteniendo `plataforma = "Switch 2"` y `precio = 79.99`.

### Pruebas automáticas (JUnit 5)

```bash
mvn test
```

```
Tests run: 28, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

| Fichero de pruebas | Pruebas |
|---|---|
| `model/VideojuegoTest.java` | 5 |
| `model/CatalogoTest.java` | 4 |
| `csv/CatalogoCSVTest.java` | 7 |
| `xml/CatalogoXMLTest.java` | 6 |
| `menu/BuscadorTest.java` | 6 |
| **Total** | **28 ✅** |

---

## 9. Documentación del proyecto

- `INFORME_ESTRUCTURA.md` → estructura, responsabilidades y **plan de inicio**
- `docs/PLANIFICACION.md` → primera planificación (fases, responsables)
- `docs/CATALOGO_PRUEBAS.md` → 28 pruebas automáticas + 17 manuales con resultados