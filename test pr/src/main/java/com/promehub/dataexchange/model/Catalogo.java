package com.promehub.dataexchange.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Catalogo: contiene TODOS los videojuegos del fichero.
 *
 * Esta clase SI es el elemento raiz del XML (@XmlRootElement), por eso
 * el documento generado empieza asi:
 *
 *   <catalogo>
 *       <videojuego id="1">...</videojuego>
 *       <videojuego id="2">...</videojuego>
 *   </catalogo>
 *
 * Con @XmlElement(name="videojuego") le decimos que cada objeto de la
 * lista se escribe como un elemento <videojuego>.
 */
@XmlRootElement(name = "catalogo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Catalogo {

    // Lista de videojuegos. Se inicializa vacia para no tener null.
    @XmlElement(name = "videojuego")
    private List<Videojuego> videojuegos = new ArrayList<>();

    /**
     * Constructor vacio: OBLIGATORIO para JAXB.
     */
    public Catalogo() {
    }

    /**
     * Constructor con la lista ya creada.
     */
    public Catalogo(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }

    // ---------------- METODOS ----------------

    /**
     * Añade un videojuego al catalogo.
     */
    public void add(Videojuego videojuego) {
        videojuegos.add(videojuego);
    }

    /**
     * Devuelve cuantos videojuegos tiene el catalogo.
     */
    public int size() {
        return videojuegos.size();
    }

    /**
     * Indica si el catalogo esta vacio.
     */
    public boolean isEmpty() {
        return videojuegos.isEmpty();
    }

    /**
     * Devuelve todos los videojuegos.
     */
    public List<Videojuego> getVideojuegos() {
        return videojuegos;
    }

    public void setVideojuegos(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }

    /**
     * Muestra el catalogo completo.
     */
    @Override
    public String toString() {
        if (videojuegos.isEmpty()) {
            return "El catalogo esta vacio.";
        }

        String texto = "";
        for (int i = 0; i < videojuegos.size(); i++) {
            texto = texto + (i + 1) + ". " + videojuegos.get(i) + "\n";
        }
        return texto;
    }
}