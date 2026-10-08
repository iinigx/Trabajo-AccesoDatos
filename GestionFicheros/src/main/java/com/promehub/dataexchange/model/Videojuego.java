package com.promehub.dataexchange.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 * Clase Videojuego: representa UN juego del catalogo.
 *
 * Aqui estan las anotaciones de JAXB:
 * - @XmlAccessorType(FIELD) : JAXB trabaja directamente con los campos,
 *                            sin necesidad de getters y setters.
 * - @XmlAttribute           : el id sale como ATRIBUTO  -> id="1"
 * - @XmlElement             : los demas campos salen como ELEMENTOS
 * - @XmlTransient           : codigoProveedor NO sale en el XML
 *                            (es informacion interna de PHManager)
 *
 * Esta clase NO lleva @XmlRootElement porque no es el elemento raiz,
 * el elemento raiz es Catalogo.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Videojuego {

    // Identificador unico del videojuego. Sale como atributo XML.
    @XmlAttribute(name = "id")
    private int id;

    @XmlElement(name = "titulo")
    private String titulo;

    @XmlElement(name = "plataforma")
    private String plataforma;

    @XmlElement(name = "genero")
    private String genero;

    @XmlElement(name = "precio")
    private double precio;

    @XmlElement(name = "stock")
    private int stock;

    /**
     * Codigo del proveedor.
     * Con @XmlTransient JAXB lo ignora al escribir el XML,
     * pero el campo sigue existiendo en Java.
     */
    @XmlTransient
    private String codigoProveedor;

    /**
     * Constructor vacio: OBLIGATORIO para JAXB.
     * JAXB lo necesita para poder crear los objetos al leer el XML.
     */
    public Videojuego() {
    }

    /**
     * Constructor con todos los datos: lo usamos al leer el CSV.
     */
    public Videojuego(int id, String titulo, String plataforma, String genero,
                      double precio, int stock, String codigoProveedor) {
        this.id = id;
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.genero = genero;
        this.precio = precio;
        this.stock = stock;
        this.codigoProveedor = codigoProveedor;
    }

    // ---------------- GETTERS Y SETTERS ----------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    /**
     * Muestra el videojuego en una linea.
     * Lo usa la opcion 2 del menu (Mostrar catalogo).
     */
    @Override
    public String toString() {
        String texto = "ID: " + id
                + " | Titulo: " + titulo
                + " | Plataforma: " + plataforma
                + " | Genero: " + genero
                + " | Precio: " + precio + " EUR"
                + " | Stock: " + stock;

        // El codigo de proveedor solo se muestra si existe
        // (los juegos que vienen del XML no lo tienen)
        if (codigoProveedor != null) {
            texto = texto + " | Proveedor: " + codigoProveedor;
        }

        return texto;
    }
}