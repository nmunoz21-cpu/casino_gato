
package Controlador;

import Modelo.Estadisticas;
import Modelo.Usuario;

/**
 * Controlador encargado de comunicar
 * la vista con las estadisticas del usuario.
 */
public class EstadisticasController {

    // Controlador de la sesion activa
    private final SessionController session;

    /**
     * Constructor que recibe la sesion
     * compartida del sistema.
     */
    public EstadisticasController(SessionController session) {

        if (session == null) {
            throw new IllegalArgumentException(
                    "El controlador de sesion es obligatorio"
            );
        }

        this.session = session;
    }

    /**
     * Obtiene las estadisticas del usuario
     * que inicio sesion.
     */
    private Estadisticas obtenerEstadisticas() {

        Usuario usuario = session.getUsuarioActual();

        return usuario.getEstadisticas();
    }

    /**
     * Devuelve el total de jugadas.
     */
    public int obtenerTotalJugadas() {
        return obtenerEstadisticas().calcularTotalJugadas();
    }

    /**
     * Devuelve la cantidad de victorias.
     */
    public int obtenerVictorias() {
        return obtenerEstadisticas().calcularVictorias();
    }

    /**
     * Devuelve el porcentaje de victorias.
     */
    public double obtenerPorcentajeVictorias() {
        return obtenerEstadisticas()
                .calcularPorcentajeVictorias();
    }

    /**
     * Devuelve la racha maxima de victorias.
     */
    public int obtenerRachaMaxima() {
        return obtenerEstadisticas().calcularRachaMaxima();
    }

    /**
     * Devuelve el tipo de apuesta mas utilizado.
     */
    public String obtenerTipoMasJugado() {
        return obtenerEstadisticas().calcularTipoMasJugado();
    }
}
