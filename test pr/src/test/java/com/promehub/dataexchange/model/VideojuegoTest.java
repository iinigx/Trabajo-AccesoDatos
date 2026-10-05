package com.promehub.dataexchange.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de la clase Videojuego.
 * Son pruebas sencillas: crear el objeto y comprobar sus datos.
 */
class VideojuegoTest {

    /**
     * Comprobamos que el constructor guarda bien todos los datos.
     */
    @Test
    void testConstructorCompleto() {

        Videojuego juego = new Videojuego(1, "Cyberpunk 2077", "PC", "RPG",
                39.99, 12, "PROV-001");

        assertEquals(1, juego.getId());
        assertEquals("Cyberpunk 2077", juego.getTitulo());
        assertEquals("PC", juego.getPlataforma());
        assertEquals("RPG", juego.getGenero());
        assertEquals(39.99, juego.getPrecio(), 0.001);
        assertEquals(12, juego.getStock());
        assertEquals("PROV-001", juego.getCodigoProveedor());
    }

    /**
     * El constructor vacio es obligatorio para JAXB,
     * por eso debe existir y dejar el objeto vacio pero utilizable.
     */
    @Test
    void testConstructorVacio() {

        Videojuego juego = new Videojuego();

        assertEquals(0, juego.getId());
        assertNull(juego.getTitulo());
        assertEquals(0, juego.getStock());
    }

    /**
     * Comprobamos que los setters funcionan.
     */
    @Test
    void testSetters() {

        Videojuego juego = new Videojuego();
        juego.setId(5);
        juego.setTitulo("GTA V");
        juego.setStock(15);

        assertEquals(5, juego.getId());
        assertEquals("GTA V", juego.getTitulo());
        assertEquals(15, juego.getStock());
    }

    /**
     * El toString debe mostrar el titulo.
     */
    @Test
    void testToString() {

        Videojuego juego = new Videojuego(4, "Mario Kart World", "Switch 2",
                "Carreras", 79.99, 6, "PROV-004");

        String texto = juego.toString();

        assertTrue(texto.contains("Mario Kart World"));
        assertTrue(texto.contains("Switch 2"));
        assertTrue(texto.contains("PROV-004"));
    }

    /**
     * Si no hay codigo de proveedor (viene del XML),
     * el toString no debe mostrarlo.
     */
    @Test
    void testToStringSinProveedor() {

        Videojuego juego = new Videojuego();
        juego.setId(1);
        juego.setTitulo("Test");
        juego.setCodigoProveedor(null);

        assertFalse(juego.toString().contains("Proveedor"));
    }
}