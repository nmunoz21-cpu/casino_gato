
package Datos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase encargada de gestionar la persistencia
 * de informacion mediante archivos de texto.
 *
 * Su unica responsabilidad es guardar,
 * recuperar y comprobar la existencia de archivos.
 */
public class GestorArchivos {

    // Carpeta donde se almacenaran los archivos.
    private static final Path DIRECTORIO =
            Paths.get("datos_guardados");

    /**
     * Constructor del gestor de archivos.
     */
    public GestorArchivos() {
    }

    /**
     * Valida el nombre de un archivo y construye
     * su ruta dentro del directorio de almacenamiento.
     *
     * @param nombreArchivo nombre del archivo
     * @return ruta del archivo
     */
    private Path obtenerRuta(String nombreArchivo) {

        if (nombreArchivo == null
                || !nombreArchivo.matches("[a-zA-Z0-9._-]+")
                || nombreArchivo.equals(".")
                || nombreArchivo.equals("..")) {

            throw new IllegalArgumentException(
                    "Nombre de archivo no valido"
            );
        }

        return DIRECTORIO.resolve(nombreArchivo);
    }

    /**
     * Guarda informacion de texto en un archivo.
     *
     * Si no existe el directorio, lo crea.
     * Si el archivo ya existe, reemplaza su contenido.
     *
     * @param nombreArchivo nombre del archivo
     * @param contenido informacion que se guardara
     * @throws IOException si ocurre un error de escritura
     */
    public void guardarTexto(String nombreArchivo,
                             String contenido) throws IOException {

        Path archivo = obtenerRuta(nombreArchivo);

        // Crea la carpeta de almacenamiento
        Files.createDirectories(DIRECTORIO);

        // Guarda el contenido utilizando UTF-8
        Files.writeString(
                archivo,
                contenido,
                StandardCharsets.UTF_8
        );
    }

    /**
     * Recupera la informacion de un archivo.
     *
     * Si el archivo no existe, devuelve
     * una cadena vacia.
     *
     * @param nombreArchivo nombre del archivo
     * @return contenido almacenado
     * @throws IOException si ocurre un error de lectura
     */
    public String leerTexto(String nombreArchivo)
            throws IOException {

        Path archivo = obtenerRuta(nombreArchivo);

        // Comprueba si el archivo existe
        if (!Files.exists(archivo)) {
            return "";
        }

        // Lee y devuelve el contenido
        return Files.readString(
                archivo,
                StandardCharsets.UTF_8
        );
    }

    /**
     * Comprueba si existe un archivo.
     *
     * @param nombreArchivo nombre del archivo
     * @return true si existe, false si no existe
     */
    public boolean existeArchivo(String nombreArchivo) {

        Path archivo = obtenerRuta(nombreArchivo);

        return Files.isRegularFile(archivo);
    }
}
