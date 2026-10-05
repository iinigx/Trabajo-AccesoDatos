package com.promehub.dataexchange.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de la clase Catalogo.
 */
class CatalogoTest {

    private Catalogo catalogo;

    @BeforeEach
    void setUp() {
        // Antes de cada prueba creamos un catalogo vacio
        catalogo = new Catalogo();
    }

    /**
     * Un catalogo nuevo debe estar vacio.
     */
    @Test
    void testCatalogoVacio() {

        assertEquals(0, catalogo.size());
        assertTrue(catalogo.isEmpty());
    }

    /**
     * Comprobamos que se pueden ir añadiendo videojuegos.
     */
    @Test
    void testAddVideojuegos() {

        catalogo.add(new Videojuego(1, "Cyberpunk 2077", "PC", "RPG", 39.99, 12, "PROV-001"));
        catalogo.add(new Videojuego(2, "GTA V", "PS5", "Accion", 29.99, 15, "PROV-001"));

        assertEquals(2, catalogo.size());
        assertFalse(catalogo.isEmpty());
        assertEquals("GTA V", catalogo.getVideojuegos().get(1).getTitulo());
    }

    /**
     * El toString de un catalogo vacio debe avisar.
     */
    @Test
    void testToStringCatalogoVacio() {

        assertTrue(catalogo.toString().contains("vacio"));
    }

    /**
     * El toString debe numerar los juegos.
     */
    @Test
    void testToStringConJuegos() {

        catalogo.add(new Videojuego(1, "Minecraft", "PC", "Sandbox", 29.99, 20, "PROV-002"));

        String texto = catalogo.toString();

        assertTrue(texto.contains("1."));
        assertTrue(texto.contains("Minecraft"));
    }
}