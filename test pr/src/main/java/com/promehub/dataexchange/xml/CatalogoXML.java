package com.promehub.dataexchange.xml;

import com.promehub.dataexchange.exception.CatalogoException;
import com.promehub.dataexchange.model.Catalogo;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Clase CatalogoXML: convierte el catalogo a XML y viceversa usando JAXB.
 *
 * JAXB hace el trabajo solo gracias a las anotaciones que tienen
 * las clases Videojuego y Catalogo:
 * - Marshaller : objeto Java  -> fichero XML  (escribir)
 * - Unmarshaller : fichero XML -> objeto Java  (leer)
 *
 * Como codigoProveedor tiene @XmlTransient, JAXB no lo escribe
 * en el XML automaticamente.
 */
public class CatalogoXML {

    /**
     * Escribe el catalogo en un fichero XML.
     */
    public static void escribir(Path ruta, Catalogo catalogo) throws CatalogoException {

        try {
            // Crear las carpetas padre si no existen
            Path padre = ruta.getParent();
            if (padre != null) {
                Files.createDirectories(padre);
            }

            // 1. Crear el contexto de JAXB para la clase Catalogo
            JAXBContext contexto = JAXBContext.newInstance(Catalogo.class);

            // 2. Crear el Marshaller (objeto Java -> XML)
            Marshaller marshaller = contexto.createMarshaller();

            // 3. Ponerlo bonito: con cabecera XML e indentado
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            // 4. Abrir el fichero y escribir el catalogo dentro
            OutputStream salida = Files.newOutputStream(ruta);
            marshaller.marshal(catalogo, salida);
            salida.close();

        } catch (JAXBException e) {
            throw new CatalogoException(
                    "No se ha podido generar el XML: error al convertir el catalogo.", e);
        } catch (IOException e) {
            throw new CatalogoException(
                    "No se ha podido escribir el fichero XML: " + ruta.toAbsolutePath(), e);
        }
    }

    /**
     * Lee un fichero XML y devuelve el catalogo.
     */
    public static Catalogo leer(Path ruta) throws CatalogoException {

        // 1. Comprobar que el fichero existe
        if (!Files.exists(ruta)) {
            throw new CatalogoException(
                    "No se encuentra el fichero XML de entrada: " + ruta.toAbsolutePath());
        }

        try {
            // 2. Crear el contexto de JAXB
            JAXBContext contexto = JAXBContext.newInstance(Catalogo.class);

            // 3. Crear el Unmarshaller (XML -> objeto Java)
            Unmarshaller unmarshaller = contexto.createUnmarshaller();

            // 4. Leer el fichero y pasar los bytes a objetos
            Catalogo catalogo = (Catalogo) unmarshaller.unmarshal(ruta.toFile());

            return catalogo;

        } catch (JAXBException e) {
            throw new CatalogoException(
                    "No se ha podido procesar el XML: el documento no tiene el formato esperado.", e);
        }
    }
}