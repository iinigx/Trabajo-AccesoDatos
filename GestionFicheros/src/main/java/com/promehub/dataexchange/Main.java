package com.promehub.dataexchange;

import com.promehub.dataexchange.menu.Menu;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase Main: punto de entrada de la aplicacion.
 *
 * Solo se encarga de indicar DONDE estan los ficheros
 * y de arrancar el menu.
 */
public class Main {

    public static void main(String[] args) {

        // Rutas de los ficheros que usa la aplicacion
        Path rutaCsvEntrada = Paths.get("data", "entrada", "videojuegos.csv");
        Path rutaXmlSalida = Paths.get("data", "salida", "catalogo.xml");
        Path rutaCsvSalida = Paths.get("data", "salida", "videojuegos_export.csv");

        // Crear el menu y arrancarlo
        Menu menu = new Menu(rutaCsvEntrada, rutaXmlSalida, rutaCsvSalida);
        menu.iniciar();
    }
}