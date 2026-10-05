package com.promehub.dataexchange.csv;

import com.promehub.dataexchange.exception.CatalogoException;
import com.promehub.dataexchange.model.Catalogo;
import com.promehub.dataexchange.model.Videojuego;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de la clase CatalogoCSV: lectura y escritura del CSV.
 */
class CatalogoCSVTest {

    // El CSV que nos ha dado el enunciado
    private Path csvOriginal = Paths.get("data", "entrada", "videojuegos.csv");

    /**
     * El enunciado pide comprobar que el CSV se lee bien.
     */
    @Test
    void testLeerCsvCorrecto() throws CatalogoException {

        Catalogo catalogo = CatalogoCSV.leer(csvOriginal);

        // Son 5 videojuegos
        assertEquals(5, catalogo.size());
    }

    /**
     * Comprobamos los datos del primer juego.
     */
    @Test
    void testDatosDelPrimerJuego() throws CatalogoException {

        Catalogo catalogo = CatalogoCSV.leer(csvOriginal);
        Videojuego primero = catalogo.getVideojuegos().get(0);

        assertEquals(1, primero.getId());
        assertEquals("Cyberpunk 2077", primero.getTitulo());
        assertEquals("PC", primero.getPlataforma());
        assertEquals("RPG", primero.getGenero());
        assertEquals(39.99, primero.getPrecio(), 0.001);
        assertEquals(12, primero.getStock());
        assertEquals("PROV-001", primero.getCodigoProveedor());
    }

    /**
     * CASO ESPECIAL: la plataforma "Switch 2" lleva una coma dentro.
     * Comprobamos que la app lo resuelve bien.
     */
    @Test
    void testPlataformaConComa() throws CatalogoException {

        Catalogo catalogo = CatalogoCSV.leer(csvOriginal);
        Videojuego marioKarts = catalogo.getVideojuegos().get(3);

        assertEquals("Mario Kart World", marioKarts.getTitulo());
        assertEquals("Switch 2", marioKarts.getPlataforma());
        assertEquals("Carreras", marioKarts.getGenero());
        assertEquals(79.99, marioKarts.getPrecio(), 0.001);
        assertEquals(6, marioKarts.getStock());
    }

    /**
     * Si el fichero no existe, debe saltar nuestra excepcion
     * con un mensaje claro.
     */
    @Test
    void testFicheroInexistente() {

        Path rutaFalsa = Paths.get("data", "entrada", "no_existe.csv");

        CatalogoException error = assertThrows(
                CatalogoException.class, () -> CatalogoCSV.leer(rutaFalsa));

        assertTrue(error.getMessage().contains("No se encuentra"));
    }

    /**
     * Un registro con campos de menos debe dar error.
     */
    @Test
    void testRegistroConCamposDeMenos() throws IOException {

        Path temporal = crearCsvTemporal(
                "id,titulo,plataforma,genero,precio,stock,codigoProveedor",
                "1,Juego Mal,PC,RPG,39.99,PROV-009");

        CatalogoException error = assertThrows(
                CatalogoException.class, () -> CatalogoCSV.leer(temporal));

        assertTrue(error.getMessage().contains("Registro incorrecto"));
        assertTrue(error.getMessage().contains("linea 2"));

        borrar(temporal);
    }

    /**
     * Un precio que no es numero debe dar error de conversion.
     */
    @Test
    void testPrecioNoNumerico() throws IOException {

        Path temporal = crearCsvTemporal(
                "id,titulo,plataforma,genero,precio,stock,codigoProveedor",
                "2,Precio Mal,PC,RPG,treinta,5,PROV-009");

        CatalogoException error = assertThrows(
                CatalogoException.class, () -> CatalogoCSV.leer(temporal));

        assertTrue(error.getMessage().contains("conversion numerica"));

        borrar(temporal);
    }

    /**
     * Escribir un catalogo y volver a leerlo debe dar lo mismo.
     */
    @Test
    void testEscribirYVolverALeer() throws CatalogoException, IOException {

        Catalogo original = CatalogoCSV.leer(csvOriginal);
        Path salida = Paths.get("target", "test_videojuegos.csv");

        CatalogoCSV.escribir(salida, original);
        Catalogo recovery = CatalogoCSV.leer(salida);

        assertEquals(5, recovery.size());
        assertEquals("Cyberpunk 2077", recovery.getVideojuegos().get(0).getTitulo());
        assertEquals("Switch 2", recovery.getVideojuegos().get(3).getPlataforma());

        borrar(salida);
    }

    // ---------------- METODOS DE AYUDA ----------------

    /**
     * Crea un CSV temporal en la carpeta target.
     */
    private Path crearCsvTemporal(String cabecera, String linea) throws IOException {
        Path ruta = Paths.get("target", "csv_error_test.csv");
        Files.createDirectories(ruta.getParent());
        Files.write(ruta, (cabecera + "\n" + linea).getBytes(StandardCharsets.UTF_8));
        return ruta;
    }

    /**
     * Borra el fichero temporal.
     */
    private void borrar(Path ruta) throws IOException {
        Files.deleteIfExists(ruta);
    }
}