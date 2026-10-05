package com.promehub.dataexchange.menu;

import com.promehub.dataexchange.csv.CatalogoCSV;
import com.promehub.dataexchange.exception.CatalogoException;
import com.promehub.dataexchange.model.Catalogo;
import com.promehub.dataexchange.model.Videojuego;
import com.promehub.dataexchange.util.InfoFichero;
import com.promehub.dataexchange.xml.CatalogoXML;

import java.nio.file.Path;
import java.util.Scanner;

/**
 * Clase Menu: muestra el menu de la aplicacion por consola
 * y llama a la opcion que el usuario elija.
 */
public class Menu {

    // El catalogo que tenemos cargado en memoria ahora mismo
    private Catalogo catalogo = new Catalogo();

    // Rutas de los ficheros que usamos
    private Path rutaCsvEntrada;
    private Path rutaXmlSalida;
    private Path rutaCsvSalida;

    // Para leer lo que escribe el usuario
    private Scanner teclado = new Scanner(System.in);

    /**
     * Constructor.
     */
    public Menu(Path rutaCsvEntrada, Path rutaXmlSalida, Path rutaCsvSalida) {
        this.rutaCsvEntrada = rutaCsvEntrada;
        this.rutaXmlSalida = rutaXmlSalida;
        this.rutaCsvSalida = rutaCsvSalida;
    }

    /**
     * Muestra el menu y pide una opcion hasta que el usuario salga.
     */
    public void iniciar() {

        int opcion;

        do {
            mostrarMenu();
            opcion = pedirOpcion();

            // Si la opcion no es valida, el mensaje ya se ha mostrado
            // en pedirOpcion(), aqui solo volvemos a preguntar.
            if (opcion == -1) {
                continue;
            }

            switch (opcion) {
                case 1:
                    cargarDesdeCsv();
                    break;
                case 2:
                    mostrarCatalogo();
                    break;
                case 3:
                    exportarAXml();
                    break;
                case 4:
                    cargarDesdeXml();
                    break;
                case 5:
                    exportarACsv();
                    break;
                case 6:
                    buscarVideojuego();
                    break;
                case 7:
                    informacionFicheros();
                    break;
                case 0:
                    System.out.println("Saliendo de PromeHub Data Exchange. Hasta pronto!");
                    break;
                default:
                    // No se llega aqui: pedirOpcion() ya valida el rango.
                    System.out.println("ERROR: Opcion no valida.");
            }

        } while (opcion != 0);

        teclado.close();
    }

    /**
     * Muestra el menu por pantalla.
     */
    private void mostrarMenu() {
        System.out.println("");
        System.out.println("========================================");
        System.out.println("       PROMEHUB DATA EXCHANGE");
        System.out.println("========================================");
        System.out.println("1. Cargar catalogo desde CSV");
        System.out.println("2. Mostrar catalogo");
        System.out.println("3. Exportar catalogo a XML");
        System.out.println("4. Cargar catalogo desde XML");
        System.out.println("5. Exportar catalogo a CSV");
        System.out.println("6. Buscar videojuego");
        System.out.println("7. Informacion de ficheros");
        System.out.println("0. Salir");
        System.out.print("Elige una opcion: ");
    }

    /**
     * Pide la opcion al usuario y la devuelve como numero.
     * Si no es correcta, avisa y devuelve -1.
     */
    private int pedirOpcion() {

        String texto = teclado.nextLine().trim();

        int opcion;

        try {
            opcion = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: La opcion \"" + texto
                    + "\" no es un numero valido. Elige un numero entre 0 y 7.");
            return -1;
        }

        // Comprobar que la opcion esta entre 0 y 7
        if (opcion < 0 || opcion > 7) {
            System.out.println("ERROR: La opcion " + opcion
                    + " no existe. Elige un numero entre 0 y 7.");
            return -1;
        }

        return opcion;
    }
    // ---------------- OPCIONES DEL MENU ----------------

    /**
     * OPCION 1: Cargar catalogo desde CSV.
     */
    private void cargarDesdeCsv() {

        System.out.println("");
        System.out.println("--- Cargar catalogo desde CSV ---");

        try {
            catalogo = CatalogoCSV.leer(rutaCsvEntrada);

            System.out.println("OK: Se han cargado "
                    + catalogo.size() + " videojuegos desde el CSV.");

        } catch (CatalogoException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * OPCION 2: Mostrar catalogo.
     */
    private void mostrarCatalogo() {

        System.out.println("");
        System.out.println("--- Catalogo actual ---");

        if (catalogo.isEmpty()) {
            System.out.println("El catalogo esta vacio.");
            System.out.println("Primero carga el catalogo con la opcion 1.");
            return;
        }

        System.out.println("Total de videojuegos: " + catalogo.size());
        System.out.println(catalogo.toString());
    }

    /**
     * OPCION 3: Exportar catalogo a XML.
     */
    private void exportarAXml() {

        System.out.println("");
        System.out.println("--- Exportar catalogo a XML ---");

        if (catalogo.isEmpty()) {
            System.out.println("ERROR: El catalogo esta vacio. Carga el CSV primero (opcion 1).");
            return;
        }

        try {
            CatalogoXML.escribir(rutaXmlSalida, catalogo);

            System.out.println("OK: XML generado en " + rutaXmlSalida.toAbsolutePath());
            System.out.println("Nota: codigoProveedor NO aparece en el XML (@XmlTransient).");

        } catch (CatalogoException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * OPCION 4: Cargar catalogo desde XML.
     */
    private void cargarDesdeXml() {

        System.out.println("");
        System.out.println("--- Cargar catalogo desde XML ---");

        try {
            catalogo = CatalogoXML.leer(rutaXmlSalida);

            System.out.println("OK: Se han cargado "
                    + catalogo.size() + " videojuegos desde el XML.");
            System.out.println("Nota: estos juegos no tienen codigoProveedor.");

        } catch (CatalogoException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * OPCION 5: Exportar catalogo a CSV.
     */
    private void exportarACsv() {

        System.out.println("");
        System.out.println("--- Exportar catalogo a CSV ---");

        if (catalogo.isEmpty()) {
            System.out.println("ERROR: El catalogo esta vacio. Carga el catalogo primero.");
            return;
        }

        try {
            CatalogoCSV.escribir(rutaCsvSalida, catalogo);

            System.out.println("OK: CSV generado en " + rutaCsvSalida.toAbsolutePath());

        } catch (CatalogoException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    /**
     * OPCION 6: Buscar videojuego por id o por titulo.
     */
    private void buscarVideojuego() {

        System.out.println("");
        System.out.println("--- Buscar videojuego ---");

        if (catalogo.isEmpty()) {
            System.out.println("ERROR: El catalogo esta vacio. Carga el catalogo primero.");
            return;
        }

        System.out.print("Busqueda por id (1) o por titulo (2)? ");
        String tipo = teclado.nextLine().trim();

        System.out.print("Escribe el texto a buscar: ");
        String texto = teclado.nextLine().trim();

        Videojuego encontrado = null;

        try {
            if (tipo.equals("1")) {
                // Buscar por id
                int id = Integer.parseInt(texto);
                encontrado = Buscador.buscarPorId(catalogo, id);

            } else if (tipo.equals("2")) {
                // Buscar por titulo
                encontrado = Buscador.buscarPorTitulo(catalogo, texto);

            } else {
                System.out.println("ERROR: Tipo de busqueda no valido. Elige 1 o 2.");
                return;
            }

            if (encontrado != null) {
                System.out.println("Videojuego encontrado:");
                System.out.println(encontrado.toString());
            } else {
                System.out.println("No se ha encontrado ningun videojuego con \"" + texto + "\".");
            }

        } catch (NumberFormatException e) {
            System.out.println("ERROR: El id \"" + texto + "\" no es un numero valido.");
        }
    }

    /**
     * OPCION 7: Informacion de los ficheros que usamos.
     */
    private void informacionFicheros() {

        System.out.println("");
        System.out.println("--- Informacion de ficheros ---");

        InfoFichero f1 = new InfoFichero("CSV de entrada (PromeHub Manager)", rutaCsvEntrada);
        InfoFichero f2 = new InfoFichero("XML de salida (PromeHub Store)", rutaXmlSalida);
        InfoFichero f3 = new InfoFichero("CSV de salida", rutaCsvSalida);

        System.out.println(f1.mostrar());
        System.out.println(f2.mostrar());
        System.out.println(f3.mostrar());
    }

    /**
     * Permite saber si el catalogo esta vacio (lo usa Main).
     */
    public Catalogo getCatalogo() {
        return catalogo;
    }
}