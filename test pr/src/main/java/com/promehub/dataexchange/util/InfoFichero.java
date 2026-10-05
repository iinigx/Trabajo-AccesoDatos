package com.promehub.dataexchange.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Clase InfoFichero: muestra informacion sobre un fichero.
 *
 * La usa la opcion 7 del menu (Informacion de ficheros).
 * Muestra: existencia, tamaño y ruta.
 */
public class InfoFichero {

    private String nombre;
    private Path ruta;
    private boolean existe;
    private long tamano;

    /**
     * Constructor: recibe el nombre y la ruta del fichero.
     */
    public InfoFichero(String nombre, Path ruta) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.existe = Files.exists(ruta);
        this.tamano = 0;

        // Si existe, cogemos su tamaño en bytes
        if (existe) {
            try {
                this.tamano = Files.size(ruta);
            } catch (IOException e) {
                this.tamano = 0;
            }
        }
    }

    /**
     * Devuelve la informacion del fichero como texto.
     */
    public String mostrar() {
        String texto = "--- " + nombre + " ---\n";
        texto = texto + "  Ruta:     " + ruta.toAbsolutePath() + "\n";

        if (existe) {
            texto = texto + "  Existe:   SI\n";
            texto = texto + "  Tamano:   " + tamano + " bytes\n";
        } else {
            texto = texto + "  Existe:   NO\n";
            texto = texto + "  Tamano:   -\n";
        }

        return texto;
    }

    // ---------------- GETTERS Y SETTERS ----------------

    public String getNombre() {
        return nombre;
    }

    public Path getRuta() {
        return ruta;
    }

    public boolean isExiste() {
        return existe;
    }

    public long getTamano() {
        return tamano;
    }

    @Override
    public String toString() {
        return mostrar();
    }
}