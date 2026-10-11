
package Controlador;

import Modelo.Resultado;
import Modelo.Usuario;

import java.util.List;

public class ResultadoController {

    // Controlador que administra la sesión activa
    private final SessionController session;

    // Recibe la misma sesión utilizada por los demás controladores
    public ResultadoController(SessionController session) {
        if (session == null) {
            throw new IllegalArgumentException(
                    "El controlador de sesión es obligatorio"
            );
        }

        this.session = session;
    }

    // Devuelve el historial del usuario que inició sesión
    public List<Resultado> obtenerHistorial() {

        // Obtiene el usuario de la sesión actual
        Usuario usuario = session.getUsuarioActual();

        // Consulta únicamente los resultados de ese usuario
        return usuario.getHistorial();
    }
}
