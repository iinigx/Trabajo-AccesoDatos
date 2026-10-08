# Primera planificacion del proyecto

PromeHub Data Exchange - U1 Persistencia en ficheros (incluido XML)

## 1. Que hay que hacer

Aplicacion Java que convierta el catalogo de videojuegos de PromeHub Manager (CSV)
al formato de PromeHub Store (XML) y viceversa, sin conectar ambas aplicaciones:

    CSV -> Java -> XML
    XML -> Java -> CSV

## 2. Tareas previstas

- Definir el modelo de datos (Videojuego y Catalogo) con las anotaciones JAXB.
- Leer el CSV con acceso secuencial y crear los objetos Java.
- Generar el XML con JAXB, excluyendo `codigoProveedor`.
- Leer el XML con JAXB y reconstruir los objetos.
- Generar de nuevo el CSV a partir del XML.
- Menu de consola con las 7 opciones.
- Gestion de excepciones y catalogo de pruebas.

## 3. Como se organiza el tiempo

| Fase | Tarea                                              | Responsable        |
|------|----------------------------------------------------|--------------------|
| 1    | Modelo de datos y lectura del CSV                  | Programador experto|
| 2    | Conversion a XML con JAXB y vuelta a CSV           | Programador experto|
| 3    | Menu, busqueda e informacion de ficheros           | Team Leader        |
| 4    | Pruebas y documentacion (README)                   | QA y Documentacion |

## 4. Proximo paso inmediato

Implementar el modelo de datos con las anotaciones `@XmlRootElement`,
`@XmlAccessorType`, `@XmlAttribute`, `@XmlElement` y `@XmlTransient`, y la lectura
secuencial del fichero CSV.

## 5. Menu de la aplicacion

    ========================================
           PROMEHUB DATA EXCHANGE
    ========================================

    1. Cargar catalogo desde CSV
    2. Mostrar catalogo
    3. Exportar catalogo a XML
    4. Cargar catalogo desde XML
    5. Exportar catalogo a CSV
    6. Buscar videojuego
    7. Informacion de ficheros
    0. Salir
