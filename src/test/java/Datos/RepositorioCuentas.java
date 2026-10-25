
package Datos;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Repositorio encargado de guardar y recuperar
 * las cuentas registradas en Casino BlackCat.
 *
 * No almacena las contrasenas en texto plano.
 * Utiliza PBKDF2 para verificar las credenciales.
 */
public class RepositorioCuentas {

    // Archivo donde se almacenan las cuentas
    private static final String ARCHIVO = "cuentas.txt";

    // Parametros de derivacion de contrasenas
    private static final int ITERACIONES = 210000;
    private static final int LONGITUD_HASH = 256;
    private static final int LONGITUD_SAL = 16;

    // Clase encargada de leer y escribir archivos
    private final GestorArchivos gestorArchivos;

    // Generador de valores aleatorios seguros
    private final SecureRandom random;

    /**
     * Inicializa el repositorio de cuentas.
     */
    public RepositorioCuentas() {
        this.gestorArchivos = new GestorArchivos();
        this.random = new SecureRandom();
    }

    /**
     * Guarda una nueva cuenta de usuario.
     *
     * Comprueba que el username no este repetido.
     * Guarda el nombre, la sal y el hash de la clave.
     *
     * @param username identificador del usuario
     * @param clave contrasena de la cuenta
     * @param nombre nombre del usuario
     * @throws IOException si ocurre un error de archivo
     */
    public void registrarCuenta(
            String username,
            String clave,
            String nombre) throws IOException {

        // Valida los datos obligatorios
        if (username == null || username.isBlank()
                || clave == null || clave.isBlank()
                || nombre == null || nombre.isBlank()) {

            throw new IllegalArgumentException(
                    "Todos los campos son obligatorios"
            );
        }

        username = username.trim();
        nombre = nombre.trim();

        // Comprueba que el usuario no este registrado
        if (existeCuenta(username)) {
            throw new IllegalArgumentException(
                    "El nombre de usuario ya existe"
            );
        }

        // Genera una sal aleatoria para la contrasena
        byte[] sal = new byte[LONGITUD_SAL];
        random.nextBytes(sal);

        // Obtiene el hash de la contrasena
        byte[] hash = derivarHash(clave, sal);

        // Codifica los datos para guardarlos en una linea
        String registro =
                codificar(username) + ";" +
                        codificar(nombre) + ";" +
                        Base64.getUrlEncoder().withoutPadding()
                                .encodeToString(sal) + ";" +
                        Base64.getUrlEncoder().withoutPadding()
                                .encodeToString(hash);

        // Lee el contenido anterior del archivo
        String contenidoAnterior =
                gestorArchivos.leerTexto(ARCHIVO);

        // Agrega el nuevo registro
        String contenidoNuevo = contenidoAnterior.isEmpty()
                ? registro + "\n"
                : contenidoAnterior.stripTrailing()
                + "\n" + registro + "\n";

        // Guarda todas las cuentas
        gestorArchivos.guardarTexto(
                ARCHIVO,
                contenidoNuevo
        );
    }

    /**
     * Comprueba si un username ya esta registrado.
     *
     * @param username nombre de usuario
     * @return true si existe, false en caso contrario
     * @throws IOException si falla la lectura
     */
    public boolean existeCuenta(String username)
            throws IOException {

        if (username == null || username.isBlank()) {
            return false;
        }

        for (Registro registro : leerRegistros()) {

            if (registro.username.equalsIgnoreCase(
                    username.trim())) {

                return true;
            }
        }

        return false;
    }

    /**
     * Autentica una cuenta utilizando las credenciales.
     *
     * Compara el hash calculado con el almacenado.
     * Nunca necesita recuperar la contrasena original.
     *
     * @param username identificador del usuario
     * @param clave contrasena ingresada
     * @return datos de la cuenta o null si no coincide
     * @throws IOException si falla la lectura
     */
    public CuentaGuardada autenticar(
            String username,
            String clave) throws IOException {

        if (username == null || clave == null) {
            return null;
        }

        for (Registro registro : leerRegistros()) {

            if (registro.username.equalsIgnoreCase(
                    username.trim())) {

                // Calcula el hash de la clave ingresada
                byte[] hashCalculado =
                        derivarHash(clave, registro.sal);

                // Compara los hashes
                boolean coincide = MessageDigest.isEqual(
                        hashCalculado,
                        registro.hash
                );

                if (coincide) {

                    return new CuentaGuardada(
                            registro.username,
                            registro.nombre
                    );
                }

                return null;
            }
        }

        return null;
    }

    /**
     * Lee todos los registros del archivo de cuentas.
     *
     * @return lista de registros guardados
     * @throws IOException si los datos estan corruptos
     */
    private List<Registro> leerRegistros()
            throws IOException {

        List<Registro> registros = new ArrayList<>();

        // Recupera el contenido del archivo
        String contenido =
                gestorArchivos.leerTexto(ARCHIVO);

        if (contenido.isBlank()) {
            return registros;
        }

        // Procesa cada linea del archivo
        for (String linea : contenido.split("\\R")) {

            if (linea.isBlank()) {
                continue;
            }

            String[] partes = linea.split(";", -1);

            if (partes.length != 4) {
                throw new IOException(
                        "Formato de cuenta invalido"
                );
            }

            try {

                // Recupera los datos de la cuenta
                String username = decodificar(partes[0]);
                String nombre = decodificar(partes[1]);

                byte[] sal = Base64.getUrlDecoder()
                        .decode(partes[2]);

                byte[] hash = Base64.getUrlDecoder()
                        .decode(partes[3]);

                if (username.isBlank() || nombre.isBlank()
                        || sal.length != LONGITUD_SAL
                        || hash.length != LONGITUD_HASH / 8) {

                    throw new IOException(
                            "Datos de cuenta invalidos"
                    );
                }

                // Agrega el registro recuperado
                registros.add(
                        new Registro(
                                username,
                                nombre,
                                sal,
                                hash
                        )
                );

            } catch (IllegalArgumentException ex) {

                throw new IOException(
                        "No se pudo interpretar una cuenta",
                        ex
                );
            }
        }

        return registros;
    }

    /**
     * Deriva un hash de la contrasena mediante PBKDF2.
     *
     * @param clave contrasena original
     * @param sal valor aleatorio asociado a la cuenta
     * @return hash derivado
     */
    private byte[] derivarHash(String clave, byte[] sal)
            throws IOException {

        PBEKeySpec especificacion = new PBEKeySpec(
                clave.toCharArray(),
                sal,
                ITERACIONES,
                LONGITUD_HASH
        );

        try {

            SecretKeyFactory fabrica =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256"
                    );

            return fabrica
                    .generateSecret(especificacion)
                    .getEncoded();

        } catch (GeneralSecurityException ex) {

            throw new IOException(
                    "No fue posible procesar la contrasena",
                    ex
            );

        } finally {

            // Limpia la clave temporal de la especificacion
            especificacion.clearPassword();
        }
    }

    /**
     * Convierte texto en Base64 compatible con archivos.
     */
    private String codificar(String texto) {

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(
                        texto.getBytes(StandardCharsets.UTF_8)
                );
    }

    /**
     * Recupera el texto codificado en Base64.
     */
    private String decodificar(String texto) {

        byte[] bytes = Base64.getUrlDecoder()
                .decode(texto);

        return new String(
                bytes,
                StandardCharsets.UTF_8
        );
    }

    /**
     * Representa internamente una cuenta almacenada.
     *
     * Contiene la sal y el hash necesarios
     * para verificar las contrasenas.
     */
    private static class Registro {

        private final String username;
        private final String nombre;
        private final byte[] sal;
        private final byte[] hash;

        private Registro(
                String username,
                String nombre,
                byte[] sal,
                byte[] hash) {

            this.username = username;
            this.nombre = nombre;
            this.sal = sal;
            this.hash = hash;
        }
    }

    /**
     * Contiene los datos publicos de una cuenta
     * despues de una autenticacion correcta.
     */
    public static class CuentaGuardada {

        private final String username;
        private final String nombre;

        private CuentaGuardada(
                String username,
                String nombre) {

            this.username = username;
            this.nombre = nombre;
        }

        /**
         * Devuelve el username registrado.
         */
        public String getUsername() {
            return username;
        }

        /**
         * Devuelve el nombre registrado.
         */
        public String getNombre() {
            return nombre;
        }
    }
}
