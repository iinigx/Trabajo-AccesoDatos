package com.promehub.dataexchange.menu;

import com.promehub.dataexchange.model.Catalogo;
import com.promehub.dataexchange.model.Videojuego;

//Clase Buscador: busca un videojuego por su id o por su titulo. La usa la opcion 6 del menu
public class Buscador {

    public static Videojuego buscarPorId(Catalogo catalogo, int id) {

        if (catalogo == null || catalogo.getVideojuegos() == null) {
            return null;
        }

        // Recorremos todos los videojuegos con un for clasico
        for (int i = 0; i < catalogo.size(); i++) {
            Videojuego videojuego = catalogo.getVideojuegos().get(i);

            if (videojuego != null && videojuego.getId() == id) {
                return videojuego;
            }
        }

        // Si no lo encontramos devolvemos null
        return null;
    }

    
    public static Videojuego buscarPorTitulo(Catalogo catalogo, String texto) {

        // Si no hay nada que buscar, no devolvemos nada.
        // Sin esto, buscar "" devolveria el primer juego porque todo contiene "".
        if (catalogo == null || catalogo.getVideojuegos() == null
                || texto == null || texto.trim().isEmpty()) {
            return null;
        }

        // Ponemos en minusculas lo que buscamos para comparar
        String buscado = texto.toLowerCase().trim();

        for (int i = 0; i < catalogo.size(); i++) {
            Videojuego videojuego = catalogo.getVideojuegos().get(i);

            if (videojuego == null || videojuego.getTitulo() == null) {
                continue;
            }

            // Si el titulo del juego contiene el texto, lo devolvemos
            if (videojuego.getTitulo().toLowerCase().contains(buscado)) {
                return videojuego;
            }
        }

        return null;
    }

    // Devuelve TODOS los que coinciden con el titulo.
    // La opcion 6 del menu los muestra en lista.
    public static java.util.List<Videojuego> buscarTodosPorTitulo(Catalogo catalogo, String texto) {
        java.util.List<Videojuego> resultado = new java.util.ArrayList<>();

        if (catalogo == null || catalogo.getVideojuegos() == null
                || texto == null || texto.trim().isEmpty()) {
            return resultado;
        }

        String buscado = texto.toLowerCase().trim();

        for (int i = 0; i < catalogo.size(); i++) {
            Videojuego videojuego = catalogo.getVideojuegos().get(i);

            if (videojuego == null || videojuego.getTitulo() == null) {
                continue;
            }

            if (videojuego.getTitulo().toLowerCase().contains(buscado)) {
                resultado.add(videojuego);
            }
        }

        return resultado;
    }
}