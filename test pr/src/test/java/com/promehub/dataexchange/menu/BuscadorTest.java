package com.promehub.dataexchange.menu;

import com.promehub.dataexchange.csv.CatalogoCSV;
import com.promehub.dataexchange.exception.CatalogoException;
import com.promehub.dataexchange.model.Catalogo;
import com.promehub.dataexchange.model.Videojuego;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de la clase Buscador (opcion 6 del menu).
 */
class BuscadorTest {

    private Catalogo catalogo;

    @BeforeEach
    void setUp() throws CatalogoException {
        // Para probar necesito un catalogo, asi que leemos el CSV de verdad
        catalogo = CatalogoCSV.leer(Paths.get("data", "entrada", "videojuegos.csv"));
    }

    /**
     * Buscar por un id que existe.
     */
    @Test
    void testBuscarPorIdExistente() {

        Videojuego juego = Buscador.buscarPorId(catalogo, 1);

        assertNotNull(juego);
        assertEquals("Cyberpunk 2077", juego.getTitulo());
    }

    /**
     * Buscar por un id que no existe -> devuelve null.
     */
    @Test
    void testBuscarPorIdNoExistente() {

        Videojuego juego = Buscador.buscarPorId(catalogo, 999);

        assertNull(juego);
    }

    /**
     * Buscar por titulo exacto.
     */
    @Test
    void testBuscarPorTituloExacto() {

        Videojuego juego = Buscador.buscarPorTitulo(catalogo, "GTA V");

        assertNotNull(juego);
        assertEquals(5, juego.getId());
    }

    /**
     * La busqueda NO debe distinguir mayusculas de minusculas.
     */
    @Test
    void testBuscarPorTituloSinMayusculas() {

        Videojuego juego = Buscador.buscarPorTitulo(catalogo, "minecraft");

        assertNotNull(juego);
        assertEquals(3, juego.getId());
    }

    /**
     * La busqueda debe encontrar textos parciales
     * (por ejemplo "mario" dentro de "Mario Kart World").
     */
    @Test
    void testBuscarPorTituloParcial() {

        Videojuego juego = Buscador.buscarPorTitulo(catalogo, "mario");

        assertNotNull(juego);
        assertEquals("Mario Kart World", juego.getTitulo());
    }

    /**
     * Un titulo que no esta en el catalogo -> null.
     */
    @Test
    void testBuscarPorTituloNoExistente() {

        Videojuego juego = Buscador.buscarPorTitulo(catalogo, "juego inexistente");

        assertNull(juego);
    }
}