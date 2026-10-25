
package Datos;

import Modelo.ApuestaBase;
import Modelo.ApuestaRojo;
import Modelo.ApuestaNegro;
import Modelo.ApuestaPar;
import Modelo.ApuestaImpar;
import Modelo.Resultado;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.IntFunction;

/**
 * Repositorio encargado de guardar y recuperar
 * el historial de resultados de cada usuario.
 *
 * Utiliza GestorArchivos para la lectura y escritura.
 */
public class RepositorioHistorial {

    // Clase responsable de gestionar los archivos
    private final GestorArchivos gestorArchivos;

    // Relaciona las etiquetas con los constructores
    // de las clases concretas de apuestas.
    private static final Map<String, IntFunction<ApuestaBase>>
            FABRICAS = Map.of(
            "ROJO", ApuestaRojo::new,
            "NEGRO", ApuestaNegro::new,
            "PAR", ApuestaPar::new,
            "IMPAR", ApuestaImpar::new
    );

    /**
     * Constructor del repositorio.
     */
    public RepositorioHistorial() {
        this.gestorArchivos = new GestorArchivos();
    }

    /**
     * Guarda el historial completo de un usuario.
     *
     * Cada resultado se almacena en una linea,
     * separando los campos mediante punto y coma.
     *
     * @param username identificador del usuario
     * @param historial lista de resultados
     * @throws IOException si falla la escritura
     */
    public void guardarHistorial(
            String username,
            List<Resultado> historial) throws IOException {

        if (historial == null) {
            throw new IllegalArgumentException(
                    "El historial no puede ser nulo"
            );
        }

        // Obtiene el archivo individual del usuario
        String archivo = obtenerNombreArchivo(username);

        // Construye el contenido del historial
        StringBuilder contenido = new StringBuilder();

        for (Resultado resultado : historial) {

            // Recupera la etiqueta de la apuesta
            String etiqueta = resultado.getApuesta()
                    .getEtiqueta()
                    .toUpperCase(Locale.ROOT);

            if (!FABRICAS.containsKey(etiqueta)) {
                throw new IllegalArgumentException(
                        "Tipo de apuesta no reconocido: " + etiqueta
                );
            }

            // Guarda los datos de la ronda
            contenido.append(resultado.getNumero())
                    .append(";")
                    .append(resultado.isRojo())
                    .append(";")
                    .append(etiqueta)
                    .append(";")
                    .append(resultado.getMonto())
                    .append(";")
                    .append(resultado.isGano())
                    .append(";")
                    .append(resultado.getSaldo())
                    .append("\n");
        }

        // Delega la escritura a GestorArchivos
        gestorArchivos.guardarTexto(
                archivo,
                contenido.toString()
        );
    }

    /**
     * Recupera el historial guardado del usuario.
     *
     * Reconstruye los objetos Resultado y ApuestaBase
     * a partir de los datos almacenados.
     *
     * @param username identificador del usuario
     * @return lista de resultados recuperados
     * @throws IOException si falla la lectura
     */
    public List<Resultado> cargarHistorial(
            String username) throws IOException {

        // Lista donde se recuperaran los resultados
        List<Resultado> historial = new ArrayList<>();

        String archivo = obtenerNombreArchivo(username);

        // Si aun no existe un archivo, devuelve
        // un historial vacio para el usuario nuevo
        if (!gestorArchivos.existeArchivo(archivo)) {
            return historial;
        }

        String contenido = gestorArchivos.leerTexto(archivo);

        if (contenido.isBlank()) {
            return historial;
        }

        // Procesa cada ronda almacenada
        for (String linea : contenido.split("\\R")) {

            if (linea.isBlank()) {
                continue;
            }

            String[] datos = linea.split(";", -1);

            if (datos.length != 6) {
                throw new IOException(
                        "Formato de historial incorrecto"
                );
            }

            try {

                // Recupera los atributos del resultado
                int numero = Integer.parseInt(datos[0]);

                boolean rojo = leerBooleano(datos[1]);

                String etiqueta = datos[2]
                        .toUpperCase(Locale.ROOT);

                int monto = Integer.parseInt(datos[3]);

                boolean gano = leerBooleano(datos[4]);

                int saldo = Integer.parseInt(datos[5]);

                // Valida los valores recuperados
                if (numero < 0 || numero > 36
                        || monto <= 0 || saldo < 0) {

                    throw new IllegalArgumentException(
                            "Valores fuera de rango"
                    );
                }

                // Recupera la clase de apuesta
                IntFunction<ApuestaBase> fabrica =
                        FABRICAS.get(etiqueta);

                if (fabrica == null) {
                    throw new IllegalArgumentException(
                            "Apuesta desconocida"
                    );
                }

                ApuestaBase apuesta = fabrica.apply(monto);

                // Reconstruye el resultado original
                Resultado resultado = new Resultado(
                        numero,
                        rojo,
                        apuesta,
                        gano,
                        saldo
                );

                // Agrega el resultado recuperado
                historial.add(resultado);

            } catch (IllegalArgumentException ex) {

                throw new IOException(
                        "No se pudo recuperar una ronda",
                        ex
                );
            }
        }

        return historial;
    }

    /**
     * Interpreta un valor booleano almacenado.
     * Rechaza contenidos distintos de true o false.
     */
    private boolean leerBooleano(String texto) {

        if ("true".equalsIgnoreCase(texto)) {
            return true;
        }

        if ("false".equalsIgnoreCase(texto)) {
            return false;
        }

        throw new IllegalArgumentException(
                "Valor booleano incorrecto"
        );
    }

    /**
     * Genera un nombre de archivo independiente
     * para cada usuario, utilizando Base64 URL-safe.
     */
    private String obtenerNombreArchivo(String username) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "El usuario es obligatorio"
            );
        }

        String identificador = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(
                        username.getBytes(
                                StandardCharsets.UTF_8
                        )
                );

        return "historial_" + identificador + ".txt";
    }
}
