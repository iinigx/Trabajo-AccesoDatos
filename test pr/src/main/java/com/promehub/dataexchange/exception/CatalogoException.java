package com.promehub.dataexchange.exception;

/**
 * Excepcion propia del proyecto.
 *
 * La usamos para TODO error que le pueda pasar al usuario:
 * - fichero que no existe
 * - registro CSV incorrecto
 * - numero que no se puede convertir
 * - problemas con el XML
 * - opcion de menu incorrecta
 *
 * Cada excepcion lleva un mensaje en espanol que explica
 * claramente cual es el problema.
 */
public class CatalogoException extends Exception {

    // Numero de serie para identificar la version de la clase
    private static final long serialVersionUID = 1L;

    /**
     * Constructor con mensaje.
     * @param mensaje texto que se mostrara al usuario
     */
    public CatalogoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con mensaje y causa (error original).
     * @param mensaje texto que se mostrara al usuario
     * @param causa   error original que ha provoked este fallo
     */
    public CatalogoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}