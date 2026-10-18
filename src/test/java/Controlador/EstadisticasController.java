
package Controlador;

import Modelo.Estadisticas;
import Modelo.TipoApuesta;
import Modelo.Usuario;

public class EstadisticasController {

    // Controlador que administra la sesión activa
    private final SessionController session;

    // Recibe la misma sesión de los otros controladores
    public EstadisticasController(SessionController session) {

        if (session == null) {
            throw new IllegalArgumentException(
                    "La sesión es obligatoria"
            );
        }

        this.session = session;
    }

    // Obtiene las estadísticas del usuario activo
    private Estadisticas obtenerEstadisticas() {

        Usuario usuario = session.getUsuarioActual();

        return usuario.getEstadisticas();
    }

    // Devuelve la cantidad total de jugadas
    public int obtenerTotalJugadas() {
        return obtenerEstadisticas().calcularTotalJugadas();
    }

    // Devuelve la cantidad de victorias
    public int obtenerVictorias() {
        return obtenerEstadisticas().calcularVictorias();
    }

    // Devuelve el porcentaje de victorias
    public double obtenerPorcentajeVictorias() {
        return obtenerEstadisticas().calcularPorcentajeVictorias();
    }

    // Devuelve la mayor racha de victorias
    public int obtenerRachaMaxima() {
        return obtenerEstadisticas().calcularRachaMaxima();
    }

    // Devuelve el tipo de apuesta más frecuente
    public TipoApuesta obtenerTipoMasJugado() {
        return obtenerEstadisticas().calcularTipoMasJugado();
    }
}
