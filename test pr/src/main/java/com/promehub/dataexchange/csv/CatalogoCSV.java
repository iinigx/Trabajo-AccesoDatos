package com.promehub.dataexchange.csv;

import com.promehub.dataexchange.exception.CatalogoException;
import com.promehub.dataexchange.model.Catalogo;
import com.promehub.dataexchange.model.Videojuego;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Clase CatalogoCSV: lee y escribe el fichero CSV de forma SECUENCIAL.
 *
 * Acceso secuencial = leemos linea a linea con readLine(),
 * sin cargar el fichero entero en memoria.
 *
 * IMPORTANTE - el CSV tiene una complicacion:
 * la linea 4 tiene "Mario Kart World,Switch 2,Carreras,..."
 * La plataforma es "Switch 2" y lleva una coma DENTRO del valor.
 * Si hacemos split(",") a pelo nos salen 8 campos en vez de 7
 * y el precio seria " 2" en vez de 79.99.
 *
 * Nuestra solucion (sencilla, de nivel basico):
 * separamos por comas, y si nos sobran campos, juntamos de nuevo
 * los sobrantes de la plataforma usando un espacio.
 */
public class CatalogoCSV {

    // Numero de campos que debe tener cada linea del CSV
    private static final int NUM_CAMPOS = 7;

    // Cabecera del fichero CSV
    private static final String CABECERA =
            "id,titulo,plataforma,genero,precio,stock,codigoProveedor";

    /**
     * Lee un fichero CSV y devuelve el catalogo.
     * @param ruta ruta del fichero CSV
     * @return Catalogo con los videojuegos leidos
     * @throws CatalogoException si el fichero no existe o hay registros incorrectos
     */
    public static Catalogo leer(Path ruta) throws CatalogoException {

        // 1. Comprobar que el fichero existe
        if (!Files.exists(ruta)) {
            throw new CatalogoException(
                    "No se encuentra el fichero CSV de entrada: " + ruta.toAbsolutePath());
        }

        Catalogo catalogo = new Catalogo();
        int numeroLinea = 0;

        try {
            // 2. Abrir el fichero (try-with-resources lo cierra solo)
            BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8);

            String linea = lector.readLine();      // 3. leer la cabecera
            numeroLinea = 1;

            // 4. Ignorar la cabecera y seguir leyendo hasta el final
            while ((linea = lector.readLine()) != null) {
                numeroLinea = numeroLinea + 1;

                // Ignorar lineas vacias
                if (linea.trim().isEmpty()) {
                    continue;
                }

                // 5. Crear un Videojuego por cada registro valido
                Videojuego videojuego = crearVideojuego(linea, numeroLinea);
                catalogo.add(videojuego);
            }

            lector.close();

        } catch (IOException e) {
            throw new CatalogoException(
                    "No se ha podido leer el fichero CSV: " + ruta.toAbsolutePath(), e);
        }

        return catalogo;
    }

    /**
     * Convierte una linea del CSV en un objeto Videojuego.
     */
    private static Videojuego crearVideojuego(String linea, int numeroLinea)
            throws CatalogoException {

        String[] campos = separarCampos(linea);

        // Comprobar que llegan los 7 campos obligatorios
        if (campos.length != NUM_CAMPOS) {
            throw new CatalogoException(
                    "Registro incorrecto en la linea " + numeroLinea
                            + " del CSV: se esperaban " + NUM_CAMPOS
                            + " campos y se han encontrado " + campos.length + ".");
        }

        int id;
        double precio;
        int stock;

        try {
            id = Integer.parseInt(campos[0].trim());
            precio = Double.parseDouble(campos[4].trim());
            stock = Integer.parseInt(campos[5].trim());
        } catch (NumberFormatException e) {
            throw new CatalogoException(
                    "Error de conversion numerica en la linea " + numeroLinea
                            + " del CSV: id, precio o stock no son numeros validos.", e);
        }

        return new Videojuego(
                id,
                campos[1].trim(),
                campos[2].trim(),
                campos[3].trim(),
                precio,
                stock,
                campos[6].trim());
    }

    /**
     * Separa la linea en campos teniendo en cuenta que un valor
     * puede llevar comas dentro (por ejemplo "Switch 2").
     *
     * Si hay mas de 7 campos, la plataforma tenia comas,
     * asi que las juntamos de nuevo con espacios.
     */
    private static String[] separarCampos(String linea) {

        // OJO: el -1 final es importante.
        // Sin el, split(",") NO devuelve los ultimos campos si estan vacios.
        //Ejemplo: "1,Juego,PC,RPG,5,2,"  ->  con split(",") salen 6 campos
        //                                ->  con split(",",-1) salen los 7 correctos
        String[] campos = linea.split(",", -1);

        // Si todo esta bien, devolvemos tal cual
        if (campos.length <= NUM_CAMPOS) {
            return campos;
        }

        // Hay campos de mas -> reconstruir la plataforma
        // Ejemplo: Switch , 2  ->  "Switch 2"
        StringBuilder plataforma = new StringBuilder(campos[1].trim());
        int sobra = campos.length - NUM_CAMPOS;

        for (int i = 0; i < sobra; i++) {
            plataforma.append(" ").append(campos[2 + i].trim());
        }

        String[] corregidos = new String[NUM_CAMPOS];
        corregidos[0] = campos[0];
        corregidos[1] = campos[1];
        corregidos[2] = plataforma.toString();
        for (int i = 3; i < NUM_CAMPOS; i++) {
            corregidos[i] = campos[campos.length - (NUM_CAMPOS - i)];
        }

        return corregidos;
    }

    /**
 * Escribe un catalogo en un fichero CSV.
 */
public static void escribir(Path ruta, Catalogo catalogo) throws CatalogoException {

    try {
        // Crear las carpetas padre si no existen
        Path padre = ruta.getParent();
        if (padre != null) {
            Files.createDirectories(padre);
        }

        BufferedWriter escritor =
                Files.newBufferedWriter(ruta, StandardCharsets.UTF_8);

        // Escribir la cabecera
        escritor.write(CABECERA);
        escritor.newLine();

        // Escribir una linea por cada videojuego
        for (Videojuego videojuego : catalogo.getVideojuegos()) {

            String linea = videojuego.getId() + ","
                    + videojuego.getTitulo() + ","
                    + videojuego.getPlataforma() + ","
                    + videojuego.getGenero() + ","
                    + videojuego.getPrecio() + ","
                    + videojuego.getStock() + ","
                    + "";

            // Si el juego viene del XML no tiene proveedor,
            // por eso el ultimo campo se deja vacio.
            escritor.write(linea);
            escritor.newLine();
        }

        escritor.close();

    } catch (IOException e) {
        throw new CatalogoException(
                "No se ha podido escribir el fichero CSV de salida: "
                        + ruta.toAbsolutePath(), e);
    }
}
}