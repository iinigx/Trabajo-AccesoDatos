package com.promehub.dataexchange.menu;

import com.promehub.dataexchange.model.Catalogo;
import com.promehub.dataexchange.model.Videojuego;

/**
 * Clase Buscador: busca un videojuego por su id o por su titulo.
 * La usa la opcion 6 del menu.
 */
public class Buscador {

    /**
     * Busca un videojuego por su identificador.
     * @param catalogo catalogo donde buscar
     * @param id       id del videojuego
     * @return el videojuego encontrado o null si no existe
     */
    public static Videojuego buscarPorId(Catalogo catalogo, int id) {

        // Recorremos todos los videojuegos con un for clasico
        for (int i = 0; i < catalogo.size(); i++) {
            Videojuego videojuego = catalogo.getVideojuegos().get(i);

            if (videojuego.getId() == id) {
                return videojuego;
            }
        }

        // Si no lo encontramos devolvemos null
        return null;
    }

    /**
     * Busca un videojuego por su titulo.
     * No distingue mayusculas y_minusculas, y busca si el titulo
     * CONTIENE el texto buscado.
     * @param catalogo catalogo donde buscar
     * @param texto    texto a buscar dentro del titulo
     * @return el videojuego encontrado o null si no existe
     */
    public static Videojuego buscarPorTitulo(Catalogo catalogo, String texto) {

        // Ponemos en minusculas lo que buscamos para comparar
        String buscado = texto.toLowerCase().trim();

        for (int i = 0; i < catalogo.size(); i++) {
            Videojuego videojuego = catalogo.getVideojuegos().get(i);

            // Si el titulo del juego contiene el texto, lo devolvemos
            if (videojuego.getTitulo().toLowerCase().contains(buscado)) {
                return videojuego;
            }
        }

        return null;
    }
}