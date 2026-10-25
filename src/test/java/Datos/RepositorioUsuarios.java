
package Datos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Repositorio encargado de conservar la informacion
 * persistente de los usuarios.
 *
 * Utiliza GestorArchivos para realizar las operaciones
 * de lectura y escritura.
 *
 * Cada usuario tiene su propio archivo de saldo.
 */
public class RepositorioUsuarios {

    // Clase responsable de leer y escribir archivos
    private final GestorArchivos gestorArchivos;

    /**
     * Constructor del repositorio.
     *
     * Inicializa el gestor de archivos que se
     * utilizara para almacenar la informacion.
     */
    public RepositorioUsuarios() {
        this.gestorArchivos = new GestorArchivos();
    }

    /**
     * Guarda el saldo actual de un usuario.
     *
     * Si el archivo ya existe, actualiza su contenido.
     * Si no existe, el gestor lo crea automaticamente.
     *
     * @param nombreUsuario nombre del usuario
     * @param saldo saldo que se desea guardar
     * @throws IOException si ocurre un error de escritura
     */
    public void guardarSaldo(String nombreUsuario,
                             int saldo) throws IOException {

        // Evita guardar valores negativos
        if (saldo < 0) {
            throw new IllegalArgumentException(
                    "El saldo no puede ser negativo"
            );
        }

        // Obtiene el archivo correspondiente al usuario
        String nombreArchivo =
                obtenerNombreArchivo(nombreUsuario);

        // Convierte el saldo a texto
        String contenido = String.valueOf(saldo);

        // Delega la escritura al gestor de archivos
        gestorArchivos.guardarTexto(
                nombreArchivo,
                contenido
        );
    }

    /**
     * Recupera el saldo previamente guardado.
     *
     * Si el usuario no tiene un archivo, devuelve
     * el saldo inicial recibido como parametro.
     *
     * @param nombreUsuario nombre del usuario
     * @param saldoInicial saldo para usuarios sin registro
     * @return saldo recuperado o saldo inicial
     * @throws IOException si el archivo no puede leerse
     *                     o contiene informacion invalida
     */
    public int cargarSaldo(String nombreUsuario,
                           int saldoInicial) throws IOException {

        if (saldoInicial < 0) {
            throw new IllegalArgumentException(
                    "El saldo inicial no puede ser negativo"
            );
        }

        // Identifica el archivo del usuario
        String nombreArchivo =
                obtenerNombreArchivo(nombreUsuario);

        // Si no existe, utiliza el saldo inicial
        if (!gestorArchivos.existeArchivo(nombreArchivo)) {
            return saldoInicial;
        }

        // Recupera la informacion almacenada
        String contenido =
                gestorArchivos.leerTexto(nombreArchivo);

        // Comprueba que el archivo no este vacio
        if (contenido.isBlank()) {
            throw new IOException(
                    "El archivo del usuario esta vacio"
            );
        }

        try {

            // Convierte el contenido a un numero entero
            int saldo = Integer.parseInt(
                    contenido.trim()
            );

            // Valida que el saldo sea correcto
            if (saldo < 0) {
                throw new IOException(
                        "El saldo almacenado es invalido"
                );
            }

            return saldo;

        } catch (NumberFormatException ex) {

            // Evita utilizar datos corruptos
            throw new IOException(
                    "El archivo contiene un saldo no valido",
                    ex
            );
        }
    }

    /**
     * Comprueba si un usuario tiene un saldo guardado.
     *
     * @param nombreUsuario nombre del usuario
     * @return true si existe el archivo
     */
    public boolean existeRegistro(String nombreUsuario) {

        String nombreArchivo =
                obtenerNombreArchivo(nombreUsuario);

        return gestorArchivos.existeArchivo(nombreArchivo);
    }

    /**
     * Genera un nombre de archivo unico para cada
     * nombre de usuario.
     *
     * Utiliza Base64 URL-safe para permitir nombres
     * con espacios, acentos y otros caracteres.
     *
     * No almacena contrasenas ni datos sensibles.
     *
     * @param nombreUsuario nombre original del usuario
     * @return nombre del archivo asociado
     */
    private String obtenerNombreArchivo(String nombreUsuario) {

        if (nombreUsuario == null
                || nombreUsuario.isBlank()) {

            throw new IllegalArgumentException(
                    "El nombre de usuario es obligatorio"
            );
        }

        // Convierte el nombre a bytes UTF-8
        byte[] bytes = nombreUsuario.getBytes(
                StandardCharsets.UTF_8
        );

        // Genera una identificacion segura para la ruta
        String identificador = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);

        // Construye el nombre del archivo
        return "usuario_" + identificador + ".txt";
    }
}
