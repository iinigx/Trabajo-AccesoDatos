package com.promehub.dataexchange.xml;

import com.promehub.dataexchange.csv.CatalogoCSV;
import com.promehub.dataexchange.exception.CatalogoException;
import com.promehub.dataexchange.model.Catalogo;
import com.promehub.dataexchange.model.Videojuego;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de la clase CatalogoXML: el flujo CSV -> Java -> XML -> Java -> CSV.
 *
 * Aqui estan las pruebas MAS IMPORTANTES de la practica,
 * porque comprueban que codigoProveedor NO sale en el XML.
 */
class CatalogoXMLTest {

    private Path csvOriginal = Paths.get("data", "entrada", "videojuegos.csv");
    private Path xmlTemporal = Paths.get("target", "test_catalogo.xml");

    /**
     * PRUEBA PRINCIPAL: generar el XML y comprobar
     * que codigoProveedor NO aparece en el fichero.
     */
    @Test
    void testCodigoProveedorNoApareceEnElXml() throws CatalogoException, Exception {

        Catalogo catalogo = CatalogoCSV.leer(csvOriginal);

        // Generar el XML
        CatalogoXML.escribir(xmlTemporal, catalogo);

        // Leer el XML como texto plano
        String contenido = Files.readString(xmlTemporal, StandardCharsets.UTF_8);

        // Estas tres comprobaciones son las importantes
        assertFalse(contenido.contains("codigoProveedor"),
                "El XML no debe contener el texto codigoProveedor");
        assertFalse(contenido.contains("PROV-"),
                "El XML no debe contener ningun codigo de proveedor");

        // Pero si deben aparecer los demas datos
        assertTrue(contenido.contains("Cyberpunk 2077"));
        assertTrue(contenido.contains("Switch 2"));

        Files.deleteIfExists(xmlTemporal);
    }

    /**
     * El id debe aparecer como ATRIBUTO, no como elemento.
     */
    @Test
    void testIdEsAtributoXml() throws CatalogoException, Exception {

        Catalogo catalogo = CatalogoCSV.leer(csvOriginal);
        CatalogoXML.escribir(xmlTemporal, catalogo);

        String contenido = Files.readString(xmlTemporal, StandardCharsets.UTF_8);

        // id="1" es un atributo -> VALE
        assertTrue(contenido.contains("<videojuego id=\"1\">"));

        // <id>1</id> seria un elemento -> NO VALE
        assertFalse(contenido.contains("<id>"));

        Files.deleteIfExists(xmlTemporal);
    }

    /**
     * El elemento raiz debe llamarse "catalogo".
     */
    @Test
    void testElementoRaizCatalogo() throws CatalogoException, Exception {

        Catalogo catalogo = CatalogoCSV.leer(csvOriginal);
        CatalogoXML.escribir(xmlTemporal, catalogo);

        String contenido = Files.readString(xmlTemporal, StandardCharsets.UTF_8);

        assertTrue(contenido.contains("<catalogo>"));
        assertTrue(contenido.contains("</catalogo>"));

        Files.deleteIfExists(xmlTemporal);
    }

    /**
     * FLUJO COMPLETO: CSV -> Java -> XML -> Java -> CSV
     */
    @Test
    void testFlujoCompleto() throws CatalogoException, Exception {

        // 1. Leer el CSV
        Catalogo desdeCsv = CatalogoCSV.leer(csvOriginal);
        assertEquals(5, desdeCsv.size());

        // 2. Pasarlo a XML
        CatalogoXML.escribir(xmlTemporal, desdeCsv);

        // 3. Leer de nuevo el XML
        Catalogo desdeXml = CatalogoXML.leer(xmlTemporal);
        assertEquals(5, desdeXml.size());

        // 4. Comprobar que los datos siguen iguales
        Videojuego primero = desdeXml.getVideojuegos().get(0);
        assertEquals(1, primero.getId());
        assertEquals("Cyberpunk 2077", primero.getTitulo());
        assertEquals(39.99, primero.getPrecio(), 0.001);

        // Y el caso especial de la coma
        assertEquals("Switch 2", desdeXml.getVideojuegos().get(3).getPlataforma());

        // 5. Volver a CSV
        Path csvSalida = Paths.get("target", "test_flujo.csv");
        CatalogoCSV.escribir(csvSalida, desdeXml);
        Catalogo finalCatalogo = CatalogoCSV.leer(csvSalida);
        assertEquals(5, finalCatalogo.size());

        // Limpieza
        Files.deleteIfExists(xmlTemporal);
        Files.deleteIfExists(csvSalida);
    }

    /**
     * Los juegos que vienen del XML NO tienen proveedor.
     */
    @Test
    void testJuegosDelXmlNoTienenProveedor() throws CatalogoException, Exception {

        Catalogo desdeCsv = CatalogoCSV.leer(csvOriginal);
        CatalogoXML.escribir(xmlTemporal, desdeCsv);

        Catalogo desdeXml = CatalogoXML.leer(xmlTemporal);

        // El primer juego venia del XML -> no tiene proveedor
        assertNull(desdeXml.getVideojuegos().get(0).getCodigoProveedor());

        Files.deleteIfExists(xmlTemporal);
    }

    /**
     * Leer un XML que no existe debe dar error.
     */
    @Test
    void testXmlInexistente() {

        Path xmlFalso = Paths.get("data", "salida", "no_existe.xml");

        CatalogoException error = assertThrows(
                CatalogoException.class, () -> CatalogoXML.leer(xmlFalso));

        assertTrue(error.getMessage().contains("No se encuentra"));
    }
}